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

	@OneToOne(targetEntity = Alternativa.class, cascade = { CascadeType.PERSIST })
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

}