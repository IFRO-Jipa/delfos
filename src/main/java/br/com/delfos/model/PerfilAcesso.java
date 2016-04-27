package br.com.delfos.model;

import java.util.Collections;
import java.util.HashSet;
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
		this.addPermissoes(funcionalidades);
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
		return Collections.unmodifiableSet(permissoes);
	}

	public boolean remove(Funcionalidade permissao) {
		return this.permissoes.remove(permissao);
	}

	public boolean removeAll(List<Funcionalidade> permissoes) {
		return this.permissoes.removeAll(permissoes);
	}

	public void addPermissao(Funcionalidade funcionalidade) {
		if (this.permissoes == null) {
			permissoes = new HashSet<>();
		}
		this.permissoes.add(funcionalidade);
	}

	public void addPermissoes(List<Funcionalidade> funcionalidades) {
		if (this.permissoes == null) {
			permissoes = new HashSet<>();
		}
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
