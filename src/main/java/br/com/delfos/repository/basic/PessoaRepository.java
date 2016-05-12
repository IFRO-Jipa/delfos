package br.com.delfos.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.basic.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
