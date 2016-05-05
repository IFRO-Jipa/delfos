package br.com.delfos.model;

import javax.persistence.Entity;

@Entity
public class Paragrafo extends Alternativa {

	private String texto;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
