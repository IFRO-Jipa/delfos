package br.com.delfos.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class QuestionarioDAOTest {

	@Autowired
	private PesquisaDAO dao;

	@Autowired
	private PessoaDAO daoPessoa;

	@Autowired
	private QuestionarioDAO daoQuestionario;

	@Test
	public void mostraQuestionariosParaOEspecialista() {
		System.out.println("--------------------");
		Pessoa especialista = daoPessoa.findOne(1L);

		System.out.println(especialista.getNome());

		List<Pesquisa> pesquisas = dao.findByEspecialista(especialista);

		pesquisas.forEach(pesquisa -> {
			System.out.println("Código pesquisa: " + pesquisa.getId());
			System.out.println("Nome da pesquisa: " + pesquisa.getNome());
			System.out.println("Responsáveis: " + pesquisa.getPesquisadores());
			System.out.println("Questionários: ");
			pesquisa.getQuestionarios().forEach(questionario -> System.out.println("    -" + questionario));
		});

		Assert.assertNotNull(pesquisas);
	}

	@Test
	public void mostraQuestionariosDaPesquisa() {
		System.out.println("--------------------");
		List<Questionario> questionarios = daoQuestionario.findByPesquisa(dao.findOne(1L));

		questionarios.forEach(System.out::println);

		Assert.assertNotNull(questionarios);

	}
}
