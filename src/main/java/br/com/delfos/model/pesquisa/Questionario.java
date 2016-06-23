package br.com.delfos.model.pesquisa;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import br.com.delfos.converter.date.LocalDatePersistenceConverter;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.view.table.Column;

enum StatusDeQuestionario {
	ATIVO, INATIVO;
}

@Entity
public class Questionario extends AbstractModel<Questionario> {

	@Column(name = "nome", alias = "Nome")
	private String nome;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate dataInicio;
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate dataFinalizacao;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate vencimento;

	@ManyToOne(cascade = CascadeType.DETACH)
	private Pesquisa pesquisa;

	@Override
	public String toString() {
		return "Questionario [id=" + getId() + ",nome=" + nome + ", dataInicio=" + dataInicio + ", dataFinalizacao="
		        + dataFinalizacao + "]";
	}

	@Lob
	@Column(name = "descricao", alias = "Descrição")
	private String descricao;

	@Enumerated(EnumType.STRING)
	private StatusDeQuestionario status;

	@Type(type = "yes_no")
	private boolean autenticavel;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Pergunta<?>> perguntas;

	public String getNome() {
		return this.nome;
	}

	public Questionario() {
		this.setActive(true);
	}

	public void setActive(boolean value) {
		if (value) {
			this.status = StatusDeQuestionario.ATIVO;
		} else {
			this.status = StatusDeQuestionario.INATIVO;
		}
	}

	public boolean isActive() {
		return this.status == StatusDeQuestionario.ATIVO;
	}

	public boolean isAutenticavel() {
		return this.autenticavel;
	}

	public void setAutenticavel(boolean value) {
		this.autenticavel = value;

	}

	public LocalDate getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(LocalDate localDate) {
		this.dataInicio = localDate;
	}

	public LocalDate getDataFinalizacao() {
		return this.dataFinalizacao;
	}

	public void setDataFinalizacao(LocalDate localDate) {
		this.dataFinalizacao = localDate;
	}

	public LocalDate getVencimento() {
		return this.vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Optional<Set<Pergunta<?>>> getPerguntas() {
		return Optional.ofNullable(Collections.unmodifiableSet(this.perguntas));
	}

	public void addPergunta(Optional<Pergunta<? extends Alternativa>> pergunta) {
		this.perguntas.add(pergunta.orElseThrow(
		        () -> new IllegalArgumentException(String.format("Pergunta inválida. Detalhe: %s", pergunta))));
	}

	public void addPerguntas(Optional<List<Pergunta<? extends Alternativa>>> perguntas) {
		this.perguntas.addAll(perguntas.orElseThrow(() -> new IllegalArgumentException("Lista inválida ou vazia.")));
	}

	public boolean removePergunta(Optional<Pergunta<? extends Alternativa>> pergunta) {
		return this.perguntas.remove(pergunta.orElseThrow(() -> new IllegalArgumentException("Pergunta inválida.")));
	}

	public boolean removePerguntas(Optional<List<Pergunta<? extends Alternativa>>> perguntas) {
		return this.perguntas
		        .removeAll(perguntas.orElseThrow(() -> new IllegalArgumentException("Lista inválida ou vazia.")));
	}

}
