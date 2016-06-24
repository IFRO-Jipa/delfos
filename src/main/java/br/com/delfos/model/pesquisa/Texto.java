package br.com.delfos.model.pesquisa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TEXTO")
public class Texto extends Alternativa {

	@Override
	public String toString() {
		return "Texto [valor=" + valor + "]";
	}

	@Column(name = "valor_texto")
	private String valor;
}
