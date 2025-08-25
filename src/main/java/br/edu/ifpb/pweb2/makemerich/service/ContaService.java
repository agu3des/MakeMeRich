package br.edu.ifpb.pweb2.makemerich.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.repository.ContaRepository;

@Component
public class ContaService implements Service<Conta, Integer> {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CorrentistaService correntistaService;

    @Override
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    @Override
    public Conta findById(Integer id) {
        return contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    @Override
    public Conta save(Conta conta) {
        Correntista correntista = correntistaService.findById(conta.getCorrentista().getId());
        conta.setCorrentista(correntista);
        return contaRepository.save(conta);
    }

    public Conta findByNumeroWithTransacoes(String nuConta){
        return contaRepository.findByNumeroWithTransacoes(nuConta);

    }

    public Conta findDistinctByIdWithTransacoes(Integer idConta) {
        return contaRepository.findDistinctByIdWithTransacoes(idConta);
    }

    public List<Conta> findByCorrentistaEmail(String email) {
        return contaRepository.findByCorrentistaEmail(email);
    }

    public void deleteById(Integer id) {
        contaRepository.deleteById(id);
    }

    public List<Conta> findByCorrentista(Correntista correntista) {
        return contaRepository.findByCorrentista(correntista);
    }

    public List<Conta> findAllWithCorrentista() {
        return contaRepository.findAllWithCorrentista();
    }

    public List<Conta> findByUsername(String username) {
        return contaRepository.findByUsername(username);
    }

    @Override
    public Page<Conta> findAll(Pageable p) {
        return contaRepository.findAll(p);
    }
   
}
