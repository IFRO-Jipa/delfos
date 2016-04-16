package br.com.delfos.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.model.Pessoa;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class PessoaDAOTest {

	@Autowired
	private PessoaDAO dao;

	@Test
	public void lista() {
		List<Pessoa> all = dao.findAll();

		all.forEach(System.out::println);
	}

}
