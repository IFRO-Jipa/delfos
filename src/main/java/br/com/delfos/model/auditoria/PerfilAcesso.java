package br.com.delfos.model.auditoria;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import br.com.delfos.model.generic.AbstractModel;

@Entity
public class PerfilAcesso extends AbstractModel<PerfilAcesso> {

	private String nome;
	private String descricao;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Funcionalidade> permissoes;

	@OneToMany(mappedBy = "perfilAcesso")
	private List<Usuario> usuarios;

	public PerfilAcesso() {
		super();
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
		return permissoes;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((permissoes == null) ? 0 : permissoes.hashCode());
		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof PerfilAcesso)) {
			return false;
		}
		PerfilAcesso other = (PerfilAcesso) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (permissoes == null) {
			if (other.permissoes != null) {
				return false;
			}
		} else if (!permissoes.equals(other.permissoes)) {
			return false;
		}
		if (usuarios == null) {
			if (other.usuarios != null) {
				return false;
			}
		} else if (!usuarios.equals(other.usuarios)) {
			return false;
		}
		return true;
	}

}
