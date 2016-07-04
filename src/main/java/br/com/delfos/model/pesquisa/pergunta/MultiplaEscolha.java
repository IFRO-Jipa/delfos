package br.com.delfos.model.pesquisa.pergunta;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaMultiplaEscolha;

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
	@CollectionTable(name = "AlternativaEscolhas")
	private Map<String, Double> escolhas;

	public MultiplaEscolha() {
		super(TipoPergunta.MULTIPLA_ESCOLHA);
		escolhas = new HashMap<String, Double>();
	}

	public Set<String> getEscolhas() {
		return this.escolhas.keySet();
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

	public double getValor(String escolha) {
		return this.escolhas.get(escolha);
	}

	public boolean contains(String key) {
		return this.escolhas.containsKey(key);
	}

	@Override
	public Resposta<?> createSimpleResposta() {
		return new RespostaMultiplaEscolha();
	}

}
