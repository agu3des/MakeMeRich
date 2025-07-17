package br.edu.ifpb.pweb2.makemerich.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.model.Transacao;
import br.edu.ifpb.pweb2.makemerich.service.CategoriaService;
import br.edu.ifpb.pweb2.makemerich.service.ContaService;
import br.edu.ifpb.pweb2.makemerich.service.TransacaoService;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/nova/{idConta}")
    public ModelAndView nova(@PathVariable Integer idConta, HttpSession session) throws AccessDeniedException {
        Conta conta = contaService.findById(idConta);
        Correntista usuario = (Correntista) session.getAttribute("usuario");

        if (!usuario.isAdmin() && !conta.getCorrentista().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        Transacao transacao = new Transacao();
        transacao.setConta(conta);

        ModelAndView mav = new ModelAndView("transacoes/form");
        mav.addObject("conta", conta);
        mav.addObject("transacao", transacao);
        mav.addObject("categorias", categoriaService.buscarAtivasOrdenadas());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute Transacao transacao, RedirectAttributes redirect) {
        Conta conta = contaService.findByIdWithTransacoes(transacao.getConta().getId());

        if (transacao.getId() == null) {
            conta.addTransacao(transacao);
            contaService.save(conta);
        } else {
            transacao.setConta(conta);
            transacaoService.salvar(transacao);
        }

        redirect.addFlashAttribute("mensagem", "Transação salva com sucesso!");
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
    public ModelAndView editar(@PathVariable Integer idTransacao, HttpSession session) throws AccessDeniedException {
        Transacao transacao = transacaoService.buscarPorId(idTransacao);
        Conta conta = contaService.findByIdWithTransacoes(transacao.getConta().getId());
        Correntista usuario = (Correntista) session.getAttribute("usuario");

        if (!usuario.isAdmin() && !conta.getCorrentista().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        ModelAndView mav = new ModelAndView("transacoes/form");
        mav.addObject("conta", conta);
        mav.addObject("transacao", transacao);
        mav.addObject("categorias", categoriaService.buscarAtivasOrdenadas());
        return mav;
    }
    @GetMapping
    public ModelAndView showSelectionForm(HttpSession session) {
        Correntista usuario = (Correntista) session.getAttribute("usuario");
        
        if (usuario == null) {
            return new ModelAndView("redirect:/auth/login");
        }

        ModelAndView mav = new ModelAndView("transacoes/form");
        
        if (usuario.isAdmin()) {
            // Admin vê todas as contas
            List<Conta> todasContas = contaService.findAll();
            mav.addObject("todasContas", todasContas);
        } else {
            // Usuário comum vê apenas suas contas
            List<Conta> contasDoUsuario = contaService.findByCorrentista(usuario);
            mav.addObject("contasDoUsuario", contasDoUsuario);
        }
        
        return mav;
    }

     @GetMapping("/listar/{idConta}")
    public ModelAndView listar(@PathVariable Integer idConta, HttpSession session) throws AccessDeniedException {
        Conta conta = contaService.findByIdWithTransacoes(idConta);
        
        if (conta == null) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("message", "Conta não encontrada!");
            return mav;
        }
        
        Correntista usuario = (Correntista) session.getAttribute("usuario");

        // Verifica se o usuário tem acesso à conta
        if (!usuario.isAdmin() && !conta.getCorrentista().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Acesso não autorizado");
        }

        ModelAndView mav = new ModelAndView("transacoes/list");
        mav.addObject("conta", conta);
        return mav;
    }
}