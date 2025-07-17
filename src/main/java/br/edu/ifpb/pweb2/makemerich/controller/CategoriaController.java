package br.edu.ifpb.pweb2.makemerich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.makemerich.model.Categoria;
import br.edu.ifpb.pweb2.makemerich.model.Natureza;
import br.edu.ifpb.pweb2.makemerich.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ModelAndView listar(ModelAndView mav) {
        mav.setViewName("categorias/list");
        mav.addObject("categorias", categoriaService.listarTodas());
        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(Categoria categoria) {
        ModelAndView mav = new ModelAndView("categorias/form");
        mav.addObject("categoria", categoria);
        return mav;
    }

    @PostMapping
    public String salvar(Categoria categoria, RedirectAttributes attr) {
        categoriaService.salvar(categoria);
        attr.addFlashAttribute("mensagem", "Categoria salva com sucesso!");
        return "redirect:/categorias";
    }

    @GetMapping("/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return form(categoria);
    }

    @GetMapping("/{id}/desativar")
    public String desativar(@PathVariable Long id, RedirectAttributes attr) {
        categoriaService.desativar(id);
        attr.addFlashAttribute("mensagem", "Categoria desativada com sucesso!");
        return "redirect:/categorias";
    }

    @ModelAttribute("naturezas")
    public Natureza[] getNaturezas() {
        return Natureza.values();
    }

    @ModelAttribute("menu")
    public String menu() {
        return "categoria";
    }
}