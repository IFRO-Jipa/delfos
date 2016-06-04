package br.com.delfos.model.pesquisa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("PARAGRAFO")
public class Paragrafo extends Alternativa {

	@Lob
	@Column(name = "valor_paragrafo")
	@NotNull
	private String valor;

}
