package br.com.delfos.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.Funcionalidade;
import br.com.delfos.repository.FuncionalidadeRepository;

@Repository
public class FuncionalidadeDAO {
	
	@Autowired
	private FuncionalidadeRepository repository;

	public long count() {
		return repository.count();
	}

	public void delete(Funcionalidade arg0) {
		repository.delete(arg0);
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<Funcionalidade> findAll() {
		return repository.findAll();
	}

	public List<Funcionalidade> findAll(Sort arg0) {
		return repository.findAll(arg0);
	}

	public Funcionalidade findByChave(String chave) {
		return repository.findByChave(chave);
	}

	public Set<Funcionalidade> findByPreRequisito(Funcionalidade preRequisito) {
		return repository.findByPreRequisito(preRequisito);
	}

	public <S extends Funcionalidade> List<S> save(Iterable<S> arg0) {
		return repository.save(arg0);
	}

	public <S extends Funcionalidade> S save(S arg0) {
		return repository.save(arg0);
	}
	
	
}
