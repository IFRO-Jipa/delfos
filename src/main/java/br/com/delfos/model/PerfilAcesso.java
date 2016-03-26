package br.com.delfos.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class PerfilAcesso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String descricao;

	@ManyToMany
	private Set<Funcionalidade> permissoes;

	@OneToMany(mappedBy = "perfilAcesso")
	private List<Usuario> usuarios;

	public PerfilAcesso(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public PerfilAcesso(String nome) {
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

}
