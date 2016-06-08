package br.com.delfos;


import java.util.Iterator;
import java.util.Set;

import br.com.delfos.dao.pesquisa.PerguntaDAO;
import br.com.delfos.model.pesquisa.Pergunta;

public class TestePegaPerguntas {
	public static void main (String[]args){
		Long id = 23l;
		Set<Pergunta<?>> listaPergunta = new PerguntaDAO().findByQuestionario(id);
		
		for(Iterator<Pergunta<?>> iter = listaPergunta.iterator(); iter.hasNext();){
			Pergunta<?> pergunta = iter.next();
			System.out.println(pergunta.getNome());
		}
		
		
	}

}
