package br.edu.ifpb.pweb2.makemerich.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "conta")
@Entity
public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @PastOrPresent(message = "A data não pode ser no futuro")
    @NotNull(message="Campo obrigatório")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @NotBlank(message="Campo obrigatório")
    private String descricao;
    

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;
    
    @NotNull(message="Campo obrigatório")
    @Enumerated(EnumType.STRING)
    private Movimento movimento;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @NotNull(message = "Campo obrigatório")
    private Categoria categoria;

    @ManyToOne
    private Conta conta;

    @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

}
