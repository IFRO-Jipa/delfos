package br.com.delfos.models;

import java.util.Date;

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
public class Pessoa implements Identificador {

	private Integer id;
	private String nome;

	private String cpf;
	private String rg;

	private Date dataNascimento;
	private String descricao;
	private String email;

	private Endereco endereco;

	@Override
	public Integer getId() {
		return this.id;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public Date getDataNascimento() {
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

}
