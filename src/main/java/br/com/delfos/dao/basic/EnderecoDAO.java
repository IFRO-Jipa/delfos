package br.com.delfos.dao.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.basic.Endereco;
import br.com.delfos.repository.basic.EnderecoRepository;

@Repository
public class EnderecoDAO {
	@Autowired
	private EnderecoRepository repository;

	public long count() {
		return repository.count();
	}

	public void delete(Endereco arg0) {
		repository.delete(arg0);
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<Endereco> findAll() {
		return repository.findAll();
	}

	public List<Endereco> findAll(Sort arg0) {
		return repository.findAll(arg0);
	}

	public Endereco findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public Endereco getOne(Long arg0) {
		return repository.getOne(arg0);
	}

	public <S extends Endereco> List<S> save(Iterable<S> arg0) {
		return repository.save(arg0);
	}

	public <S extends Endereco> S save(S arg0) {
		return repository.save(arg0);
	}
}
