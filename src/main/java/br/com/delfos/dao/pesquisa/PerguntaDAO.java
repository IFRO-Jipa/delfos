package br.com.delfos.dao.pesquisa;

import java.util.Set;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pergunta;
import br.com.delfos.repository.pesquisa.PerguntaRepository;

@Repository
public class PerguntaDAO extends AbstractDAO<Pergunta<?>, Long, PerguntaRepository> {

	public Set<Pergunta<?>> findByQuestionario(Long id) {
		return repository.findByQuestionario(id);
	}

}
