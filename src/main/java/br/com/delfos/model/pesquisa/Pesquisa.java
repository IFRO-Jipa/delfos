package br.com.delfos.model.pesquisa;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.com.delfos.converter.date.LocalDatePersistenceConverter;
import br.com.delfos.except.basic.PessoaInvalidaException;
import br.com.delfos.except.pesquisa.LimiteDeEspecialistasAtingidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.generic.AbstractModel;

enum StatusPesquisa {
	EM_ANDAMENTO, FINALIZADA;
}

@Entity
public class Pesquisa extends AbstractModel<Pesquisa> {

	@NotNull
	private String nome;
	private String descricao;
	private int limite = 0;

	@Enumerated(EnumType.STRING)
	private StatusPesquisa status;

	@ManyToMany(fetch = FetchType.EAGER)
	@NotNull
	@CollectionTable(name = "pesquisa_pesquisadores")
	private Set<Pessoa> pesquisadores;

	@ManyToMany(fetch = FetchType.EAGER)
	@CollectionTable(name = "pesquisa_especialistas")
	private Set<Pessoa> especialistas;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true,
	           cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH })
	private Set<Questionario> questionarios;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate date;

	public Pesquisa() {
		this.especialistas = new HashSet<>();
		this.pesquisadores = new HashSet<>();
		this.questionarios = new HashSet<>();
		this.setAtivo();
	}

	public void setAtivo() {
		this.status = StatusPesquisa.EM_ANDAMENTO;
	}

	public void finaliza() {
		this.status = StatusPesquisa.FINALIZADA;
	}

	public boolean isAtivo() {
		return this.status == StatusPesquisa.EM_ANDAMENTO;
	}

	public boolean isFinalizada() {
		return !isAtivo();
	}

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

	public Set<Questionario> getQuestionarios() {
		return questionarios;
	}

	public boolean addQuestionario(Questionario questionario) {
		return this.questionarios.add(questionario);
	}

	public void addQuestionarios(List<Questionario> questionarios) {
		this.questionarios.addAll(questionarios);
	}

	public void clearQuestionario() {
		this.questionarios.clear();
	}

	public boolean removeQuestionario(Questionario questionario) {
		return this.questionarios.remove(questionario);
	}

	public boolean removeQuestionarios(List<Questionario> questionarios) {
		return this.questionarios.removeAll(questionarios);
	}

	public boolean addPesquisador(Pessoa pessoa) throws PessoaInvalidaException {
		if (pessoa.isPesquisador()) {
			return this.pesquisadores.add(pessoa);
		} else
			throw new PessoaInvalidaException(
			        String.format("A pessoa %s não é um pesquisador válido.", pessoa.getNome()));
	}

	public boolean addEspecialista(Pessoa pessoa) {
		if (pessoa.isEspecialista() || verificaSeVaiAtingirLimite(1)) {
			return this.especialistas.add(pessoa);
		} else
			throw new PessoaInvalidaException(
			        String.format("A pessoa %s não é um especialista válido.", pessoa.getNome()));
	}

	private boolean verificaSeVaiAtingirLimite(int qtdAdicional) {
		return getQtdEspecialistas() + qtdAdicional <= this.limite;
	}

	private int getQtdEspecialistas() {
		return this.especialistas != null ? this.especialistas.size() : 0;
	}

	public boolean addEspecialistas(List<Pessoa> especialistas) throws LimiteDeEspecialistasAtingidoException {
		if (verificaTipo(especialistas, TipoPessoa.ESPECIALISTA)) {
			if ((limite == 0) || verificaSeVaiAtingirLimite(especialistas.size()))
				return this.especialistas.addAll(especialistas);
			else
				throw new LimiteDeEspecialistasAtingidoException(
				        "A pesquisa estourou o limite de especialistas definido.");
		} else
			throw new PessoaInvalidaException("As pessoas informadas não são especialistas válidos.");
	}

	public boolean addPesquisadores(List<Pessoa> pesquisadores) {
		if (verificaTipo(pesquisadores, TipoPessoa.PESQUISADOR)) {
			return this.pesquisadores.addAll(pesquisadores);
		} else
			throw new PessoaInvalidaException("As pessoas informadas não são especialistas válidos.");
	}

	private boolean verificaTipo(List<Pessoa> especialistas, TipoPessoa tipo) {
		boolean resultado = true;
		for (Pessoa pessoa : especialistas) {
			System.out.printf("Pessoa: %s Tipo %s", pessoa.getNome(), pessoa.getTipo());
			resultado = pessoa.getTipo().contains(tipo);
		}
		return resultado;
	}

	public void clearPesquisadores() {
		this.pesquisadores.clear();
	}

	public void clearEspecialistas() {
		this.especialistas.clear();
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		if (limite >= 0) {
			this.limite = limite;
		} else
			throw new IllegalArgumentException("Número negativo não é aceito.");
	}

	@Override
	public String toString() {
		return "Pesquisa [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", questionarios=" + questionarios
		        + "]";
	}

	public LocalDate getData() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Set<Pessoa> getPesquisadores() {
		return Collections.unmodifiableSet(pesquisadores);
	}

	public Set<Pessoa> getEspecialistas() {
		return Collections.unmodifiableSet(especialistas);
	}
}