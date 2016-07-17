package br.com.delfos.dao.basic;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.repository.basic.PessoaRepository;

@Repository
public class PessoaDAO extends AbstractDAO<Pessoa, Long, PessoaRepository> {

	public Set<Pessoa> findByTipo(TipoPessoa tipo) {
		return repository.findByTipo(tipo);
	}

	public List<Pessoa> findByUsuarioIsNull() {
		return repository.findByUsuarioIsNull();
	}

}
