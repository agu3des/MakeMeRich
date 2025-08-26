/**
 * Controlador responsável pelo gerenciamento da autenticação de usuários.
 * 
 * Este controlador gerencia três operações principais:
 * 1. Login: Renderiza o formulário de login
 * 2. Acesso Negado: Exibe a página de acesso negado quando um usuário tenta acessar um recurso sem permissão
 * 3. Logout: Encerra a sessão do usuário e redireciona para a página inicial
 * 
 * Todas as rotas deste controlador começam com '/auth', por exemplo:
 * - /auth/login
 * - /auth/acesso-negado
 * - /auth/logout
 */
package br.edu.ifpb.pweb2.makemerich.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ModelAndView getForm(ModelAndView model) {
        model.setViewName("auth/login");
        return model;
    }

    @GetMapping(value = "/acesso-negado")
    public ModelAndView getAcessoNegado(ModelAndView model) {
        model.setViewName("/auth/acesso-negado");
        return model;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mav) {
        SecurityContextHolder.clearContext();
        mav.setViewName("redirect:/auth");
        return mav;
    }

}