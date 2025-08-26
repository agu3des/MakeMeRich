package br.edu.ifpb.pweb2.makemerich.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    
    List<Transacao> findByContaId(Integer contaId);
    @Query("SELECT t FROM Transacao t " +
           "WHERE t.conta = :conta " +
           "AND YEAR(t.data) = :ano")
           
    List<Transacao> findByContaAndYear(
        @Param("conta") Integer conta,
        @Param("ano") int ano
    );

    Page<Transacao> findByContaIdOrderByDataDesc(Integer contaId, Pageable pageable);

    Page<Transacao> findByConta_IdAndDataBetweenOrderByDataDesc(Integer idConta, LocalDate inicio, LocalDate fim, Pageable pageable);

    List<Transacao> findByDataBetween(LocalDate inicio, LocalDate fim);

    List<Transacao> findByDataBetweenAndConta_Correntista_Id(LocalDate inicio, LocalDate fim, Integer idUsuario);

    List<Transacao> findByConta_IdAndDataBetweenOrderByDataDesc(Integer idConta, LocalDate inicio, LocalDate fim);

    @Query("SELECT t FROM Transacao t WHERE t.conta.id IN :contaIds AND YEAR(t.data) = :ano")
    List<Transacao> findByContaIdInAndYear(
        @Param("contaIds") List<Integer> contaIds, 
        @Param("ano") int ano
    );

    @Query("SELECT t.categoria, MONTH(t.data), SUM(t.valor * CASE WHEN t.movimento = 'CREDITO' THEN 1 ELSE -1 END) "
     + "FROM Transacao t "
     + "WHERE t.conta IN :contas AND YEAR(t.data) = :ano "
     + "GROUP BY t.categoria, MONTH(t.data)")
        List<Object[]> getTotaisAgrupados(@Param("contas") List<Conta> contas, @Param("ano") int ano);

    @Query("SELECT MONTH(t.data), SUM(t.valor) FROM Transacao t " +
            "WHERE t.conta.id = :idConta " +
            "AND YEAR(t.data) = :ano " +
            "GROUP BY MONTH(t.data) " +
            "ORDER BY MONTH(t.data)")
    List<Object[]> totalPorMesAnoConta(@Param("idConta") Integer idConta, @Param("ano") Integer ano);

    @Query("SELECT MONTH(t.data), COUNT(t) " +
            "FROM Transacao t " +
            "WHERE t.conta.id = :idConta AND YEAR(t.data) = :ano " +
            "GROUP BY MONTH(t.data)")
    List<Object[]> qtdPorMesAnoConta(@Param("idConta") Integer idConta,
                                    @Param("ano") Integer ano);

}

