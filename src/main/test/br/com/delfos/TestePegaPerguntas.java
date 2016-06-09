package br.com.delfos;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.model.pesquisa.Pergunta;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class TestePegaPerguntas {

	@Autowired
	private QuestionarioDAO questionarioDAO;

	@Test
	public void testa() {
		Long id = 23l;

		Set<Pergunta<?>> listaPergunta = questionarioDAO.findByQuestionario(id);

		for (Iterator<Pergunta<?>> iter = listaPergunta.iterator(); iter.hasNext();) {
			Pergunta<?> pergunta = iter.next();
			System.out.println(pergunta.getNome());
		}

	}

}
