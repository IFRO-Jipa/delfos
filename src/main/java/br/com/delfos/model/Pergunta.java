package br.com.delfos.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Pergunta<T extends Alternativa> extends AbstractModel<T> {

	private String nome;

	@OneToOne(targetEntity=Alternativa.class)
	private T alternativa;

	public T getAlternativa() {
		return alternativa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}