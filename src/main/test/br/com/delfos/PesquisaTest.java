package br.com.delfos;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class PesquisaTest {

	@Autowired
	private PesquisaDAO dao;

	@Test
	public void testa() {
		Pesquisa pesquisa = dao.findOne(1L);

		Set<Questionario> questionarios = pesquisa.getQuestionarios();

		questionarios.forEach(questionario -> {
			System.out.println(String.format("Código: %d, Nome: %s", questionario.getId(), questionario.getNome()));
			System.out.println("Perguntas: \n");
			questionario.getPerguntas().ifPresent(lista -> lista.forEach(System.out::println));
			System.out.println("----------------------------------------");

		});
	}

}
