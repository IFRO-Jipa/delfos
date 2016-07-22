package br.com.delfos.model.basic;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.model.generic.AbstractModel;

/**
 * Classe responsável por modelar as pessoas que serão salvas e manipuladas em
 * funções do software. <br>
 * Algumas classes que estendem de Pessoa: {@code Especialista}, {@code Pessoa},
 * etc...
 *
 * @author Leonardo Braz
 * @version 1.0
 * @since 1.7
 *
 */
@Entity
public class Pessoa extends AbstractModel<Pessoa> {

	private String nome;
	private String apelido;

	private LocalDate dataNascimento;
	private String descricao;

	@Email
	private String email;

	@NotNull(message = "É necessário informar o tipo de pessoa.")
	@ElementCollection(targetClass = TipoPessoa.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<TipoPessoa> tipos;

	@NotNull
	@CPF
	@Column(unique = true)
	private String cpf;

	private String rg;

	@Embedded
	private Endereco endereco;

	@OneToOne(cascade = { CascadeType.REFRESH,
			CascadeType.REMOVE }, orphanRemoval = true, optional = true, mappedBy = "pessoa")
	private Usuario usuario;

	public Pessoa() {
		super();
	}

	public Pessoa(String nome, LocalDate dataNascimento, Endereco endereco) {
		super();
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
	}

	public Pessoa(Long id, String nome, LocalDate dataNascimento, String descricao, String email, Endereco endereco) {
		super(id);
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.descricao = descricao;
		this.email = email;
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getEmail() {
		return email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPesquisador() {
		return this.tipos.contains(TipoPessoa.PESQUISADOR);
	}

	public boolean isEspecialista() {
		return this.tipos.contains(TipoPessoa.ESPECIALISTA);
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public List<TipoPessoa> getTipo() {
		return tipos;
	}

	public void setTipo(List<TipoPessoa> tipo) {
		this.tipos = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apelido == null) ? 0 : apelido.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((tipos == null) ? 0 : tipos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Pessoa)) {
			return false;
		}
		Pessoa other = (Pessoa) obj;
		if (apelido == null) {
			if (other.apelido != null) {
				return false;
			}
		} else if (!apelido.equals(other.apelido)) {
			return false;
		}
		if (cpf == null) {
			if (other.cpf != null) {
				return false;
			}
		} else if (!cpf.equals(other.cpf)) {
			return false;
		}
		if (dataNascimento == null) {
			if (other.dataNascimento != null) {
				return false;
			}
		} else if (!dataNascimento.equals(other.dataNascimento)) {
			return false;
		}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (endereco == null) {
			if (other.endereco != null) {
				return false;
			}
		} else if (!endereco.equals(other.endereco)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (rg == null) {
			if (other.rg != null) {
				return false;
			}
		} else if (!rg.equals(other.rg)) {
			return false;
		}
		if (tipos == null) {
			if (other.tipos != null) {
				return false;
			}
		} else if (!tipos.equals(other.tipos)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

}
