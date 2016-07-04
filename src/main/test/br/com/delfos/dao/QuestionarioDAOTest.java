package br.com.delfos.dao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.except.pesquisa.LimiteDeEspecialistasAtingidoException;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class QuestionarioDAOTest {

	@Autowired
	private PesquisaDAO daoPesquisa;
	@Autowired
	private QuestionarioDAO daoQuestionario;

	@Autowired
	private PessoaDAO daoPessoa;

	@Test
	public void modificaInformacoesDoQuestionario() {
		Questionario questionario = daoQuestionario.findOne(71L);
		questionario.setNome("Novo nome para o questionário");
		daoQuestionario.save(questionario).ifPresent(System.out::println);
	}

	public void criaNovaPesquisaComDoisQuestionarios() throws LimiteDeEspecialistasAtingidoException {
		Pesquisa pesquisa = new Pesquisa();
		pesquisa.setNome("Pesquisa número 1");
		pesquisa.setDescricao("Essa é a minha pesquisa");
		pesquisa.setDataInicio(LocalDate.now());
		pesquisa.setDataVencimento(LocalDate.now().plusDays(100));
		pesquisa.setLimite(10);
		pesquisa.addEspecialistas(daoPessoa.findByTipo(TipoPessoa.ESPECIALISTA));
		pesquisa.addPesquisadores(daoPessoa.findByTipo(TipoPessoa.PESQUISADOR));
		Set<Questionario> set = new HashSet<>();
		set.add(criaQuestionario(pesquisa));
		pesquisa.addQuestionarios(set);

		Optional<Pesquisa> save = daoPesquisa.save(pesquisa);

		Assert.assertTrue(save.isPresent());
	}

	public void adicionaNovoQuestionarioNaPesquisa() {
		Pesquisa pesquisa = daoPesquisa.findOne(12L);

		daoQuestionario.save(criaQuestionario(pesquisa))
				.ifPresent(questionario -> pesquisa.addQuestionario(questionario));
		;
		daoPesquisa.save(pesquisa);
	}

	private Questionario criaQuestionario(Pesquisa dona) {
		Questionario questionario = new Questionario();
		questionario.setNome("Esse é um questionario pertencente à pesquisa de código: " + dona.getId());
		questionario.setDescricao("Descrição do questionário criado em ambiente de teste");
		questionario.setDataInicio(LocalDate.now());
		questionario.setVencimento(LocalDate.now().plusDays(12));
		questionario.setAutenticavel(true);

		Pergunta<Texto> perguntaTexto = new Pergunta<>();
		perguntaTexto.setNome("Pergunta de texto para o questionário");
		perguntaTexto.setAlternativa(new Texto());

		Pergunta<Paragrafo> perguntaParagrafo = new Pergunta<>();
		perguntaParagrafo.setNome("Minha pergunta de parágrafo para o questionário");
		perguntaParagrafo.setAlternativa(new Paragrafo());

		Pergunta<Intervalo> perguntaIntervalo = new Pergunta<>();
		perguntaIntervalo.setNome("Pergunta de intervalo para o questionário");
		perguntaIntervalo.setDescricao("Descrição da pergunta");
		perguntaIntervalo.setAlternativa(new Intervalo());

		questionario.addPerguntas(
				Optional.ofNullable(new HashSet<>(Arrays.asList(perguntaTexto, perguntaParagrafo, perguntaIntervalo))));

		return questionario;
	}

}
