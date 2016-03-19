package br.com.delfos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class TipoLogradouro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(unique = true)
	private String nome;
	private String descricao;
	private String sigla;

	public TipoLogradouro(Long id, String nome, String descricao, String sigla) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.sigla = sigla;
	}

	public TipoLogradouro(String nome, String sigla) {
		this.nome = nome;
		this.sigla = sigla;
	}

	public TipoLogradouro(String nome, String descricao, String sigla) {
		this.nome = nome;
		this.descricao = descricao;
		this.sigla = sigla;
	}

	public TipoLogradouro() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public String toString() {
		return String.format("%s-%s", this.sigla, this.nome);
	}

}
