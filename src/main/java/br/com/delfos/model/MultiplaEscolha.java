package br.com.delfos.model;
// EMBEDABLE

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@DiscriminatorValue("MULTIPLA_ESCOLHA")
public class MultiplaEscolha extends Alternativa {

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "mesq_escolhas")
	public Map<String, Double> escolhas;

	public MultiplaEscolha() {
		escolhas = new HashMap<String, Double>();
	}

	public Map<String, Double> getEscolhas() {
		return Collections.unmodifiableMap(escolhas);
	}

	public void add(String item, double valor) {
		if (!escolhas.containsKey(item)) {
			escolhas.put(item, valor);
		}
	}

	@Override
	public String toString() {
		return "MultiplaEscolha [escolhas=" + escolhas + "]";
	}

}
