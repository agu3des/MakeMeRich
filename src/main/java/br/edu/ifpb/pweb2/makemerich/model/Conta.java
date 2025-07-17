package br.edu.ifpb.pweb2.makemerich.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private String descricao;
    private String tipo;
    private Integer diaFechamento;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private Set<Transacao> transacoes = new HashSet<Transacao>();

    @ManyToOne
    @JoinColumn(name = "id_correntista")
    private Correntista correntista;

    public Conta(Correntista correntista) {
        this.correntista = correntista;
    }

    public BigDecimal getSaldo() {
    BigDecimal total = BigDecimal.ZERO;

    for (Transacao t : this.transacoes) {
        BigDecimal valor = t.getValor();
        Categoria categoria = t.getCategoria();

        if (valor != null && categoria != null && categoria.getNatureza() != null) {
            switch (categoria.getNatureza()) {
                case ENTRADA -> total = total.add(valor);
                case SAIDA -> total = total.subtract(valor);
            }
        }
    }

    return total;
}


    public void addTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
        transacao.setConta(this);
    }



}
