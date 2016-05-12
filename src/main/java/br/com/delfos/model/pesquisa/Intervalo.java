package br.com.delfos.model.pesquisa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INTERVALO")
public class Intervalo extends Alternativa {

	@Override
	public String toString() {
		return "Intervalo [valorInicial=" + valorInicial + ", valorFinal=" + valorFinal + ", intervalo="
		        + intervalo + "]";
	}

	private int valorInicial;
	private int valorFinal;
	private int intervalo;

	public Intervalo(int valorInicial, int valorFinal, int intervalo) {
		super();
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
		this.intervalo = intervalo;
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

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}

}
