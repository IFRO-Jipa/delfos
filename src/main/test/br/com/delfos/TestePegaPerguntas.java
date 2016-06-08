package br.com.delfos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.model.pesquisa.Questionario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class TestePegaPerguntas {

	@Autowired
	private QuestionarioDAO questionarioDAO;

	@Test
	public void testa() {
		Long id = 16L;

		Questionario quest = questionarioDAO.findOne(id);

		System.out.println(quest);

	}

}
