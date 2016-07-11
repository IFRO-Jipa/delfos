package br.com.delfos.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class PesquisaDAOTest {

	@Autowired
	private PesquisaDAO daoPesquisa;

	@Autowired
	private PessoaDAO daoPessoa;

	@Test
	public void mostraAsPesquisasDoPesquisador() {
		Pessoa leonardo = daoPessoa.findOne(2L);
		List<Pesquisa> pesquisas = daoPesquisa.findByPesquisador(leonardo);

		pesquisas.forEach(pesquisa -> {
			System.out.println("Nome: " + pesquisa.getNome());
			System.out.print("Pesquisadores: ");
			pesquisa.getPesquisadores().forEach(p -> System.out.println(p.getNome()));

			System.out.println(pesquisa.containsPesquisador(leonardo));
			System.out.println("------------------------------------");
		});

	}

}
