// ContaController:
// 1. Crie, edite, liste e exclua contas financeiras.
// 2. Respeite as regras de permissão:
    // 2.1 Apenas admins podem excluir ou atribuir contas a outros usuários.
    // 2.2 Usuários comuns só podem manipular suas próprias contas.
// 5. Use Thymeleaf para renderizar formulários e listas de contas.

package br.edu.ifpb.pweb2.makemerich.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.makemerich.model.Categoria;
import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.model.Natureza;
import br.edu.ifpb.pweb2.makemerich.service.ContaService;
import br.edu.ifpb.pweb2.makemerich.service.CorrentistaService;
import br.edu.ifpb.pweb2.makemerich.service.TransacaoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contas")
@PreAuthorize("hasRole('ADMIN', 'CLIENTE')")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private TransacaoService transacaoService;

    @RequestMapping("/form")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ModelAndView getForm(ModelAndView modelAndView, Authentication authentication) {
        modelAndView.setViewName("contas/form");
        
        String username = authentication.getName();
        Correntista usuarioLogado = correntistaService.findByUsername(username);
        
        if (usuarioLogado == null) {
            // Pode ser admin sem correntista
            boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            if (isAdmin) {
                // Para admin, cria conta sem correntista definido (admin escolhe no form)
                Conta conta = new Conta();
                conta.setCorrentista(new Correntista()); // vazio para escolha
                modelAndView.addObject("conta", conta);
                modelAndView.addObject("isAdmin", true);
                return modelAndView;
            } else {
                // Usuário sem correntista e sem admin -> redireciona para login
                return new ModelAndView("redirect:/auth/login");
            }
        }
        
        // Se tiver correntista
        Conta conta = new Conta();
        if (!usuarioLogado.getUser().isAdmin()) {
            conta.setCorrentista(usuarioLogado);
        } else {
            conta.setCorrentista(new Correntista());
        }
        
        modelAndView.addObject("conta", conta);
        modelAndView.addObject("isAdmin", usuarioLogado.getUser().isAdmin());
        return modelAndView;
    }


    @ModelAttribute("correntistaItems")
    public List<Correntista> getCorrentistas() {
        return correntistaService.findAll();
    }

    @ModelAttribute("menu")
    public String selectMenu() {
        return "conta";
    }

    @GetMapping("/orcamento/anual")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ModelAndView showOrcamentoAnualTodasContas(ModelAndView modelAndView,
    @RequestParam(value = "ano", required = false) Integer ano,
    @RequestParam(value = "correntistaId", required = false) Integer correntistaId,
    @RequestParam(value = "exibirSelecaoCorrentista", required = false, defaultValue = "true") boolean exibirSelecaoCorrentista,
    Authentication authentication) {
        try {
            // Recupera username do usuário logado
            String username = authentication.getName();
            Correntista usuarioLogado = correntistaService.findByUsername(username);

            // Se não encontrou usuário, redireciona para login
            boolean isAdmin;
            if (usuarioLogado == null) {
                // Pode ser admin sem correntista
                isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                
                if (!isAdmin) {
                    return new ModelAndView("redirect:/auth/login");
                }
            }  else {
                    isAdmin = usuarioLogado.getUser().isAdmin();
            }
            

            int anoFinal = (ano != null) ? ano : LocalDate.now().getYear();

            // Define o correntista alvo: próprio usuário ou escolhido pelo admin
            Correntista correntistaAlvo = (usuarioLogado != null) ? usuarioLogado : null;
            if (isAdmin && correntistaId != null) {
                correntistaAlvo = correntistaService.findById(correntistaId);
            }

            // Busca as contas do correntista alvo
            List<Conta> contas = contaService.findByCorrentista(correntistaAlvo);

            // Processamento do orçamento
            Map<Natureza, Map<Categoria, Map<Integer, BigDecimal>>> orcamentoConsolidado = 
                transacaoService.calcularOrcamentoConsolidado(contas, anoFinal);
            Map<Natureza, Map<Categoria, BigDecimal>> totaisPorNatureza = 
                transacaoService.calcularTotaisPorNatureza(orcamentoConsolidado);
            Map<Natureza, Map<Integer, BigDecimal>> totaisPorMesPorNatureza = 
                transacaoService.calcularTotaisMensaisPorNatureza(orcamentoConsolidado);
            transacaoService.ordenarOrcamentoPorCategoria(orcamentoConsolidado);
            BigDecimal saldoConsolidado = 
                transacaoService.calcularSaldoConsolidado(totaisPorNatureza);

            Map<Natureza, BigDecimal> totalPorNatureza = new EnumMap<>(Natureza.class);
            for (Map.Entry<Natureza, Map<Categoria, BigDecimal>> entry : totaisPorNatureza.entrySet()) {
                BigDecimal soma = entry.getValue().values().stream()
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                totalPorNatureza.put(entry.getKey(), soma);
            }

            ModelAndView mav = new ModelAndView("contas/orcamento");
            mav.addObject("noCache", true);
            mav.addObject("menu", "orcamento");
            mav.addObject("ano", anoFinal);
            mav.addObject("orcamentoPorNatureza", orcamentoConsolidado);
            mav.addObject("totaisPorNatureza", totaisPorNatureza);
            mav.addObject("totalPorNatureza", totalPorNatureza);
            mav.addObject("totaisPorMesPorNatureza", totaisPorMesPorNatureza);
            mav.addObject("nomesMeses", new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"});
            mav.addObject("saldoConsolidado", saldoConsolidado);

            if (isAdmin) {
                mav.addObject("isAdmin", true);
                mav.addObject("correntistas", correntistaService.findAll());
                mav.addObject("correntistaSelecionado", correntistaAlvo);
                mav.addObject("exibirSelecaoCorrentista", exibirSelecaoCorrentista);
            }

            return mav;

        } catch (Exception e) {
            ModelAndView errorView = new ModelAndView("error");
            errorView.addObject("message", "Erro ao gerar orçamento: " + e.getMessage());
            return errorView;
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ModelAndView saveConta(
            @Valid Conta conta,
            BindingResult validation,
            ModelAndView mav,
            RedirectAttributes attr,
            Authentication authentication) {

        String username = authentication.getName();
        Correntista usuarioLogado = correntistaService.findByUsername(username);

        boolean isAdmin;
        if (usuarioLogado == null) {
            // usuário não possui correntista — verifica se é admin pelas roles
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        } else {
            isAdmin = usuarioLogado.getUser().isAdmin();
        }

        // Se não for admin, força a conta para o próprio usuário
        if (!isAdmin) {
            // Se usuário não tem correntista (usuarioLogado == null), provavelmente erro ou validação extra
            if (usuarioLogado == null) {
                // Pode lançar exceção ou redirecionar para erro/autenticação
                throw new IllegalStateException("Usuário sem correntista associado não pode criar conta");
            }
            conta.setCorrentista(usuarioLogado);
        }

        if (validation.hasErrors()) {
            mav.setViewName("contas/form");
            return mav;
        }

        String operacao = (conta.getId() == null) ? "criada" : "salva";
        contaService.save(conta);
        attr.addFlashAttribute("mensagem", "Conta " + operacao + " com sucesso!");
        mav.setViewName("redirect:/contas");
        return mav;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ModelAndView list(Authentication authentication) {
        String username = authentication.getName();
        Correntista usuarioLogado = correntistaService.findByUsername(username);

        if (usuarioLogado == null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            if (isAdmin) {
                List<Conta> contas = contaService.findAll(); // admin vê tudo
                ModelAndView model = new ModelAndView("contas/list");
                model.addObject("contas", contas);
                model.addObject("isAdmin", true);
                return model;
            } else {
                // Se não admin e sem correntista, redirecione para login ou erro
                return new ModelAndView("redirect:/auth/login");
            }
        }

        // Se usuário tem correntista:
        List<Conta> contas = usuarioLogado.getUser().isAdmin()
                ? contaService.findAll()
                : contaService.findByCorrentistaEmail(usuarioLogado.getEmail());

        ModelAndView model = new ModelAndView("contas/list");
        model.addObject("contas", contas);
        model.addObject("isAdmin", usuarioLogado.getUser().isAdmin());
        return model;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ModelAndView edit(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView("contas/form");
        model.addObject("conta", contaService.findById(id));
        return model;
    }

    @RequestMapping("/{id}")
    public String getCorrentistaById(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("conta", contaService.findById(id));
        return "contas/form";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteById(@PathVariable(value = "id") Integer id,
            ModelAndView mav, RedirectAttributes attr) {
        contaService.deleteById(id);
        attr.addFlashAttribute("mensagem", "Conta removida com sucesso!");
        mav.setViewName("redirect:/contas");
        return mav;
    }


}
