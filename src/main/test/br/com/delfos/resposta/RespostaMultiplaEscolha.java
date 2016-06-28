package br.com.delfos.resposta;

import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;

public class RespostaMultiplaEscolha extends Resposta<MultiplaEscolha, String> {

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

}
