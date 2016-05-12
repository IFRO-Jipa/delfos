package br.com.delfos.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@DiscriminatorValue("PARAGRAFO")
public class Paragrafo extends Alternativa {

	@Lob
	@Column(name = "valor_paragrafo")
	private String valor;

}
