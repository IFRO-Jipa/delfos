package br.com.delfos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Funcionalidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFuncionalidade;
	@NotNull
	private String nome;
	@NotNull
	private String chave;
	private String descricao;

	@OneToOne
	private Funcionalidade preRequisito;

	public Funcionalidade(String nome, String chave, String descricao, Funcionalidade funcionalidade) {
		this.nome = nome;
		this.chave = chave;
		this.descricao = descricao;
		this.preRequisito = funcionalidade;
	}

	public Funcionalidade(String nome, String chave, Funcionalidade preRequisito) {
		this.nome = nome;
		this.chave = chave;
		this.preRequisito = preRequisito;
	}

	public Funcionalidade(Long idFuncionalidade, String nome, String chave, String descricao,
	        Funcionalidade preRequisito) {
		super();
		this.idFuncionalidade = idFuncionalidade;
		this.nome = nome;
		this.chave = chave;
		this.descricao = descricao;
		this.preRequisito = preRequisito;
	}

	public Long getId() {
		return idFuncionalidade;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Funcionalidade getPreRequisito() {
		return preRequisito;
	}

	public void addPreRequisito(Funcionalidade funcionalidade) {
		this.preRequisito = funcionalidade;
	}

}
