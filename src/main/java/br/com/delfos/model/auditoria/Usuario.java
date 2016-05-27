package br.com.delfos.model.auditoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.generic.AbstractModel;

enum Status {
	ATIVO, INATIVO;
}

@Entity
public class Usuario extends AbstractModel<Usuario> {

	@NotNull
	@Size(min = 4, message = "O login deve conter, no m�nimo, quatro caracteres.")
	private String login;
	
	@Column(unique=true)
	@NotNull
	@Size(min = 6, message = "A senha deve conter, no m�nimo, seis caracteres.")
	private String senha;

	private String descricao;

	@ManyToOne(fetch = FetchType.EAGER)
	private PerfilAcesso perfilAcesso;

	@OneToOne(mappedBy = "usuario", optional = true)
	private Pessoa pessoa;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Usuario(Usuario usuario) {
		this.id = usuario.id;
		this.login = usuario.login;
		this.senha = usuario.senha;
		this.descricao = usuario.descricao;
		this.perfilAcesso = usuario.perfilAcesso;
		this.status = usuario.status;
	}

	public void setStatus(boolean status) {
		if (status) {
			this.status = Status.ATIVO;
		} else {
			this.status = Status.INATIVO;
		}
	}

	public boolean isAtivo() {
		return this.status == Status.ATIVO;
	}

	public Usuario() {
	}

	public Usuario(String login, String senha, PerfilAcesso perfilAcesso) {
		super();
		this.login = login;
		this.senha = senha;
		this.perfilAcesso = perfilAcesso;
	}

	public Usuario(String login, String senha, String descricao, PerfilAcesso perfilAcesso) {
		super();
		this.login = login;
		this.senha = senha;
		this.descricao = descricao;
		this.perfilAcesso = perfilAcesso;
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

	public PerfilAcesso getPerfilAcesso() {
		return perfilAcesso;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
		pessoa.setUsuario(this);
	}

	public void setPerfilAcesso(PerfilAcesso perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", descricao=" + descricao
		        + ", perfilAcesso=" + perfilAcesso + "]";
	}

}
