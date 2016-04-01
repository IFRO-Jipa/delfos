package br.com.delfos.view;

import br.com.delfos.model.Funcionalidade;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class FuncionalidadeProperty {

	private Funcionalidade other;

	private SimpleStringProperty nome;
	private SimpleLongProperty id;
	private SimpleStringProperty chave;

	public FuncionalidadeProperty(Funcionalidade other) {
		this.other = other;
		configuraCampos();
	}

	private void configuraCampos() {
		this.nome = new SimpleStringProperty(other.getNome());
		this.chave = new SimpleStringProperty(other.getChave());
		this.id = new SimpleLongProperty(other.getId());
	}

	public String getNome() {
		return this.nome.get();
	}

	public Funcionalidade getOther() {
		return other;
	}

	public long getId() {
		return id.get();
	}

	public String getChave() {
		return chave.get();
	}

}
