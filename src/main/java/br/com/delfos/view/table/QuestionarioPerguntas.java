package br.com.delfos.view.table;

import javafx.beans.property.SimpleStringProperty;

public class QuestionarioPerguntas {
	
	private final SimpleStringProperty nome;
	private final SimpleStringProperty tipoPergunta;
	
	public QuestionarioPerguntas(SimpleStringProperty nome, SimpleStringProperty tipoPergunta) {
		this.nome = nome;
		this.tipoPergunta = tipoPergunta;
	}

	public SimpleStringProperty getNome() {
		return nome;
	}

	public SimpleStringProperty getTipoPergunta() {
		return tipoPergunta;
	}
	
	
	
	

}
