package br.com.delfos.view.table;

import br.com.delfos.model.pesquisa.TipoPergunta;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class QuestionarioPerguntas {
	
	private final SimpleStringProperty nome;
	private final SimpleObjectProperty<TipoPergunta> tipoPergunta;
	
	public QuestionarioPerguntas(String nome, Object tipoPergunta ) {
		this.nome = new SimpleStringProperty(nome);
		this.tipoPergunta = new SimpleObjectProperty(tipoPergunta);
	}

	public String getNome() {
		return nome.get();
	}

	public Object getTipoPergunta() {
		return tipoPergunta.get();
	}
	
}
