package br.com.delfos.model.pesquisa;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

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

	@Lob
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

	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Questionario> questionarios;

	private LocalDate dataInicio;

	private LocalDate dataVencimento;

	private LocalDate dataFinalizada;

	public Pesquisa() {
		this.especialistas = new HashSet<>();
		this.pesquisadores = new HashSet<>();
		this.questionarios = new HashSet<>();
		this.setAtivo();
	}

	public void setAtivo() {
		this.status = StatusPesquisa.EM_ANDAMENTO;
	}

	public void finalizadaEm(LocalDate data) {
		if (data != null) {
			this.dataFinalizada = data;
			this.status = StatusPesquisa.FINALIZADA;
		}
	}

	public void finaliza() {
		finalizadaEm(LocalDate.now());
	}

	public boolean isAtivo() {
		return this.status == StatusPesquisa.EM_ANDAMENTO;
	}

	public boolean isFinalizada() {
		return !isAtivo();
	}

	public LocalDate getDataFinalizada() {
		return dataFinalizada;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public boolean isVencida() {
		return LocalDate.now().isAfter(dataVencimento);
	}

	public boolean isValida() {
		return !isVencida() && !isFinalizada();
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
		if (questionario != null) {
			return this.questionarios.add(questionario);
		} else
			throw new NullPointerException();
	}

	public void addQuestionarios(Set<Questionario> questionarios) {
		if (questionarios != null)
			this.questionarios.addAll(questionarios);
	}

	public void clearQuestionarios() {
		this.questionarios.clear();
	}

	public boolean removeQuestionario(Questionario questionario) {
		return this.questionarios.removeIf(value -> value.getId().equals(questionario.getId()));
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

	public boolean addEspecialistas(Set<Pessoa> especialistas) throws LimiteDeEspecialistasAtingidoException {
		if (verificaTipo(especialistas, TipoPessoa.ESPECIALISTA)) {
			if ((limite == 0) || verificaSeVaiAtingirLimite(especialistas.size()))
				return this.especialistas.addAll(especialistas);
			else
				throw new LimiteDeEspecialistasAtingidoException(
						"A pesquisa estourou o limite de especialistas definido.");
		} else
			throw new PessoaInvalidaException("As pessoas informadas não são especialistas válidos.");
	}

	public boolean addPesquisadores(Set<Pessoa> pesquisadores) {
		if (verificaTipo(pesquisadores, TipoPessoa.PESQUISADOR)) {
			return this.pesquisadores.addAll(pesquisadores);
		} else
			throw new PessoaInvalidaException("As pessoas informadas não são especialistas válidos.");
	}

	private boolean verificaTipo(Collection<Pessoa> especialistas, TipoPessoa tipo) {
		boolean resultado = true;
		for (Pessoa pessoa : especialistas) {
			resultado = pessoa.getTipo().contains(tipo);
		}
		return resultado;
	}

	public boolean containsPesquisador(Pessoa pesquisador) {
		if (pesquisador.isPesquisador()) {
			return this.pesquisadores.stream().filter(p -> p.getId().equals(pesquisador.getId())).findFirst()
					.isPresent();
		} else
			return false;
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

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate date) {
		this.dataInicio = date;
	}

	public Set<Pessoa> getPesquisadores() {
		return Collections.unmodifiableSet(pesquisadores);
	}

	public Set<Pessoa> getEspecialistas() {
		return Collections.unmodifiableSet(especialistas);
	}

	public boolean isEmptyQuestionario() {
		return questionarios == null ? false : questionarios.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dataFinalizada == null) ? 0 : dataFinalizada.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((especialistas == null) ? 0 : especialistas.hashCode());
		result = prime * result + limite;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pesquisadores == null) ? 0 : pesquisadores.hashCode());
		result = prime * result + ((questionarios == null) ? 0 : questionarios.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	public String getNomePesquisadores() {
		return pesquisadores.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Pesquisa)) {
			return false;
		}
		Pesquisa other = (Pesquisa) obj;
		if (dataFinalizada == null) {
			if (other.dataFinalizada != null) {
				return false;
			}
		} else if (!dataFinalizada.equals(other.dataFinalizada)) {
			return false;
		}
		if (dataInicio == null) {
			if (other.dataInicio != null) {
				return false;
			}
		} else if (!dataInicio.equals(other.dataInicio)) {
			return false;
		}
		if (dataVencimento == null) {
			if (other.dataVencimento != null) {
				return false;
			}
		} else if (!dataVencimento.equals(other.dataVencimento)) {
			return false;
		}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (especialistas == null) {
			if (other.especialistas != null) {
				return false;
			}
		} else if (!especialistas.equals(other.especialistas)) {
			return false;
		}
		if (limite != other.limite) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (pesquisadores == null) {
			if (other.pesquisadores != null) {
				return false;
			}
		} else if (!pesquisadores.equals(other.pesquisadores)) {
			return false;
		}
		if (questionarios == null) {
			if (other.questionarios != null) {
				return false;
			}
		} else if (!questionarios.equals(other.questionarios)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}

}