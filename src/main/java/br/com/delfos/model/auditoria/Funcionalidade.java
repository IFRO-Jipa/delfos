package br.com.delfos.model.auditoria;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.view.table.Column;

@Entity
public class Funcionalidade extends AbstractModel<Funcionalidade> {

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
		super(idFuncionalidade);
		this.nome = nome;
		this.chave = chave;
		this.descricao = descricao;
		this.preRequisito = preRequisito;
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

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chave == null) ? 0 : chave.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((preRequisito == null) ? 0 : preRequisito.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionalidade other = (Funcionalidade) obj;
		if (chave == null) {
			if (other.chave != null)
				return false;
		} else if (!chave.equals(other.chave))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (preRequisito == null) {
			if (other.preRequisito != null)
				return false;
		} else if (!preRequisito.equals(other.preRequisito))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionalidade [id=" + id + ", nome=" + nome + ", chave=" + chave + "]";
	}
}
