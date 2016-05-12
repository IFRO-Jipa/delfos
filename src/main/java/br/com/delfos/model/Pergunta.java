package br.com.delfos.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.com.delfos.model.generic.AbstractModel;

@Entity
public class Pergunta<T extends Alternativa> extends AbstractModel<Pergunta> {

	private String nome;
	private String descricao;
	
	@OneToOne(targetEntity = Alternativa.class, cascade = { CascadeType.PERSIST })
	private T alternativa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}