package br.com.delfos.model.pesquisa.pergunta;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.com.delfos.model.generic.AbstractModel;

@Entity
public class Pergunta<T extends Alternativa> extends AbstractModel<Pergunta<T>> {

	@NotNull
	private String nome;
	private String descricao;

	@OneToOne(targetEntity = Alternativa.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, orphanRemoval = true)
	private T alternativa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setAlternativa(T alternativa) {
		this.alternativa = alternativa;
	}

	public T getAlternativa() {
		return alternativa;
	}

	public TipoPergunta getTipo() {
		return this.alternativa.getTipo();
	}

	public Pergunta() {
	}

	public Pergunta(String nome, T alternativa) {
		this(nome, nome, alternativa);
	}

	public Pergunta(String nome, String descricao, T alternativa) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.alternativa = alternativa;
	}

	public Pergunta(Long id, String nome, String descricao, T alternativa) {
		super(id);
		this.nome = nome;
		this.descricao = descricao;
		this.alternativa = alternativa;
	}

	@Override
	public String toString() {
		return "Pergunta [nome=" + nome + ", descricao=" + descricao + ", alternativa=" + alternativa + ", id=" + id
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((alternativa == null) ? 0 : alternativa.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		if (!(obj instanceof Pergunta)) {
			return false;
		}
		Pergunta<?> other = (Pergunta<?>) obj;
		if (alternativa == null) {
			if (other.alternativa != null) {
				return false;
			}
		} else if (!alternativa.equals(other.alternativa)) {
			return false;
		}
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
		return true;
	}

}