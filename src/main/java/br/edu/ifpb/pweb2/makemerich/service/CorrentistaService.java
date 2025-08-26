package br.edu.ifpb.pweb2.makemerich.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.makemerich.model.Authority;
import br.edu.ifpb.pweb2.makemerich.model.Authority.AuthorityId;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.model.User;
import br.edu.ifpb.pweb2.makemerich.repository.AuthorityRepository;
import br.edu.ifpb.pweb2.makemerich.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.makemerich.repository.UserRepository;

@Component
public class CorrentistaService implements Service<Correntista, Integer>{

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


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
        // salva o correntista
        Correntista saved = correntistaRepository.save(c);

        User user = saved.getUser();

        if (user != null) {
            String username = user.getUsername();

            // sempre garante que tenha ROLE_CLIENTE
            AuthorityId clienteId = new Authority.AuthorityId(username, "ROLE_CLIENTE");
            if (!authorityRepository.existsById(clienteId)) {
                authorityRepository.save(new Authority(clienteId, user, "ROLE_CLIENTE"));
            }

            // se admin = true, adiciona ROLE_ADMIN
            AuthorityId adminId = new Authority.AuthorityId(username, "ROLE_ADMIN");
            if (c.getUser().isAdmin()) {
                if (!authorityRepository.existsById(adminId)) {
                    authorityRepository.save(new Authority(adminId, user, "ROLE_ADMIN"));
                }
            } else {
                // remove ROLE_ADMIN se existir
                if (authorityRepository.existsById(adminId)) {
                    authorityRepository.deleteById(adminId);
                }
            }
        }

        return saved;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public List<User> findEnabledUsers() {
        return userRepository.findByEnabledTrue();
    }

    public Correntista findByUsername(String username) {
        return correntistaRepository.findByUserUsername(username).orElse(null);
    }

    @Override
    public Page<Correntista> findAll(Pageable pageable) {
        return correntistaRepository.findAll(pageable);
    }


    
}