package br.com.delfos.model.pesquisa;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.delfos.converter.date.LocalDatePersistenceConverter;
import br.com.delfos.except.basic.PessoaInvalidaException;
import br.com.delfos.except.pesquisa.LimiteDeEspecialistasAtingidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.generic.AbstractModel;

@Entity
public class Pesquisa extends AbstractModel<Pesquisa> {

	@NotNull
	private String nome;
	private String descricao;
	private int limite = 0;

	@ManyToMany
	@NotNull
	@ElementCollection
	private Set<Pessoa> pesquisadores;

	@ManyToMany
	@ElementCollection
	private Set<Pessoa> especialistas;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Questionario> questionarios;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate date;

	public Pesquisa() {
		this.especialistas = new HashSet<>();
		this.pesquisadores = new HashSet<>();
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

	public List<Questionario> getQuestionarios() {
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