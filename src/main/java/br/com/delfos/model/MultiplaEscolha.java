package br.com.delfos.model;

import java.util.HashMap;
import java.util.Map;

public class MultiplaEscolha extends Alternativa {

	private Map<String, Double> escolhas;

	public MultiplaEscolha() {
		escolhas = new HashMap<>();
	}

	public MultiplaEscolha(Map<String, Double> escolhas) {
		this.escolhas = escolhas;
	}

	public void add(String pergunta, Double valor) throws Exception {
		if (!escolhas.containsKey(pergunta)) {
			escolhas.put(pergunta, valor);
		} else {
			throw new Exception("Essa pergunta está presente na lista.");
		}
	}

}
