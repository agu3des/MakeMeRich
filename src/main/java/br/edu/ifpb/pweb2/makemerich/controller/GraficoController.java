package br.edu.ifpb.pweb2.makemerich.controller;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.service.ContaService;
import br.edu.ifpb.pweb2.makemerich.service.CorrentistaService;
import br.edu.ifpb.pweb2.makemerich.service.TransacaoService;

@Controller
public class GraficoController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/grafico/{idConta}")
    public ModelAndView mostrarGraficoAnoConta(
        @PathVariable Integer idConta,
        @RequestParam(required = false) Integer ano,
        Authentication authentication) throws AccessDeniedException {

        // Busca o usuário logado
        String username = authentication.getName();
        Correntista usuarioLogado = correntistaService.findByUsername(username); 

        boolean isAdmin;
        if (usuarioLogado == null) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            if (!isAdmin) {
                return new ModelAndView("redirect:/auth/login");
            }
        } else {
            isAdmin = usuarioLogado.getUser().isAdmin();
        }

        Conta conta = contaService.findById(idConta);
        if (conta == null) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("message", "Conta não encontrada");
            return mav;
        }

        // Verifica permissão - admin pode ver qualquer conta
        if (!isAdmin && !conta.getCorrentista().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        if (ano == null) {
            ano = java.time.Year.now().getValue();
        }

        // Totais mensais (R$)
        List<Object[]> dados = transacaoService.totalPorMesAnoConta(idConta, ano);
        List<String> labels = new ArrayList<>();
        List<BigDecimal> valores = new ArrayList<>();

        // Quantidade de transações por mês
        List<Object[]> qtdDados = transacaoService.qtdTransacoesPorMesAnoConta(idConta, ano);
        List<Integer> qtdTransacoes = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            labels.add(java.time.Month.of(mes).getDisplayName(java.time.format.TextStyle.SHORT, new Locale("pt", "BR")));
            valores.add(BigDecimal.ZERO);
            qtdTransacoes.add(0);
        }

        for (Object[] row : dados) {
            Integer mes = (Integer) row[0];
            BigDecimal total = (BigDecimal) row[1];
            valores.set(mes - 1, total);
        }

        for (Object[] row : qtdDados) {
            Integer mes = (Integer) row[0];
            Long qtd = (Long) row[1]; 
            qtdTransacoes.set(mes - 1, qtd.intValue());
        }

        List<Double> valoresDouble = valores.stream()
            .map(BigDecimal::doubleValue)
            .toList();

        int anoAtual = java.time.Year.now().getValue();
        List<Integer> anosDisponiveis = IntStream.rangeClosed(anoAtual - 5, anoAtual)
            .boxed()
            .sorted(Comparator.reverseOrder())
            .toList();

        ModelAndView mav = new ModelAndView("/grafico");
        mav.addObject("labels", labels);
        mav.addObject("valores", valoresDouble);
        mav.addObject("qtdTransacoes", qtdTransacoes);
        mav.addObject("ano", ano);
        mav.addObject("conta", conta);
        mav.addObject("anosDisponiveis", anosDisponiveis);
        mav.addObject("anoSelecionado", ano);
        mav.addObject("isAdmin", isAdmin);

        return mav;
    }


}
