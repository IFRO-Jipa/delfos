package br.com.delfos.model.pesquisa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TEXTO")
public class Texto extends Alternativa {

	@Column(name = "valor_texto", length = 255)
	private String valor;
}
