// InicializadorCategoria: 
// Tem como objetivo popular automaticamente a base de dados com categorias financeiras predefinidas quando a aplicação Spring Boot é iniciada. 
// Isso garante que o sistema já comece com categorias úteis para o controle financeiro do usuário.

package br.edu.ifpb.pweb2.makemerich.config;

import java.util.List;

import br.edu.ifpb.pweb2.makemerich.model.Categoria;
import br.edu.ifpb.pweb2.makemerich.model.Natureza;
import br.edu.ifpb.pweb2.makemerich.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializadorCategoria implements ApplicationRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (categoriaRepository.count() == 0) {
            List<Categoria> categorias = List.of(
                new Categoria(null, "Salário"                   , Natureza.ENTRADA,      1,  true),
                new Categoria(null, "Cashback"                  , Natureza.ENTRADA,      2,  true),
                new Categoria(null, "Resgate Investimento"      , Natureza.ENTRADA,      3,  true),
                new Categoria(null, "Outras Entradas"           , Natureza.ENTRADA,      4,  true),

                new Categoria(null, "Saúde e Remédios"          , Natureza.SAIDA,        1,  true),
                new Categoria(null, "Academia e Personal"       , Natureza.SAIDA,        2,  true),
                new Categoria(null, "Carros e Uber"             , Natureza.SAIDA,        3,  true),
                new Categoria(null, "Educação e Cursos"         , Natureza.SAIDA,        4,  true),
                new Categoria(null, "Lazer e Turismo"           , Natureza.SAIDA,        5,  true),
                new Categoria(null, "Condomínio"                , Natureza.SAIDA,        6,  true),
                new Categoria(null, "Energia"                   , Natureza.SAIDA,        7,  true),
                new Categoria(null, "Celular"                   , Natureza.SAIDA,        8,  true),
                new Categoria(null, "Internet"                  , Natureza.SAIDA,        9,  true),
                new Categoria(null, "Itens Pessoais"            , Natureza.SAIDA,        10, true),
                new Categoria(null, "Feira"                     , Natureza.SAIDA,        11, true),
                new Categoria(null, "Casa"                      , Natureza.SAIDA,        12, true),
                new Categoria(null, "Impostos"                  , Natureza.SAIDA,        13, true),
                new Categoria(null, "Outros gastos"             , Natureza.SAIDA,        14, true),

                new Categoria(null, "Aporte Renda Fixa"         , Natureza.INVESTIMENTO, 1,  true),
                new Categoria(null, "Aporte Renda Variável"     , Natureza.INVESTIMENTO, 2,  true),
                new Categoria(null, "Aporte Reserva Emergencia" , Natureza.INVESTIMENTO, 3,  true),
                new Categoria(null, "Aporte Previdência"        , Natureza.INVESTIMENTO, 4,  true)
            );

            categoriaRepository.saveAll(categorias);
            System.out.println("Categorias predefinidas inseridas com sucesso.");
        } else {
            System.out.println("Categorias já existem.");
        }
    }
}