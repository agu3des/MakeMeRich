package br.edu.ifpb.pweb2.makemerich.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.model.User;
import br.edu.ifpb.pweb2.makemerich.service.CorrentistaService;
import br.edu.ifpb.pweb2.makemerich.ui.NavPage;
import br.edu.ifpb.pweb2.makemerich.ui.NavePageBuilder;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaService correntistaService;

    
    @GetMapping("/form")
    public ModelAndView getForm(Correntista correntista, ModelAndView model) {
        if (correntista.getUser() == null) {
            correntista.setUser(new User());
        }
        model.addObject("correntista", correntista);
        model.setViewName("correntistas/form");
        return model;
    }

   @PostMapping
    public ModelAndView save(@Valid Correntista correntista, BindingResult result, ModelAndView model, RedirectAttributes attr) {
        if (result.hasErrors()) {
            model.setViewName("correntistas/form");
            return model;
        }
        correntistaService.save(correntista);
        attr.addFlashAttribute("mensagem", "Correntista inserido com sucesso!");
        model.setViewName("redirect:/correntistas");
        return model;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView model, @RequestParam(name="page", defaultValue = "1", required = false) int page,
            @RequestParam(name="size", defaultValue = "3", required = false) int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Correntista> pageCorrentistas = correntistaService.findAll(paging);
    
        // objeto do Spring Data (Page) → tem totalPages, totalElements etc.
        model.addObject("pagina", pageCorrentistas);
    
        // só a lista de correntistas (facilita no th:each)
        model.addObject("correntistas", pageCorrentistas.getContent());
    
        // se quiser manter seu NavPage customizado
        NavPage navPage = NavePageBuilder.newNavPage(
                pageCorrentistas.getNumber() + 1,
                pageCorrentistas.getTotalElements(),
                pageCorrentistas.getTotalPages(),
                size
        );
        model.addObject("navPage", navPage);
    
        model.setViewName("correntistas/list");
        return model;
    }


    @GetMapping("/{id}")
    public ModelAndView getCorrentistaById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        Correntista correntista = correntistaService.findById(id);
        // if (correntista != null && correntista.getUser() != null) {
        //     correntistaService.setAdmin(correntista);
        // }
        model.setViewName("correntistas/form");
        model.addObject("correntista", correntista);
        return model;
    }

    @ModelAttribute("menu")
    public String selectMenu() {
        return "correntista";
    }

    @PostMapping("/bloquear")
    public String bloquear(@RequestParam("id") Integer id, @RequestParam("ativo")Boolean ativo, RedirectAttributes ra) {
        Correntista correntista = correntistaService.findById(id);
        if (correntista != null && correntista.getUser() != null) {
            User user = correntista.getUser();
            user.setEnabled(!ativo); // inverte o status
            correntistaService.saveUser(user); 
        }
        return "redirect:/correntistas";
    }

    @ModelAttribute("users")
    public List<User> getUserOptions() {
        return correntistaService.findEnabledUsers();
    }
}

