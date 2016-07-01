package br.com.delfos.model.pesquisa;

import java.time.LocalDate;
import java.util.HashSet;
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
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import br.com.delfos.converter.date.LocalDatePersistenceConverter;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.view.table.TableColumnConfig;

enum StatusDeQuestionario {
	ATIVO, INATIVO;
}

@Entity
public class Questionario extends AbstractModel<Questionario> {

	@TableColumnConfig(name = "nome", alias = "Nome")
	private String nome;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate dataInicio;
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate dataFinalizacao;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate vencimento;

	@Override
	public String toString() {
		return "Questionario [id=" + getId() + ",nome=" + nome + ", dataInicio=" + dataInicio + ", dataFinalizacao="
				+ dataFinalizacao + "]";
	}

	@Lob
	@TableColumnConfig(name = "descricao", alias = "Descrição")
	private String descricao;

	@Enumerated(EnumType.STRING)
	private StatusDeQuestionario status;

	@Type(type = "yes_no")
	private boolean autenticavel;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST }, orphanRemoval = true)
	private Set<Pergunta<?>> perguntas;

	public String getNome() {
		return this.nome;
	}

	public Questionario() {
		this.perguntas = new HashSet<>();
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
		// TODO: Apagar essa linha de impressão
		this.perguntas.forEach(System.out::println);

		return Optional.ofNullable(this.perguntas);
	}

	public void addPergunta(Optional<Pergunta<? extends Alternativa>> pergunta) {
		this.perguntas.add(pergunta.orElseThrow(
				() -> new IllegalArgumentException(String.format("Pergunta inválida. Detalhe: %s", pergunta))));
	}

	public void addPerguntas(Optional<Set<Pergunta<? extends Alternativa>>> perguntas) {

		this.perguntas.addAll(perguntas.orElseThrow(() -> new IllegalArgumentException("Lista inválida ou vazia.")));
	}

	public boolean removePergunta(Optional<Pergunta<? extends Alternativa>> pergunta) {
		return this.perguntas.remove(pergunta.orElseThrow(() -> new IllegalArgumentException("Pergunta inválida.")));
	}

	public boolean removePerguntas(Optional<List<Pergunta<? extends Alternativa>>> perguntas) {
		return this.perguntas
				.removeAll(perguntas.orElseThrow(() -> new IllegalArgumentException("Lista inválida ou vazia.")));
	}

	public void clearPerguntas() {
		this.perguntas.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (autenticavel ? 1231 : 1237);
		result = prime * result + ((dataFinalizacao == null) ? 0 : dataFinalizacao.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((perguntas == null) ? 0 : perguntas.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((vencimento == null) ? 0 : vencimento.hashCode());
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
		Questionario other = (Questionario) obj;
		if (autenticavel != other.autenticavel)
			return false;
		if (dataFinalizacao == null) {
			if (other.dataFinalizacao != null)
				return false;
		} else if (!dataFinalizacao.equals(other.dataFinalizacao))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (perguntas == null) {
			if (other.perguntas != null)
				return false;
		} else if (!perguntas.equals(other.perguntas))
			return false;
		if (status != other.status)
			return false;
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (!vencimento.equals(other.vencimento))
			return false;
		return true;
	}

}
