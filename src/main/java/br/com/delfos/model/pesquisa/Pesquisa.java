package br.com.delfos.model.pesquisa;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import br.com.delfos.except.basic.PessoaInvalidaException;
import br.com.delfos.except.pesquisa.LimiteDeEspecialistasAtingidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.generic.AbstractModel;

@Entity
public class Pesquisa extends AbstractModel<Pesquisa> {

	private String nome;
	private String descricao;
	private int limite;

	@ManyToMany
	private Set<Pessoa> pesquisadores;

	@ManyToMany
	private Set<Pessoa> especialistas;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Questionario> questionarios;

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

	public void setQuestionarios(List<Questionario> questionarios) {
		this.questionarios = questionarios;
	}

	public void addPesquisador(Pessoa pessoa) throws PessoaInvalidaException {
		if (pessoa.isPesquisador()) {
			this.pesquisadores.add(pessoa);
		} else
			throw new PessoaInvalidaException(
			        String.format("A pessoa %s não é um pesquisador válido.", pessoa.getNome()));
	}

	public void addEspecialista(Pessoa pessoa) {
		if (pessoa.isEspecialista() || verificaSeVaiAtingirLimite(1)) {
			this.especialistas.add(pessoa);
		} else
			throw new PessoaInvalidaException(
			        String.format("A pessoa %s não é um especialista válido.", pessoa.getNome()));
	}

	private boolean verificaSeVaiAtingirLimite(int qtdAdicional) {
		return this.especialistas.size() + qtdAdicional <= this.limite;
	}

	public void addEspecialistas(Set<Pessoa> especialistas) throws LimiteDeEspecialistasAtingidoException {
		if (verificaTipo(especialistas, TipoPessoa.ESPECIALISTA)) {
			if ((limite == 0) || verificaSeVaiAtingirLimite(especialistas.size()))
				this.especialistas.addAll(especialistas);
			else
				throw new LimiteDeEspecialistasAtingidoException(
				        "A pesquisa estourou o limite de especialistas definido.");
		}
	}

	public void addPesquisadores(Set<Pessoa> pesquisadores) {
		if (verificaTipo(pesquisadores, TipoPessoa.PESQUISADOR)) {
			this.pesquisadores.addAll(pesquisadores);
		}
	}

	private boolean verificaTipo(Set<Pessoa> especialistas, TipoPessoa tipo) {
		boolean resultado = true;
		for (Pessoa pessoa : especialistas) {
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

}