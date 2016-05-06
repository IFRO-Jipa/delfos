package br.com.delfos.model;

import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import br.com.delfos.converter.LocalDateTimePersistenceConverter;
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
	private LocalTime vencimento;

	@Lob
	@Column(name = "descricao", alias = "Descrição")
	private String descricao;

	@Enumerated(EnumType.STRING)
	private StatusDeQuestionario status;

	private boolean autenticavel;

	@ManyToOne(fetch = FetchType.EAGER)
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

	public LocalTime getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalTime vencimento) {
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

}
