package br.com.delfos.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import br.com.delfos.converter.LocalDateTimePersistenceConverter;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.view.Column;

enum StatusDeQuestionario {
	ATIVO, INATIVO;
}

@Entity
public class Questionario extends AbstractModel<Questionario> {

	@Column(name = "nome", alias = "Nome")
	private String nome;

	@Convert(converter = LocalDateTimePersistenceConverter.class)
	private LocalTime dataInicio;
	@Convert(converter = LocalDateTimePersistenceConverter.class)
	private LocalTime dataFinalizacao;

	@Convert(converter = LocalDateTimePersistenceConverter.class)
	private LocalDate vencimento;

	@Lob
	@Column(name = "descricao", alias = "Descrição")
	private String descricao;

	@Enumerated(EnumType.STRING)
	private StatusDeQuestionario status;

	private boolean autenticavel;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Pergunta<?>> perguntas;

	public String getNome() {
		return nome;
	}

	public Questionario() {
		this.setActive(true);
	}

	public void setActive(boolean value) {
		if (value)
			status = StatusDeQuestionario.ATIVO;
		else
			status = StatusDeQuestionario.INATIVO;
	}

	public boolean isActive() {
		return status == StatusDeQuestionario.ATIVO;
	}

	public boolean isAutenticavel() {
		return autenticavel;
	}

	public void setAutenticavel(boolean value) {
		this.autenticavel = value;
	}

	public LocalTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalTime getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(LocalTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Pergunta<?>> getPerguntas() {
		return Collections.unmodifiableSet(perguntas);
	}

	public void addPergunta(Pergunta<? extends Alternativa> pergunta) {
		if (pergunta != null) {
			this.perguntas.add(pergunta);
		} else
			throw new IllegalArgumentException("Pergunta inválida. [" + pergunta + "]");
	}

	public void addPerguntas(List<Pergunta<? extends Alternativa>> perguntas) {
		if (perguntas != null && !perguntas.isEmpty()) {
			this.perguntas.addAll(perguntas);
		} else
			throw new IllegalArgumentException("Lista inválida ou vazia.");
	}

	public boolean removePergunta(Pergunta<? extends Alternativa> pergunta) {
		return this.perguntas.remove(pergunta);
	}

	public boolean removePerguntas(List<Pergunta<? extends Alternativa>> perguntas) {
		return this.perguntas.removeAll(perguntas);
	}

}
