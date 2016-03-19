package br.com.delfos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String logradouro;
	private String descricao;
	private String numero;
	private String cep;
	private String bairro;

	@OneToOne
	private TipoLogradouro tipoLogradouro;

	@OneToOne
	private Cidade cidade;

	public Endereco(String logradouro, String descricao, String numero, String cep, String bairro,
	        TipoLogradouro tipoLogradouro, Cidade cidade) {
		super();
		this.logradouro = logradouro;
		this.descricao = descricao;
		this.numero = numero;
		this.cep = cep;
		this.bairro = bairro;
		this.tipoLogradouro = tipoLogradouro;
		this.cidade = cidade;
	}

	public Endereco() {
		super();
	}

	public Endereco(Long id, String logradouro, String descricao, String numero, String cep, String bairro,
	        TipoLogradouro tipoLogradouro, Cidade cidade) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.descricao = descricao;
		this.numero = numero;
		this.cep = cep;
		this.bairro = bairro;
		this.tipoLogradouro = tipoLogradouro;
		this.cidade = cidade;
	}

	public Long getId() {
		return id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getNumero() {
		return numero;
	}

	public String getCep() {
		return cep;
	}

	public TipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
