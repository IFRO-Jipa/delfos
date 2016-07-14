package br.com.delfos.dao;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaQuestionario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class RespostaDAOTest {

	@PersistenceContext(unitName = "mysqlDataSource")
	private EntityManager manager;

	@Autowired
	private RespostaDAO daoResposta;

	@Autowired
	private PesquisaDAO daoPesquisa;

	@Autowired
	private PessoaDAO daoPessoa;

	public void verificaSeARespostaDeQuestionarioEhEncontrada() {
		Set<RespostaQuestionario> respostas = daoResposta.getRespostaDosQuestionarios(daoPesquisa.findOne(1L),
				daoPessoa.findOne(4L));

		if (respostas != null && !respostas.isEmpty())
			respostas.forEach(System.out::println);
	}

	public void pegaTodasAsRespostasEImprime() {
		List<Resposta<?>> respostas = daoResposta.findAll();
		respostas.forEach(System.out::println);
	}

	@Test
	public void agrupaRespostasParaQuestionariosEmMeses() {
		Map<Questionario, Map<Month, Number>> respostas = daoResposta
				.getRespostasAgrupadosPelaData(daoPesquisa.findOne(2L));

		respostas.forEach((chave, valor) -> {
			System.out.printf("%s = %s\n", chave, valor);
		});
	}
}
