package br.com.delfos.dao;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.resposta.Resposta;

public class Teste {

	public static void main(String[] args) {
		List<Resposta<?>> respostas = getRespostas();

		Stream<Month> mapeado = respostas.stream().map(resposta -> resposta.getHoraResposta().toLocalDate().getMonth());
		
		
		mapeado.forEach(System.out::println);
	}

	private static List<Resposta<?>> getRespostas() {
		List<Resposta<?>> respostas = new ArrayList<>();

		for (int i = 0; i < 30; i++) {
			Resposta<Alternativa> resposta = new Resposta<>();
			resposta.setHoraResposta(LocalDateTime.now().minusMonths(i <= 10 ? 1 : ((i > 10 && i <= 20) ? 2 : 3)));
			respostas.add(resposta);
		}

		return respostas;
	}
}
