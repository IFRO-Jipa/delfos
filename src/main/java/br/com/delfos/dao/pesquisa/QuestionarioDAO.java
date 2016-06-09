package br.com.delfos.dao.pesquisa;

import java.util.Set;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pergunta;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.QuestionarioRepository;

@Repository
public class QuestionarioDAO extends AbstractDAO<Questionario, Long, QuestionarioRepository> {

	public Set<Pergunta<?>> findByQuestionario(Long id) {
		return repository.findByQuestionario(id);
	}

}
