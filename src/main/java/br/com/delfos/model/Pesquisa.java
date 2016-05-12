package br.com.delfos.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import br.com.delfos.except.PessoaInvalidaException;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.model.registro.Pessoa;
import br.com.delfos.model.registro.TipoPessoa;

@Entity
public class Pesquisa extends AbstractModel<Pesquisa> {

	@Override
	public String toString() {
		return "Pesquisa [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", questionarios="
		        + questionarios + "]";
	}

	private String nome;
	private String descricao;

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
		if (pessoa.isEspecialista()) {
			this.especialistas.add(pessoa);
		} else
			throw new PessoaInvalidaException(
			        String.format("A pessoa %s não é um especialista válido.", pessoa.getNome()));
	}

	public void addEspecialistas(Set<Pessoa> especialistas) {
		if (verificaTipo(especialistas, TipoPessoa.ESPECIALISTA)) {
			this.especialistas.addAll(especialistas);
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

}