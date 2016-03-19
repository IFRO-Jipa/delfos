package br.com.delfos.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

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

	private String uf;

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

	public Long getId() {
		return id;
	}

}
