package br.edu.ifpb.pweb2.makemerich.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.makemerich.util.PasswordUtil;

@Component
public class CorrentistaService implements Service<Correntista, Integer>{

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public List<Correntista> findAll() {
        return correntistaRepository.findAll();
    }

    @Override
    public Correntista findById(Integer id) {
        return correntistaRepository.findById(id).orElse(null);
    }

    @Override
    public Correntista save(Correntista c) {
        c.setSenha(PasswordUtil.hashPassword(c.getSenha()));
       return correntistaRepository.save(c);
    }
    
}