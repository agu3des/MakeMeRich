// TransacaoController:
// Componente Spring responsável por controlar a navegação e operações relacionadas às transações financeiras associadas a contas no sistema. 
// A classe permite ações como criar, editar, salvar e listar transações, sempre verificando as permissões do usuário logado.
// Autorização: Verifica se o usuário logado tem permissão para acessar a conta/transação (exceto se for admin).
// Views envolvidas:
//     transacoes/form: formulário de nova ou edição de transação.
//     transacoes/list: listagem de transações da conta.
//     error: página de erro personalizada.
// Serviços usados:
//     TransacaoService: lida com persistência de transações.
//     ContaService: acessa dados das contas.
//     CategoriaService: fornece categorias para preenchimento do formulário.
// Redirecionamentos e mensagens: uso de RedirectAttributes para feedbacks de sucesso e erro.

package br.edu.ifpb.pweb2.makemerich.controller;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.makemerich.model.Categoria;
import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.model.Transacao;
import br.edu.ifpb.pweb2.makemerich.service.CategoriaService;
import br.edu.ifpb.pweb2.makemerich.service.ContaService;
import br.edu.ifpb.pweb2.makemerich.service.CorrentistaService;
import br.edu.ifpb.pweb2.makemerich.service.TransacaoService;
import br.edu.ifpb.pweb2.makemerich.ui.NavPage;
import br.edu.ifpb.pweb2.makemerich.ui.NavePageBuilder;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private CategoriaService categoriaService;

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                   .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private Correntista getCorrentista(Authentication auth) {
        return correntistaService.findByUsername(auth.getName());
    }

    @GetMapping("/nova/{idConta}")
    public ModelAndView nova(@PathVariable(value = "idConta") Integer idConta, Authentication auth) throws AccessDeniedException {
        Conta conta = contaService.findById(idConta);
        Correntista usuario = getCorrentista(auth);

        if (!isAdmin(auth) && (usuario == null || !conta.getCorrentista().getId().equals(usuario.getId()))) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setCategoria(new Categoria());

        ModelAndView mav = new ModelAndView("transacoes/form");
        mav.addObject("conta", conta);
        mav.addObject("transacao", transacao);
        mav.addObject("categorias", categoriaService.buscarAtivasOrdenadas());
        return mav;
    }


    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Transacao transacao, BindingResult result, RedirectAttributes attr, Authentication auth) throws AccessDeniedException {

        Correntista usuario = getCorrentista(auth);
        Conta conta = contaService.findDistinctByIdWithTransacoes(transacao.getConta().getId());

        if (!isAdmin(auth) && (usuario == null || !conta.getCorrentista().getId().equals(usuario.getId()))) {
            throw new AccessDeniedException("Ação não permitida");
        }

        if (transacao.getCategoria() != null && transacao.getCategoria().getId() != null) {
            Categoria categoria = categoriaService.findByIdCategoria(transacao.getCategoria().getId());
            transacao.setCategoria(categoria);
        } else {
            transacao.setCategoria(null);
        }

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("transacoes/form");
            mav.addObject("conta", conta);
            mav.addObject("transacao", transacao);
            mav.addObject("categorias", categoriaService.buscarAtivasOrdenadas());
            return mav;
        }

        transacao.setConta(conta);

        if (transacao.getId() == null) {
            conta.addTransacao(transacao);
            contaService.save(conta);
        } else {
            transacaoService.salvar(transacao);
        }

        attr.addFlashAttribute("mensagem", "Transação salva com sucesso!");
        return new ModelAndView("redirect:/transacoes/listar/" + conta.getId());
    }

    @PostMapping("/selecionar")
    public ModelAndView processarSelecao( @RequestParam("nuConta") Integer idConta, @RequestParam("acao") String acao, RedirectAttributes redirect) {
        if (acao.equals("nova")) {
            return new ModelAndView("redirect:/transacoes/nova/" + idConta);
        } else if (acao.equals("listar")) {
            return new ModelAndView("redirect:/transacoes/listar/" + idConta);
        }

        redirect.addFlashAttribute("mensagem", "Ação inválida");
        return new ModelAndView("redirect:/transacoes");
    }

    @GetMapping("/editar/{idTransacao}")
    public ModelAndView editar(@PathVariable Integer idTransacao, Authentication auth) throws AccessDeniedException {
        Transacao transacao = transacaoService.findByIdTransacao(idTransacao);
        Conta conta = contaService.findDistinctByIdWithTransacoes(transacao.getConta().getId());
        Correntista usuario = getCorrentista(auth);

        if (!isAdmin(auth) && (usuario == null || !conta.getCorrentista().getId().equals(usuario.getId()))) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        ModelAndView mav = new ModelAndView("transacoes/form");
        mav.addObject("conta", conta);
        mav.addObject("transacao", transacao);
        mav.addObject("categorias", categoriaService.buscarAtivasOrdenadas());
        return mav;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, RedirectAttributes attr) throws AccessDeniedException {
        Transacao transacao = transacaoService.findByIdTransacao(id);
        Conta conta = contaService.findDistinctByIdWithTransacoes(transacao.getConta().getId());

        conta.getTransacoes().remove(transacao); 
        contaService.save(conta);              

        attr.addFlashAttribute("mensagem", "Transação removida com sucesso!");
        return new ModelAndView("redirect:/transacoes/listar/" + conta.getId());
    }



    @GetMapping
    public ModelAndView showSelectionForm(Authentication auth) {
        Correntista usuario = getCorrentista(auth);
        
        ModelAndView mav = new ModelAndView("transacoes/form");

        if (isAdmin(auth)) {
            mav.addObject("todasContas", contaService.findAll());
        } else if (usuario != null) {
            mav.addObject("contasDoUsuario", contaService.findByCorrentista(usuario));
        }

        return mav;
    }

    @GetMapping("/listar/{idConta}")
    public ModelAndView listar(@PathVariable("idConta") Integer idConta, 
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        Authentication auth) throws AccessDeniedException {

        Conta conta = contaService.findDistinctByIdWithTransacoes(idConta);

        if (conta == null) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("message", "Conta não encontrada!");
            return mav;
        }

        Correntista usuario = getCorrentista(auth);

        if (!isAdmin(auth) && (usuario == null || !conta.getCorrentista().getId().equals(usuario.getId()))) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Transacao> pagina = transacaoService.buscarPorConta(idConta, paging);

        NavPage navPage = NavePageBuilder.newNavPage(
                pagina.getNumber() + 1,
                pagina.getTotalElements(),
                pagina.getTotalPages(),
                size
        );

        ModelAndView mav = new ModelAndView("transacoes/list");
        mav.addObject("conta", conta);
        mav.addObject("pagina", pagina);
        mav.addObject("transacoes", pagina.getContent());
        mav.addObject("navPage", navPage);
        return mav;
    }

  
    @GetMapping("/filtrar/{idConta}")
    public ModelAndView filtrarPorPeriodo(@PathVariable("idConta") Integer idConta,
                                        @RequestParam(value = "dataInicio", required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                                        @RequestParam(value = "dataFim", required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                        @RequestParam(name = "size", defaultValue = "5") int size,
                                        Authentication auth) throws AccessDeniedException {
        Correntista usuario = getCorrentista(auth);
        Conta conta = contaService.findDistinctByIdWithTransacoes(idConta);

        // segurança
        if (!isAdmin(auth) && (usuario == null || !conta.getCorrentista().getId().equals(usuario.getId()))) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        // --- VALIDAÇÃO ADICIONADA AQUI ---
        if (dataInicio != null && dataFim != null && !dataInicio.isBefore(dataFim)) {
            ModelAndView mav = new ModelAndView("transacoes/list");
            mav.addObject("conta", conta);
            mav.addObject("dataInicio", dataInicio);
            mav.addObject("dataFim", dataFim);
            mav.addObject("mensagemErro", "A data inicial deve ser antes da data final.");
            
            // Retorna os dados da listagem padrão (sem filtro) para o usuário
            Pageable paging = PageRequest.of(page - 1, size);
            Page<Transacao> pagina = transacaoService.buscarPorConta(idConta, paging);
            NavPage navPage = NavePageBuilder.newNavPage(
                pagina.getNumber() + 1,
                pagina.getTotalElements(),
                pagina.getTotalPages(),
                size
            );
            
            mav.addObject("pagina", pagina);
            mav.addObject("transacoes", pagina.getContent());
            mav.addObject("navPage", navPage);
            mav.addObject("totalPeriodo", conta.getSaldo()); // Usa o saldo total como fallback
            
            return mav;
        }
        // --- FIM DA VALIDAÇÃO ---

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Transacao> pagina;
        BigDecimal total;

        // Se os parâmetros vieram, aplica filtro
        if (dataInicio != null && dataFim != null) {
            List<Transacao> filtradas = transacaoService.buscarPorContaEPeriodo(idConta, dataInicio, dataFim);
            // Simula página manualmente
            int start = Math.min((page - 1) * size, filtradas.size());
            int end = Math.min(start + size, filtradas.size());
            List<Transacao> content = filtradas.subList(start, end);
            pagina = new org.springframework.data.domain.PageImpl<>(content, paging, filtradas.size());

            // calcula o total apenas das filtradas
            if (filtradas.isEmpty()) {
            total = BigDecimal.ZERO; // filtro sem transações
            } else {
                total = filtradas.stream()
                    .map(t -> {
                        if (t.getCategoria() != null && "SAIDA".equals(t.getCategoria().getNatureza().name())) {
                            return t.getValor().negate();
                        } else {
                            return t.getValor();
                        }
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        } else {
            // Caso não haja filtro, retorna todas as transações paginadas
            pagina = transacaoService.buscarPorConta(idConta, paging);

            // calcula o total de todas as transações da conta
            total = (conta.getSaldo() != null) ? conta.getSaldo() : BigDecimal.ZERO;
        }

        NavPage navPage = NavePageBuilder.newNavPage(
                pagina.getNumber() + 1,
                pagina.getTotalElements(),
                pagina.getTotalPages(),
                size
        );

        ModelAndView mav = new ModelAndView("transacoes/list");
        mav.addObject("conta", conta);
        mav.addObject("pagina", pagina);
        mav.addObject("transacoes", pagina.getContent());
        mav.addObject("navPage", navPage);
        mav.addObject("dataInicio", dataInicio);
        mav.addObject("dataFim", dataFim);
        mav.addObject("totalPeriodo", total);

        return mav;
    }

}