package br.com.delfos.dao.basic;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Estado;
import br.com.delfos.repository.basic.EstadoRepository;

@Repository
public class EstadoDAO extends AbstractDAO<Estado, Long, EstadoRepository> {

	public List<Estado> findByNome(String nome) {
		return repository.findByNome(nome);
	}

	public Estado findByUf(String uf) {
		return repository.findByUf(uf);
	}

}
