package br.com.delfos.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.com.delfos.converter.LocalDatePersistenceConverter;

/**
 * Classe responsável por modelar as pessoas que serão salvas e manipuladas em funções do
 * software. <br>
 * Algumas classes que estendem de Pessoa: {@code Especialista}, {@code Pessoa}, etc...
 *
 * @author Leonardo Braz
 * @version 1.0
 * @since 1.5
 *
 */
@Entity
public class Pessoa extends AbstractModel<Pessoa> {

	private String nome;
	private String apelido;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate dataNascimento;
	private String descricao;
	private String email;

	@NotNull(message = "É necessário informar o tipo de pessoa.")
	@ElementCollection(targetClass = TipoPessoa.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<TipoPessoa> tipos;

	@NotNull
	private String cpf;
	private String rg;

	// TODO: arrumar problema com o cascade
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, orphanRemoval = true)
	private Endereco endereco;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
	        CascadeType.REFRESH }, orphanRemoval = true, optional = true)
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

	public Pessoa(Long id, String nome, LocalDate dataNascimento, String descricao, String email,
	        Endereco endereco) {
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

}
