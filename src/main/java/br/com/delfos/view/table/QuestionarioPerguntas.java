package br.com.delfos.view.table;

import br.com.delfos.model.pesquisa.TipoPergunta;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class QuestionarioPerguntas {

	private final SimpleStringProperty nome;
	private final SimpleObjectProperty<TipoPergunta> tipoPergunta;

	public QuestionarioPerguntas(String nome, TipoPergunta tipoPergunta) {
		this.nome = new SimpleStringProperty(nome);
		this.tipoPergunta = new SimpleObjectProperty<TipoPergunta>(tipoPergunta);
	}

	public String getNome() {
		return nome.get();
	}

	public TipoPergunta getTipoPergunta() {
		return tipoPergunta.get();
	}

}
