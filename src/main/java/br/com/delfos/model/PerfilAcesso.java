package br.com.delfos.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class PerfilAcesso extends AbstractModel<PerfilAcesso> {

	private String nome;
	private String descricao;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Funcionalidade> permissoes;

	@OneToMany(mappedBy = "perfilAcesso")
	private List<Usuario> usuarios;

	public PerfilAcesso() {
	}

	public PerfilAcesso(String nome, String descricao, List<Funcionalidade> funcionalidades) {
		this.nome = nome;
		this.descricao = descricao;
		this.adicionaPermissoes(funcionalidades);
	}

	public PerfilAcesso(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Set<Funcionalidade> getPermissoes() {
		return permissoes;
	}

	public void adicionaPermissao(Funcionalidade funcionalidade) {
		if (funcionalidade != null) {
			this.permissoes.add(funcionalidade);
		}
	}

	public void adicionaPermissoes(List<Funcionalidade> funcionalidades) {
		this.permissoes.addAll(funcionalidades);
	}

	@Override
	public String toString() {
		return "PerfilAcesso [id=" + id + ", nome=" + nome + ", permissoes=" + permissoes + "]";
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
