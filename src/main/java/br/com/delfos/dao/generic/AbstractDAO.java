package br.com.delfos.dao.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.delfos.model.generic.Identificator;

@SuppressWarnings("rawtypes")
@NoRepositoryBean
public abstract class AbstractDAO<Type extends Identificator, ID extends Serializable, Repository extends JpaRepository> {

	@Autowired
	protected Repository repository;

	public long count() {
		return repository.count();
	}

	@SuppressWarnings("unchecked")
	public void delete(Serializable arg0) {
		repository.delete(arg0);
	}

	@SuppressWarnings("unchecked")
	public boolean exists(Serializable arg0) {
		return repository.exists(arg0);
	}

	@SuppressWarnings("unchecked")
	public List<Type> findAll() {
		return repository.findAll();
	}

	@SuppressWarnings("unchecked")
	public List<Type> findAll(Iterable<Serializable> arg0) {
		return repository.findAll(arg0);
	}

	@SuppressWarnings("unchecked")
	public Page<Type> findAll(Pageable arg0) {
		return repository.findAll(arg0);
	}

	@SuppressWarnings("unchecked")
	public List<Type> findAll(Sort arg0) {
		return repository.findAll(arg0);
	}

	@SuppressWarnings("unchecked")
	public Type findOne(Serializable arg0) {
		return (Type) repository.findOne(arg0);
	}

	@SuppressWarnings("unchecked")
	public <S extends Type> Optional<S> save(S newValue) {
		return Optional.ofNullable((S) repository.save(newValue));
	}

	@SuppressWarnings("unchecked")
	public <S extends Type> S saveAndFlush(S arg0) {
		return (S) repository.saveAndFlush(arg0);
	}

}