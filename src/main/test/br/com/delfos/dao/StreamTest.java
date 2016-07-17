package br.com.delfos.dao;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.resposta.Resposta;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class StreamTest {

	@Autowired
	private RespostaDAO daoResposta;

	@Autowired
	private QuestionarioDAO daoQuestionario;

	@Test
	public void verificaContagemNoStream() {
		Set<Resposta<?>> respostas = daoResposta.findByQuestionarioAndTipoMultiplaEscolha(daoQuestionario.findOne(1L));

	}

}
