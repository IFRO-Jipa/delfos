package br.com.delfos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
