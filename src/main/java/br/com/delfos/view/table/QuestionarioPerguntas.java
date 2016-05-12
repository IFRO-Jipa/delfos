package br.com.delfos.view.table;

import javafx.beans.property.SimpleStringProperty;

public class QuestionarioPerguntas {
	
	private final SimpleStringProperty nome;
	private final SimpleStringProperty tipoPergunta;
	
	public QuestionarioPerguntas(SimpleStringProperty nome, SimpleStringProperty tipoPergunta) {
		this.nome = nome;
		this.tipoPergunta = tipoPergunta;
	}

	public String getNome() {
		return nome.get();
	}

	public Object getTipoPergunta() {
		return tipoPergunta.get();
	}
	
	
	
	

}
