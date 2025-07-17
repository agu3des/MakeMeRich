package br.edu.ifpb.pweb2.makemerich.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
    List<Conta> findByCorrentista(Correntista correntista);

    @Query("from Conta c left join fetch c.transacoes t where c.numero = :numero")
    Conta findByNumeroWithTransacoes(String numero);

    @Query("from Conta c left join fetch c.transacoes t where c.id = :id")
    Conta findByIdWithTransacoes(Integer id);

    @Query("select distinct c from Conta c left join fetch c.transacoes t where c.id = :id")
    Conta findDistinctByIdWithTransacoes(Integer id);

    List<Conta> findByCorrentistaEmail(String email);

    @Query("SELECT c FROM Conta c LEFT JOIN FETCH c.transacoes t WHERE c.id = :id ORDER BY t.data DESC")
    Conta findByIdWithTransacoesOrdered(@Param("id") Integer id);


}
