package br.com.delfos.model;

import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PerfilAcesso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String descricao;

	private Map<Funcionalidade, Boolean> permissoes;

	public Map<Funcionalidade, Boolean> getPermissoes() {
		return permissoes;
	}

	public PerfilAcesso(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}

	public PerfilAcesso(String nome) {
		super();
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

}
