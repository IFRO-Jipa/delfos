package br.com.delfos.models;

public class Endereco implements Identificador {

	private Integer id;
	private String logradouro;
	private String descricao;
	private String numero;
	private String cep;

	private Logradouro tipoLogradouro;
	private Bairro bairro;
	private Cidade cidade;

	@Override
	public Integer getId() {
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

	public Logradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

}
