package br.com.delfos.dao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.Main;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import javafx.application.Application;
import javafx.stage.Stage;

class AssNonApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class QuestionarioDAOTest {

	@Autowired
	private PesquisaDAO daoPesquisa;

	@Autowired
	private PessoaDAO daoPessoa;

	@Autowired
	private QuestionarioDAO daoQuestionario;

	@Test
	public void adicionaNovoQuestionarioNaPesquisa() {
		Questionario questionario = new Questionario();
		questionario.setNome("Nome do meu questionário");
		questionario.setDescricao("Descrição do meu questionário");
		questionario.setDataInicio(LocalDate.now());
		questionario.setVencimento(LocalDate.now().plusDays(12));
		questionario.setAutenticavel(true);

		Pergunta<Texto> perguntaTexto = new Pergunta<>();
		perguntaTexto.setNome("Minha pergunta de texto");
		perguntaTexto.setAlternativa(new Texto());

		Pergunta<Paragrafo> perguntaParagrafo = new Pergunta<>();
		perguntaParagrafo.setNome("Minha pergunta de parágrafo");
		perguntaParagrafo.setAlternativa(new Paragrafo());

		questionario.addPerguntas(Optional.ofNullable(Arrays.asList(perguntaTexto, perguntaParagrafo)));

		daoQuestionario.save(questionario);

	}

	// @Test
	// public void mostraQuestionariosParaOEspecialista() {
	// System.out.println("--------------------");
	// Pessoa especialista = daoPessoa.findOne(1L);
	//
	// System.out.println(especialista.getNome());
	//
	// List<Pesquisa> pesquisas = daoPesquisa.findByEspecialista(especialista);
	//
	// pesquisas.forEach(pesquisa -> {
	// System.out.println("Código pesquisa: " + pesquisa.getId());
	// System.out.println("Nome da pesquisa: " + pesquisa.getNome());
	// System.out.println("Responsáveis: " + pesquisa.getPesquisadores());
	// System.out.println("Questionários: ");
	// pesquisa.getQuestionarios().forEach(questionario -> System.out.println("
	// -" + questionario));
	// });
	//
	// Assert.assertNotNull(pesquisas);
	// }

	// @Test
	// public void mostraQuestionariosDaPesquisa() {
	// System.out.println("--------------------");
	// List<Questionario> questionarios =
	// daoQuestionario.findByPesquisa(daoPesquisa.findOne(1L));
	//
	// questionarios.forEach(System.out::println);
	//
	// Assert.assertNotNull(questionarios);
	//
	// }
}
