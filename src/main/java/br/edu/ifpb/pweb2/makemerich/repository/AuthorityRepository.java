package br.edu.ifpb.pweb2.makemerich.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.makemerich.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Authority.AuthorityId> {
}