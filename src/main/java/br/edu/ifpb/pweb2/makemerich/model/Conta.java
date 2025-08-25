// Conta:
// 1. Representa uma conta com número, tipo, descrição e data de fechamento.
// 2. Está ligada a um Correntista (dono da conta).
// 3. Possui várias Transacao ligadas a ela.
// 4. Calcula o saldo com base nas transações (entradas e saídas).
// 5. Usa JPA para persistência no banco e Lombok para evitar código repetitivo.

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
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "\\d{6}", message = "Número deve ter 6 dígitos numéricos")
    private String numero;


    @NotBlank(message="Campo obrigatório")
    private String descricao;
    
    @NotBlank(message="Campo obrigatório")
    private String tipo;
    
    private Integer diaFechamento;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy("data desc")
    private Set<Transacao> transacoes;


    @ManyToOne
    @JoinColumn(name = "id_correntista")
     @ToString.Exclude
    private Correntista correntista;

    public Conta(Correntista correntista) {
        this.transacoes = new HashSet<>();
        this.correntista = correntista;
    }

    public BigDecimal getSaldo() {
        BigDecimal total = BigDecimal.ZERO;

        for (Transacao t : this.transacoes) {
            BigDecimal valor = t.getValor();
            Movimento movimento = t.getMovimento();

            if (valor != null && movimento != null && movimento != null) {
                switch (movimento) {
                    case CREDITO -> total = total.add(valor);
                    case DEBITO -> total = total.subtract(valor);
                    default -> total = total.subtract(valor);
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
