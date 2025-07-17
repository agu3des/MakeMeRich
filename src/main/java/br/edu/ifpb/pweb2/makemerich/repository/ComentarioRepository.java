package br.edu.ifpb.pweb2.makemerich.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.makemerich.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    
    // Buscar todos os comentários de uma transação
    List<Comentario> findByTransacaoId(Integer transacaoId);
}
