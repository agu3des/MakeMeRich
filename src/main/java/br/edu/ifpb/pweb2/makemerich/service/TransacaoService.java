package br.edu.ifpb.pweb2.makemerich.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.makemerich.model.Transacao;
import br.edu.ifpb.pweb2.makemerich.repository.TransacaoRepository;

@Component
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepo;

    public List<Transacao> listarPorConta(Integer contaId) {
        return transacaoRepo.findByContaId(contaId);
    }

    public Transacao buscarPorId(Integer id) {
        return transacaoRepo.findById(id).orElseThrow();
    }

    public Transacao salvar(Transacao transacao) {
        return transacaoRepo.save(transacao);
    }

    public void excluir(Integer id) {
        transacaoRepo.deleteById(id);
    }
}
