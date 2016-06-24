package br.com.delfos.model.pesquisa;
// EMBEDABLE

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@DiscriminatorValue("MULTIPLA_ESCOLHA")
public class MultiplaEscolha extends Alternativa {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((escolhas == null) ? 0 : escolhas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiplaEscolha other = (MultiplaEscolha) obj;
		if (escolhas == null) {
			if (other.escolhas != null)
				return false;
		} else if (!escolhas.equals(other.escolhas))
			return false;
		return true;
	}

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

	public void addAll(Optional<Map<String, Double>> itens) {
		itens.ifPresent(valores -> escolhas.putAll(valores));
	}

	@Override
	public String toString() {
		return "MultiplaEscolha [escolhas=" + escolhas + ", id=" + this.getId() + "]";
	}

}
