package br.edu.ifpb.pweb2.makemerich.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.makemerich.model.Comentario;
import br.edu.ifpb.pweb2.makemerich.repository.ComentarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Salvar novo comentário ou atualizar
    public Comentario salvar(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    // Buscar por id
    public Comentario findByIdComentario(Integer id) {
        Optional<Comentario> opt = comentarioRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("Comentário não encontrado com id: " + id));
    }

    // Buscar todos os comentários de uma transação
    public List<Comentario> buscarPorTransacao(Integer transacaoId) {
        return comentarioRepository.findByTransacaoId(transacaoId);
    }

    // Excluir comentário
    public void excluir(Integer id) {
        comentarioRepository.deleteById(id);
    }
}