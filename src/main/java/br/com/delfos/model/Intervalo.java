package br.com.delfos.model;

public class Intervalo extends Alternativa {

	private int valorInicial;
	private int valorFinal;
	private int intervalo;

	public Intervalo(int valorInicial, int valorFinal, int intervaloEntreValores) {
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
		this.intervalo = intervaloEntreValores;
	}

	public Intervalo(int intervalo) {
		this.intervalo = intervalo;
	}

	public Intervalo(int valorFinal, int intervalo) {
		this.valorInicial = 0;
		this.valorFinal = valorFinal;
		this.intervalo = intervalo;
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

	public int getIntervaloEntreValores() {
		return intervalo;
	}

	public void setIntervaloEntreValores(int intervaloEntreValores) {
		this.intervalo = intervaloEntreValores;
	}

}
