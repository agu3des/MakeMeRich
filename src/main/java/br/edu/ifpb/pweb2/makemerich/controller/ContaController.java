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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.service.CategoriaService;
import br.edu.ifpb.pweb2.makemerich.service.ContaService;
import br.edu.ifpb.pweb2.makemerich.service.CorrentistaService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/form")
    public ModelAndView getForm(HttpSession session) {
        ModelAndView model = new ModelAndView("contas/form");
        Conta conta = new Conta();
        Correntista usuario = (Correntista) session.getAttribute("usuario");

        if (!usuario.isAdmin()) {
            conta.setCorrentista(usuario);
        }

        model.addObject("conta", conta);
        return model;
    }

    @ModelAttribute("correntistaItems")
    public List<Correntista> getCorrentistas() {
        return correntistaService.findAll();
    }

    @ModelAttribute("menu")
    public String selectMenu() {
        return "conta";
    }

    @PostMapping
    public ModelAndView save(Conta conta, RedirectAttributes attr, HttpSession session) throws AccessDeniedException {
        Correntista usuario = (Correntista) session.getAttribute("usuario");

        if (!usuario.isAdmin() &&
                !conta.getCorrentista().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Ação não permitida");
        }

        contaService.save(conta);
        attr.addFlashAttribute("mensagem", "Conta salva com sucesso!");
        return new ModelAndView("redirect:/contas");
    }

    @GetMapping
    public ModelAndView list(HttpSession session) {
        ModelAndView model = new ModelAndView("contas/list");
        Correntista usuario = (Correntista) session.getAttribute("usuario");

        List<Conta> contas = usuario.isAdmin()
                ? contaService.findAll()
                : contaService.findByCorrentistaEmail(usuario.getEmail());

        model.addObject("contas", contas);
        return model;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView("contas/form");
        model.addObject("conta", contaService.findById(id));
        return model;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Integer id, HttpSession session, RedirectAttributes attr) throws AccessDeniedException {
        Correntista usuario = (Correntista) session.getAttribute("usuario");

        if (!usuario.isAdmin()) {
            throw new AccessDeniedException("Ação não permitida");
        }

        contaService.deleteById(id);
        attr.addFlashAttribute("mensagem", "Conta removida com sucesso!");
        return new ModelAndView("redirect:/contas");
    }
}
