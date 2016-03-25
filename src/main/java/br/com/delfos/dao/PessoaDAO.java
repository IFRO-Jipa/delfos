package br.com.delfos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.Pessoa;
import br.com.delfos.repository.PessoaRepository;

@Repository
public class PessoaDAO {

	@Autowired
	private PessoaRepository repository;


	public long count() {
		return repository.count();
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<Pessoa> findAll() {
		return repository.findAll();
	}

	public List<Pessoa> findAll(Sort arg0) {
		return repository.findAll(arg0);
	}

	public Pessoa findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public Pessoa getOne(Long arg0) {
		return repository.getOne(arg0);
	}

	public <S extends Pessoa> List<S> save(Iterable<S> arg0) {
		return repository.save(arg0);
	}

	public <S extends Pessoa> S save(S arg0) {
		return repository.save(arg0);
	}

}
