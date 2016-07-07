package br.com.delfos;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;

import br.com.delfos.model.generic.Upgradable;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;

public class TestaUpgrader implements Upgradable<Questionario> {

	@Test
	public void testaQuestionarios() {
		Questionario questionarioVazio = montaQuestionario();
		Questionario questionarioComPerguntas = montaQuestionario();
		questionarioComPerguntas.setDescricao(null);

		for (int i = 0; i < 2; i++) {
			Pergunta<Texto> pergunta = new Pergunta<>();
			pergunta.setNome("Pergunta número " + (i + 1));
			pergunta.setDescricao("Vendo se a pergunta vai ser validada corretamente.");
			pergunta.setAlternativa(new Texto());

			questionarioVazio.addPergunta(Optional.ofNullable(pergunta));
			questionarioComPerguntas.addPergunta(Optional.ofNullable(pergunta));
		}

		Pergunta<Paragrafo> paragrafo = new Pergunta<>();
		paragrafo.setNome("Opa olha só alguem foi adicionada");
		paragrafo.setDescricao("Teste");
		paragrafo.setAlternativa(new Paragrafo());

		questionarioComPerguntas.addPergunta(Optional.ofNullable(paragrafo));

		System.out.println("Antes da verificação (vazio e com perguntas)");
		System.out.println(questionarioVazio.getPerguntas());
		System.out.println("...");
		System.out.println(questionarioComPerguntas.getPerguntas());

		this.upgrade(questionarioVazio, questionarioComPerguntas);

		System.out.println("--------------------------------------------");
		System.out.println("Depois da verificação (vazio e com perguntas)");
		System.out.println(questionarioVazio.getPerguntas());
		System.out.println("...");
		System.out.println(questionarioComPerguntas.getPerguntas());

	}

	private Questionario montaQuestionario() {
		Questionario q = new Questionario();
		q.setNome("Meu questionário");
		q.setDescricao("Descrição do meu questionário");
		q.setAutenticavel(true);
		q.setDataInicio(LocalDate.now().minusDays(10));
		q.setVencimento(LocalDate.now().minusDays(2));
		q.setDataFinalizacao(LocalDate.now());
		return q;
	}
}
