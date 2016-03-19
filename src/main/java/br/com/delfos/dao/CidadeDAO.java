package br.com.delfos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.Cidade;
import br.com.delfos.repository.CidadeRepository;

@Repository
public class CidadeDAO {

	@Autowired
	private CidadeRepository repository;

	
	
	public <S extends Cidade> List<S> save(Iterable<S> arg0) {
		return repository.save(arg0);
	}

	public long count() {
		return repository.count();
	}

	public void delete(Cidade arg0) {
		repository.delete(arg0);
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<Cidade> findAll() {
		return repository.findAll();
	}

	public Cidade findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public Cidade getOne(Long arg0) {
		return repository.getOne(arg0);
	}

	public <S extends Cidade> S save(S arg0) {
		return repository.save(arg0);
	}
}
