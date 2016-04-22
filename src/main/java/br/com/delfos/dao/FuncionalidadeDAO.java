package br.com.delfos.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import br.com.delfos.model.Funcionalidade;
import br.com.delfos.repository.FuncionalidadeRepository;

@Repository
public class FuncionalidadeDAO extends AbstractDAO<Funcionalidade, Long, FuncionalidadeRepository> {

	public Funcionalidade findByChave(String chave) {
		return repository.findByChave(chave);
	}

	public Set<Funcionalidade> findByPreRequisito(Funcionalidade preRequisito) {
		return repository.findByPreRequisito(preRequisito);
	}
}
