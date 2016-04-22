package br.com.delfos.model;

import javax.persistence.Entity;

@Entity
public class Questionario extends AbstractModel<Questionario> {

	private String nome;

	public String getNome() {
		return nome;
	}

}
