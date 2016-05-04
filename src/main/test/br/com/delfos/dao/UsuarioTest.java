package br.com.delfos.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.model.Pessoa;
import br.com.delfos.model.Usuario;
import br.com.delfos.util.AlertBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class UsuarioTest {

	@Autowired
	private UsuarioDAO daoUsuario;

	@Autowired
	private PessoaDAO daoPessoa;

	@Test
	public void adiciona() {

	}

	@Test
	public void mostraTodosOsUsuarios() {
		List<Usuario> usuarios = daoUsuario.findAll();

		usuarios.forEach(usuario -> AlertBuilder.information(usuario.getLogin()));
	}

	@Test
	public void verificaSeExisteUsuario() {
		Usuario findByLogin = daoUsuario.findByLogin("root");

		Assert.assertNotNull(findByLogin);
	}

	@Test
	public void altera() {
		Pessoa pessoa = daoPessoa.findOne(2L);

		Usuario usuario = daoUsuario.findOne(1L);
		usuario.setPessoa(pessoa);

		daoUsuario.save(usuario);

	}

}
