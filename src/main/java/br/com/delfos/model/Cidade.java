package br.com.delfos.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Cidade extends AbstractModel<Cidade> {

	private String nome;

	@ManyToOne
	private Estado estado;

	public Cidade() {
		super();
	}

	public Cidade(String nome, Estado estado) {
		super();
		this.nome = nome;
		this.estado = estado;
	}

	public Cidade(Long id, String nome, Estado estado) {
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return String.format("%s - %s", this.nome, this.estado.getUf());
	}

}
