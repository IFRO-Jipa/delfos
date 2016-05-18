package br.com.delfos.dao.pesquisa;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pergunta;

import br.com.delfos.repository.pesquisa.PerguntaRepository;

@SuppressWarnings("rawtypes")
@Repository
public class PerguntaDAO extends AbstractDAO<Pergunta, Long, PerguntaRepository>{
	
	@Autowired
	private PerguntaRepository repository;

	public long count() {
		return repository.count();
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}
	

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<Pergunta> findAll() {
		return repository.findAll();
	}

	public Pergunta findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public Pergunta getOne(Long arg0) {
		return repository.getOne(arg0);
	}
	
	
	
	
	
	
	
	

}
