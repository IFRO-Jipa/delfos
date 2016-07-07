package br.com.delfos.model.pesquisa.resposta;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;

@Entity
@DiscriminatorValue("MULTIPLA_ESCOLHA")
public class RespostaMultiplaEscolha extends Resposta<MultiplaEscolha> implements RespostaImpl<String> {

	@Column(name = "escolhaMultiplaEscolha")
	private String escolha;

	@Override
	public void setEscolha(String t) {
		if (this.getPergunta().getAlternativa().contains(t))
			this.escolha = t;
		else
			throw new IllegalArgumentException("Essa escolha não é valida, pois não está disponível para seleção.");
	}

	@Override
	public String getEscolha() {
		return escolha;
	}

	public double getValor() {
		return this.getPergunta().getAlternativa().getValor(escolha);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((escolha == null) ? 0 : escolha.hashCode());
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
		if (!(obj instanceof RespostaMultiplaEscolha)) {
			return false;
		}
		RespostaMultiplaEscolha other = (RespostaMultiplaEscolha) obj;
		if (escolha == null) {
			if (other.escolha != null) {
				return false;
			}
		} else if (!escolha.equals(other.escolha)) {
			return false;
		}
		return true;
	}

}
