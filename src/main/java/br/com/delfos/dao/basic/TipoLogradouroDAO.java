package br.com.delfos.dao.basic;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.TipoLogradouro;
import br.com.delfos.repository.basic.TipoLogradouroRepository;

@Repository
public class TipoLogradouroDAO extends AbstractDAO<TipoLogradouro, Long, TipoLogradouroRepository> {

	public List<TipoLogradouro> findByNome(String nome) {
		return repository.findByNome(nome);
	}

	public TipoLogradouro findBySigla(String sigla) {
		return repository.findBySigla(sigla);
	}

}
