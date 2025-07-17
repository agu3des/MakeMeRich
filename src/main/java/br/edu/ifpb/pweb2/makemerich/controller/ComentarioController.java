package br.edu.ifpb.pweb2.makemerich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.makemerich.model.Comentario;
import br.edu.ifpb.pweb2.makemerich.model.Transacao;
import br.edu.ifpb.pweb2.makemerich.service.ComentarioService;
import br.edu.ifpb.pweb2.makemerich.service.TransacaoService;


@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private ComentarioService comentarioService;

    // Listar comentários de uma transação
    @GetMapping("/listar/{idTransacao}")
    public ModelAndView listarComentarios(@PathVariable Integer idTransacao, ModelAndView model) {
        Transacao transacao = transacaoService.buscarPorId(idTransacao);
        model.addObject("transacao", transacao);
        model.addObject("comentarios", transacao.getComentarios());
        model.setViewName("comentarios/list");
        return model;
    }

    // Form para criar novo comentário
    @GetMapping("/novo/{idTransacao}")
    public ModelAndView novoComentario(@PathVariable Integer idTransacao, ModelAndView model) {
        Comentario comentario = new Comentario();
        Transacao transacao = transacaoService.buscarPorId(idTransacao);
        comentario.setTransacao(transacao);
        model.addObject("comentario", comentario);
        model.setViewName("comentarios/form");
        return model;
    }

    // Salvar comentário
    @PostMapping("/salvar")
    public String salvarComentario(@ModelAttribute Comentario comentario) {
        comentarioService.salvar(comentario);
        return "redirect:/comentarios/listar/" + comentario.getTransacao().getId();
    }

    // Form editar comentário
    @GetMapping("/editar/{id}")
    public ModelAndView editarComentario(@PathVariable Integer id, ModelAndView model) {
        Comentario comentario = comentarioService.buscarPorId(id);
        model.addObject("comentario", comentario);
        model.setViewName("comentarios/form");
        return model;
    }

    // Excluir comentário
    @GetMapping("/excluir/{id}")
    public String excluirComentario(@PathVariable Integer id) {
        Comentario comentario = comentarioService.buscarPorId(id);
        Integer idTransacao = comentario.getTransacao().getId();
        comentarioService.excluir(id);
        return "redirect:/comentarios/listar/" + idTransacao;
    }
}
