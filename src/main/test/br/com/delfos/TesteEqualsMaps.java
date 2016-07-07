package br.com.delfos;

import org.junit.Assert;
import org.junit.Test;

import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;

public class TesteEqualsMaps {

	@Test
	public void testaSeOMapaContinuaIgualMesmoComValoresDiferentes() {
		MultiplaEscolha m1 = new MultiplaEscolha();
		m1.add("ALTO", 0.4);
		m1.add("MEDIO", 0.3);
		m1.add("BAIXO", 0.01);

		MultiplaEscolha m2 = new MultiplaEscolha();
		m2.add("ALTO", 0.3);
		m2.add("MEDIO", 0.4);
		m2.add("BAIXO", 0.02);

		System.out.println(m2);
		System.out.println(m1);

		System.out.println(m1.equals(m2));

		Assert.assertEquals(m1, m2);

	}

}
