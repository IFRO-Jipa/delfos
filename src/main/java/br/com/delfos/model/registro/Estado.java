package br.com.delfos.model.registro;

import java.util.List;

import javax.persistence.Entity;

import br.com.delfos.model.generic.AbstractModel;

@Entity
public class Estado extends AbstractModel<Estado> {

	private String nome;
	private String uf;

	@Override
	public String toString() {
		return "Estado [id=" + id + ", nome=" + nome + ", uf=" + uf + "]";
	}

	public Estado() {
		super();
	}

	public Estado(String nome, String uf) {
		super();
		this.nome = nome;
		this.uf = uf;
	}

	public Estado(String nome, String uf, List<Cidade> cidades) {
		super();
		this.nome = nome;
		this.uf = uf;
	}

	public Estado(Long id, String nome, String uf) {
		super();
		this.id = id;
		this.nome = nome;
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
