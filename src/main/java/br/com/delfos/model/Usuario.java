package br.com.delfos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@Min(value = 5)
	private String login;
	@NotNull
	@Min(value = 6)
	private String senha;
	private String descricao;

	// private PerfilAcesso perfilAcesso;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String login, String senha, PerfilAcesso perfilAcesso) {
		super();
		this.login = login;
		this.senha = senha;
		// this.perfilAcesso = perfilAcesso;
	}

	public Usuario(String login, String senha, String descricao, PerfilAcesso perfilAcesso) {
		super();
		this.login = login;
		this.senha = senha;
		this.descricao = descricao;
		// this.perfilAcesso = perfilAcesso;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public PerfilAcesso getPerfilAcesso() {
		// return perfilAcesso;
		return null;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", descricao=" + descricao
		        + "]";
	}
}
