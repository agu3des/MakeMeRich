package br.edu.ifpb.pweb2.makemerich.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.makemerich.model.Categoria;
import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.model.Natureza;
import br.edu.ifpb.pweb2.makemerich.service.ContaService;
import br.edu.ifpb.pweb2.makemerich.service.CorrentistaService;
import br.edu.ifpb.pweb2.makemerich.service.TransacaoService;

@Controller
@PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
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
            Authentication authentication) throws Exception {

        String username = authentication.getName();
        Correntista usuarioLogado = correntistaService.findByUsername(authentication.getName());

        Conta conta = contaService.findById(idConta);
        if (conta == null) {
            return new ModelAndView("error").addObject("message", "Conta não encontrada");
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Apenas admin ou dono da conta podem acessar
        if (!isAdmin && !conta.getCorrentista().getId().equals(usuarioLogado.getId())) {
            throw new Exception("Acesso negado");
        }

        if (ano == null) {
            ano = java.time.Year.now().getValue();
        }

        // Preparar dados de transações
        List<Object[]> dados = transacaoService.totalPorMesAnoConta(idConta, ano);
        List<Object[]> qtdDados = transacaoService.qtdTransacoesPorMesAnoConta(idConta, ano);

        List<String> labels = new ArrayList<>();
        List<BigDecimal> valores = new ArrayList<>();
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

        List<Double> valoresDouble = valores.stream().map(BigDecimal::doubleValue).collect(Collectors.toList());

        List<Integer> anosDisponiveis = IntStream.rangeClosed(java.time.Year.now().getValue() - 5,
                java.time.Year.now().getValue())
                .boxed().sorted(Comparator.reverseOrder()).toList();

        // Orçamento consolidado
        List<Conta> contas = List.of(conta);
        Map<Natureza, Map<Categoria, Map<Integer, BigDecimal>>> orcamentoConsolidado =
                transacaoService.calcularOrcamentoConsolidado(contas, ano);
        Map<Natureza, Map<Integer, BigDecimal>> totaisPorMesPorNatureza =
                transacaoService.calcularTotaisMensaisPorNatureza(orcamentoConsolidado);

        List<String> orcamentoLabels = new ArrayList<>();
        List<Double> entradaMensal = new ArrayList<>();
        List<Double> saidaMensal = new ArrayList<>();
        List<Double> investimentoMensal = new ArrayList<>();
        List<Double> saldoMensal = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            orcamentoLabels.add(java.time.Month.of(mes).getDisplayName(java.time.format.TextStyle.SHORT, new Locale("pt", "BR")));
            BigDecimal entrada = totaisPorMesPorNatureza.getOrDefault(Natureza.ENTRADA, Map.of()).getOrDefault(mes, BigDecimal.ZERO);
            BigDecimal saida = totaisPorMesPorNatureza.getOrDefault(Natureza.SAIDA, Map.of()).getOrDefault(mes, BigDecimal.ZERO);
            BigDecimal investimento = totaisPorMesPorNatureza.getOrDefault(Natureza.INVESTIMENTO, Map.of()).getOrDefault(mes, BigDecimal.ZERO);
            BigDecimal saldo = entrada.subtract(saida);

            entradaMensal.add(entrada.doubleValue());
            saidaMensal.add(saida.doubleValue());
            investimentoMensal.add(investimento.doubleValue());
            saldoMensal.add(saldo.doubleValue());
        }

        ModelAndView mav = new ModelAndView("/grafico");
        mav.addObject("labels", labels);
        mav.addObject("valores", valoresDouble);
        mav.addObject("qtdTransacoes", qtdTransacoes);
        mav.addObject("ano", ano);
        mav.addObject("conta", conta);
        mav.addObject("anosDisponiveis", anosDisponiveis);
        mav.addObject("anoSelecionado", ano);
        mav.addObject("orcamentoLabels", orcamentoLabels);
        mav.addObject("entradaMensal", entradaMensal);
        mav.addObject("saidaMensal", saidaMensal);
        mav.addObject("investimentoMensal", investimentoMensal);
        mav.addObject("saldoMensal", saldoMensal);
        mav.addObject("isAdmin", isAdmin);

        return mav;
    }
}
