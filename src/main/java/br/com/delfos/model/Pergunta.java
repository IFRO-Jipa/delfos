package br.com.delfos.model;

import javax.persistence.Entity;

@Entity
public class Pergunta extends AbstractModel<Pergunta> {

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}