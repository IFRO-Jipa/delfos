package br.com.delfos.model.basic;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Embeddable
public class Endereco {

	@NotNull
	private String logradouro;
	private String descricaoEndereco;
	@NotNull
	private String numero;

	@Pattern(regexp = "^\\d{2}.\\d{3}-\\d{3}")
	private String cep;
	@NotNull
	private String bairro;

	@OneToOne(cascade = CascadeType.DETACH)
	private TipoLogradouro tipoLogradouro;

	@OneToOne
	private Cidade cidade;

	public Endereco(String logradouro, String descricao, String numero, String cep, String bairro,
			TipoLogradouro tipoLogradouro, Cidade cidade) {
		this.logradouro = logradouro;
		this.descricaoEndereco = descricao;
		this.numero = numero;
		this.cep = cep;
		this.bairro = bairro;
		this.tipoLogradouro = tipoLogradouro;
		this.cidade = cidade;
	}

	public Endereco() {
		super();
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getDescricao() {
		return descricaoEndereco;
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
		this.descricaoEndereco = descricao;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((descricaoEndereco == null) ? 0 : descricaoEndereco.hashCode());
		result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((tipoLogradouro == null) ? 0 : tipoLogradouro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Endereco)) {
			return false;
		}
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null) {
				return false;
			}
		} else if (!bairro.equals(other.bairro)) {
			return false;
		}
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} else if (!cep.equals(other.cep)) {
			return false;
		}
		if (cidade == null) {
			if (other.cidade != null) {
				return false;
			}
		} else if (!cidade.equals(other.cidade)) {
			return false;
		}
		if (descricaoEndereco == null) {
			if (other.descricaoEndereco != null) {
				return false;
			}
		} else if (!descricaoEndereco.equals(other.descricaoEndereco)) {
			return false;
		}
		if (logradouro == null) {
			if (other.logradouro != null) {
				return false;
			}
		} else if (!logradouro.equals(other.logradouro)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (tipoLogradouro == null) {
			if (other.tipoLogradouro != null) {
				return false;
			}
		} else if (!tipoLogradouro.equals(other.tipoLogradouro)) {
			return false;
		}
		return true;
	}

}
