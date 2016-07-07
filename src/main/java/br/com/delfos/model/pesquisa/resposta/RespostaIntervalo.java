package br.com.delfos.model.pesquisa.resposta;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.com.delfos.model.pesquisa.pergunta.Intervalo;

@Entity
@DiscriminatorValue("INTERVALO")
public class RespostaIntervalo extends Resposta<Intervalo> implements RespostaImpl<Integer> {

	@Column(name = "escolhaIntervalo")
	private Integer escolha;

	@Override
	public void setEscolha(Integer t) {
		if (this.getPergunta().getAlternativa().seletorValido(t)) {
			this.escolha = t;
		} else
			throw new IllegalArgumentException(String.format(
					"O valor selecionado não está dentro do intervalo. Por favor, selecione um valor que esteja entre %d e %d",
					this.getPergunta().getAlternativa().getValorInicial(),
					this.getPergunta().getAlternativa().getValorFinal()));
	}

	@Override
	public Integer getEscolha() {
		return escolha;
	}

	public double getValor() {
		int intervalos = this.getPergunta().getAlternativa().getIntervalos();

		return escolha * (intervalos / 100.0);
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
		if (!(obj instanceof RespostaIntervalo)) {
			return false;
		}
		RespostaIntervalo other = (RespostaIntervalo) obj;
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
