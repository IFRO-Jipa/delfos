package br.com.delfos;


import java.util.Iterator;
import java.util.Set;

import br.com.delfos.dao.pesquisa.PerguntaDAO;
import br.com.delfos.model.pesquisa.Pergunta;

public class TestePegaPerguntas {
	public static void main (String[]args){
		Set<Pergunta<?>> listaPergunta = new PerguntaDAO().findByQuestionario(1l);
		
		for(Iterator<Pergunta<?>> iter = listaPergunta.iterator(); iter.hasNext();){
			Pergunta<?> pergunta = iter.next();
			System.out.println(pergunta.getNome());
		}
		
		
	}

}
