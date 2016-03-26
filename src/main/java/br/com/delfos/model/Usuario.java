package br.com.delfos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@Size(min = 4, message = "O login deve conter, no mínimo, quatro caracteres.")
	private String login;
	@NotNull
	@Size(min = 6, message = "A senha deve conter, no mínimo, seis caracteres.")
	private String senha;
	private String descricao;

	@ManyToOne
	private PerfilAcesso perfilAcesso;

	public Usuario() {
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
