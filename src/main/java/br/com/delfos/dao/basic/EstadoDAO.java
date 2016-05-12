package br.com.delfos.dao.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.basic.Estado;
import br.com.delfos.repository.basic.EstadoRepository;

@Repository
public class EstadoDAO {

	@Autowired
	private EstadoRepository repository;

	public long count() {
		return repository.count();
	}

	public void delete(Estado arg0) {
		repository.delete(arg0);
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<Estado> findAll() {
		return repository.findAll();
	}

	public Estado findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public Estado getOne(Long arg0) {
		return repository.getOne(arg0);
	}

	public <S extends Estado> S save(S arg0) {
		return repository.save(arg0);
	}

	public List<Estado> findByNome(String nome) {
		return repository.findByNome(nome);
	}

	public Estado findByUf(String uf) {
		return repository.findByUf(uf);
	}

	
}
