// ComentarioController:
// Gerencia os comentários vinculados a uma transação financeira no sistema MakeMeRich. 
// É responsável por listar, criar, editar e excluir comentários.

// 1. Integrações e dependências
//     - TransacaoService: usado para buscar uma transação por ID.
//     - ComentarioService: usado para salvar, buscar, e excluir comentários.
//     - Comentario: modelo que representa um comentário associado a uma transação.
//     - Transacao: modelo que possui uma lista de comentários (List<Comentario>).

package br.edu.ifpb.pweb2.makemerich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import jakarta.validation.Valid;


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
        Transacao transacao = transacaoService.findByIdTransacao(idTransacao);
        model.addObject("transacao", transacao);
        model.addObject("comentarios", transacao.getComentarios());
        model.setViewName("comentarios/list");
        return model;
    }

    // Form para criar novo comentário
    @GetMapping("/novo/{idTransacao}")
    public ModelAndView novoComentario(@PathVariable Integer idTransacao, ModelAndView model) {
        Comentario comentario = new Comentario();
        Transacao transacao = transacaoService.findByIdTransacao(idTransacao);
        comentario.setTransacao(transacao);
        model.addObject("comentario", comentario);
        model.setViewName("comentarios/form");
        return model;
    }

    // Salvar comentário
    @PostMapping("/salvar")
    public ModelAndView salvarComentario(@Valid Comentario comentario, BindingResult result, ModelAndView model) {
        if (result.hasErrors()) {
            model.addObject("comentario", comentario); // mantém os dados do formulário
            model.setViewName("comentarios/form");
            return model;
        }
        comentarioService.salvar(comentario);
        return new ModelAndView("redirect:/comentarios/listar/" + comentario.getTransacao().getId());
    }

    // Form editar comentário
    @GetMapping("/editar/{id}")
    public ModelAndView editarComentario(@PathVariable Integer id, ModelAndView model) {
        Comentario comentario = comentarioService.findByIdComentario(id);
        model.addObject("comentario", comentario);
        model.setViewName("comentarios/form");
        return model;
    }

    // Excluir comentário
    @GetMapping("/excluir/{id}")
    public String excluirComentario(@PathVariable Integer id) {
        Comentario comentario = comentarioService.findByIdComentario(id);
        Integer idTransacao = comentario.getTransacao().getId();
        comentarioService.excluir(id);
        return "redirect:/comentarios/listar/" + idTransacao;
    }
}