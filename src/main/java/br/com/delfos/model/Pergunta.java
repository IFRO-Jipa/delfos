package br.com.delfos.model;

import javax.persistence.OneToOne;

public class Pergunta {

	private String nome;
	private String descricao;

	private TipoPergunta tipo;

	@OneToOne(mappedBy = "perguntas", orphanRemoval = true)
	private Questionario questionario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoPergunta getTipo() {
		return tipo;
	}

	public void setTipo(TipoPergunta tipo) {
		this.tipo = tipo;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

}
