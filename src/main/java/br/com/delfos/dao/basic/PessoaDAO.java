package br.com.delfos.dao.basic;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.repository.basic.PessoaRepository;

@Repository
public class PessoaDAO extends AbstractDAO<Pessoa, Long, PessoaRepository> {

}
