package br.com.delfos.dao.auditoria;

import java.util.Set;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.repository.auditoria.FuncionalidadeRepository;

@Repository
public class FuncionalidadeDAO extends AbstractDAO<Funcionalidade, Long, FuncionalidadeRepository> {

	public Funcionalidade findByChave(String chave) {
		return repository.findByChave(chave);
	}

	public Set<Funcionalidade> findByPreRequisito(Funcionalidade preRequisito) {
		return repository.findByPreRequisito(preRequisito);
	}
}
