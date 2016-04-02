package br.com.delfos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.com.delfos.view.Column;

@Entity
public class Funcionalidade {

	@Override
	public String toString() {
		return "Funcionalidade [id=" + id + ", nome=" + nome + ", chave=" + chave + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", alias = "ID")
	private Long id;
	@NotNull
	@Column(name = "nome", alias = "Nome")
	private String nome;
	@NotNull
	@Column(name = "chave", alias = "Chave de identificação")
	private String chave;
	private String descricao;

	@OneToOne
	private Funcionalidade preRequisito;

	public Funcionalidade() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		this.id = idFuncionalidade;
		this.nome = nome;
		this.chave = chave;
		this.descricao = descricao;
		this.preRequisito = preRequisito;
	}

	public Long getId() {
		return id;
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

	public String getChave() {
		return chave;
	}

}
