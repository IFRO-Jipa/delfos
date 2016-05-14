package br.com.delfos.view.table.property;

import br.com.delfos.model.pesquisa.TipoPergunta;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PerguntaProperty {

	private final SimpleStringProperty nome;
	private final SimpleObjectProperty<TipoPergunta> tipoPergunta;

	public PerguntaProperty(String nome, TipoPergunta tipoPergunta) {
		this.nome = new SimpleStringProperty(nome);
		this.tipoPergunta = new SimpleObjectProperty<TipoPergunta>(tipoPergunta);
	}

	public String getNome() {
		return nome.get();
	}

	public TipoPergunta getTipoPergunta() {
		return tipoPergunta.get();
	}

	public void setTipoPergunta(TipoPergunta tipo) {
		this.tipoPergunta.set(tipo);
	}

	public ObjectProperty<TipoPergunta> getTipoPerguntaProperty() {
		return tipoPergunta;
	}

}
