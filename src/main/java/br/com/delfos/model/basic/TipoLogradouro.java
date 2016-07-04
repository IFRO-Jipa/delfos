package br.com.delfos.model.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import br.com.delfos.model.generic.AbstractModel;

@Entity
public class TipoLogradouro extends AbstractModel<TipoLogradouro> {

	@NotNull
	@Column(unique = true)
	private String nome;
	private String descricao;
	@NotNull
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
		return this.sigla + " -> " + this.nome;
	}

}
