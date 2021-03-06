package br.com.delfos.model.pesquisa.pergunta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaIntervalo;

@Entity
@DiscriminatorValue("INTERVALO")
public class Intervalo extends Alternativa {

	private int valorInicial;
	private int valorFinal;
	private int intervalo;

	public Intervalo(int valorInicial, int valorFinal, int incremento) {
		super(TipoPergunta.INTERVALO);
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
		this.intervalo = incremento;
	}

	public Intervalo() {
		this(0, 10, 1);
	}

	public int getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(int valorInicial) {
		this.valorInicial = valorInicial;
	}

	public int getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(int valorFinal) {
		this.valorFinal = valorFinal;
	}

	public int getIncremento() {
		return intervalo;
	}

	public void setIncremento(int intervalo) {
		this.intervalo = intervalo;
	}

	@Override
	public String toString() {
		return "Intervalo [valorInicial=" + valorInicial + ", valorFinal=" + valorFinal + ", intervalo=" + intervalo
				+ "]";
	}

	public boolean seletorValido(int valor) {
		return valor >= valorInicial && valor <= valorFinal;
	}

	public int getIntervalos() {
		int intervalos = 0;
		int contador = valorInicial;

		while (contador <= valorFinal) {
			intervalos++;

			contador += this.intervalo;
		}

		return intervalos;
	}

	@Override
	public Resposta<?> createSimpleResposta() {
		return new RespostaIntervalo();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + intervalo;
		result = prime * result + valorFinal;
		result = prime * result + valorInicial;
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
		if (!(obj instanceof Intervalo)) {
			return false;
		}
		Intervalo other = (Intervalo) obj;
		if (intervalo != other.intervalo) {
			return false;
		}
		if (valorFinal != other.valorFinal) {
			return false;
		}
		if (valorInicial != other.valorInicial) {
			return false;
		}
		return true;
	}

}
