package br.edu.ifpb.pweb2.makemerich.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    public Categoria(Long id, String nome, Natureza natureza, Integer ordem, boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.natureza = natureza;
        this.ordem = ordem;
        this.ativa = ativa;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @NotNull(message = "Selecione a natureza")
    @Enumerated(EnumType.STRING)
    private Natureza natureza; // ENTRADA, SAIDA, INVESTIMENTO

    private Integer ordem;

    private boolean ativa = true; // usado para desativar sem excluir

    @OneToMany(mappedBy = "categoria")
    private List<Transacao> transacoes = new ArrayList<>();
}