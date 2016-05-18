package br.com.delfos.model.pesquisa;

import java.time.LocalDate;
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

import org.hibernate.annotations.Type;

import br.com.delfos.converter.datetime.LocalDatePersistenceConverter;
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

	@Lob
	@Column(name = "descricao", alias = "Descrição")
	private String descricao;

	@Enumerated(EnumType.STRING)
	private StatusDeQuestionario status;

	@Type(type = "y_n")
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

	public Set<Pergunta<?>> getPerguntas() {
		return Collections.unmodifiableSet(this.perguntas);
	}

	public void addPergunta(Pergunta<? extends Alternativa> pergunta) {
		if (pergunta != null) {
			this.perguntas.add(pergunta);
		} else {
			throw new IllegalArgumentException("Pergunta inválida. [" + pergunta + "]");
		}
	}

	public void addPerguntas(List<Pergunta<? extends Alternativa>> perguntas) {
		if (perguntas != null && !perguntas.isEmpty()) {
			this.perguntas.addAll(perguntas);
		} else {
			throw new IllegalArgumentException("Lista inválida ou vazia.");
		}
	}

	public boolean removePergunta(Pergunta<? extends Alternativa> pergunta) {
		return this.perguntas.remove(pergunta);
	}

	public boolean removePerguntas(List<Pergunta<? extends Alternativa>> perguntas) {
		return this.perguntas.removeAll(perguntas);
	}

}
