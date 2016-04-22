package br.com.delfos.dao;

import org.springframework.stereotype.Repository;

import br.com.delfos.model.Pessoa;
import br.com.delfos.repository.PessoaRepository;

@Repository
public class PessoaDAO extends AbstractDAO<Pessoa, Long, PessoaRepository> {

}
