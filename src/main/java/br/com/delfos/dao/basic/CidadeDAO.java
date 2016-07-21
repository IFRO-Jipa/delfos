package br.com.delfos.dao.basic;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Cidade;
import br.com.delfos.model.basic.Estado;
import br.com.delfos.repository.basic.CidadeRepository;

@Repository
public class CidadeDAO extends AbstractDAO<Cidade, Long, CidadeRepository> {

	public List<Cidade> findByEstado(Estado estado) {
		return repository.findByEstado(estado);
	}

	public Cidade findByFullName(String name) {
		return repository.findByFullName(name);
	}

	public List<Cidade> findByName(String name) {
		return repository.findByName(name);
	}

}
