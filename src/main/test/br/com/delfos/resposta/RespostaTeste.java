package br.com.delfos.resposta;

import java.util.Optional;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;

public class RespostaTeste {
	public static void main(String[] args) {
		Pergunta<MultiplaEscolha> multiplaEscolha = new Pergunta<MultiplaEscolha>();
		multiplaEscolha.setNome("Questão de múltipla escolha");
		MultiplaEscolha alternativaMultiplaEscolha = new MultiplaEscolha();
		alternativaMultiplaEscolha.add("ALTO", 0.3);
		alternativaMultiplaEscolha.add("MEDIO", 0.5);
		alternativaMultiplaEscolha.add("BAIXO", 0.4);
		multiplaEscolha.setAlternativa(alternativaMultiplaEscolha);

		Optional<Pessoa> especialista = Optional.empty();
		RespostaMultiplaEscolha resposta = new RespostaMultiplaEscolha();
		resposta.setPergunta(multiplaEscolha);
		resposta.setExpert(especialista);
		resposta.setEscolha("ALTO");

		System.out.println("Valor da resposta selecionada em multipla escolha: " + resposta.getValor());

		Pergunta<Intervalo> intervalo = new Pergunta<Intervalo>();
		intervalo.setNome("Questão de intervalo");
		Intervalo alternativaIntervalo = new Intervalo();
		intervalo.setAlternativa(alternativaIntervalo);

		RespostaIntervalo respostaIntervalo = new RespostaIntervalo();
		respostaIntervalo.setPergunta(intervalo);
		respostaIntervalo.setExpert(especialista);
		respostaIntervalo.setEscolha(4);

		System.out.printf("Valor da resposta selecionada em intervalo de valores: %.2f\n",
				respostaIntervalo.getValor());

		Pergunta<Texto> texto = new Pergunta<Texto>();
		texto.setNome("Questão de texto");
		texto.setAlternativa(new Texto());

		RespostaTexto respostaTexto = new RespostaTexto();
		respostaTexto.setPergunta(texto);
		respostaTexto.setExpert(especialista);
		respostaTexto.setEscolha("minha resposta em texto aqui. Não é um parágrafo");

		Pergunta<Paragrafo> paragrafo = new Pergunta<Paragrafo>();
		paragrafo.setNome("Questão de Parágrafo");
		paragrafo.setAlternativa(new Paragrafo());

		RespostaParagrafo respostaParagrafo = new RespostaParagrafo();
		respostaParagrafo.setPergunta(paragrafo);
		respostaParagrafo.setExpert(especialista);
		respostaParagrafo.setEscolha("Meu paragrafo aqui, essa resposta é equivalente a um parágrafo de texto");
	}
}
