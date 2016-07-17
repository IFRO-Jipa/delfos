package br.com.delfos.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.model.basic.Pessoa;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class PessoaDAOTest {

	@Autowired
	private PessoaDAO daoPessoa;

	@Test
	public void buscaTodasAsPessoasSemUsuarioCadastrado() {
		List<Pessoa> pessoas = daoPessoa.findByUsuarioIsNull();
		pessoas.forEach(p -> System.out.println(p.getNome()));
	}
}
