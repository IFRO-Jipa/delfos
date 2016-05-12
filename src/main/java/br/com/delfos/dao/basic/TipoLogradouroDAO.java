package br.com.delfos.dao.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.basic.TipoLogradouro;
import br.com.delfos.repository.basic.TipoLogradouroRepository;

@Repository
public class TipoLogradouroDAO {

	@Autowired
	private TipoLogradouroRepository repository;

	public long count() {
		return repository.count();
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public void delete(TipoLogradouro arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<TipoLogradouro> findAll() {
		return repository.findAll();
	}

	public TipoLogradouro findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public <S extends TipoLogradouro> S save(S arg0) {
		return repository.save(arg0);
	}

	public List<TipoLogradouro> findByNome(String nome) {
		return repository.findByNome(nome);
	}

	public TipoLogradouro findBySigla(String sigla) {
		return repository.findBySigla(sigla);
	}

}
