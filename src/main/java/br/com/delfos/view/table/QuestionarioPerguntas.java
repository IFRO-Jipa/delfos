package br.com.delfos.view.table;

import br.com.delfos.model.pesquisa.TipoPergunta;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class QuestionarioPerguntas {
	
	private final SimpleStringProperty nome;
	private final SimpleObjectProperty<TipoPergunta> tipoPergunta;
	
	public QuestionarioPerguntas(SimpleStringProperty nome, SimpleObjectProperty<TipoPergunta> tipoPergunta ) {
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
