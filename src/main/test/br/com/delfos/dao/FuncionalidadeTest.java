package br.com.delfos.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delfos.dao.auditoria.FuncionalidadeDAO;
import br.com.delfos.model.auditoria.Funcionalidade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/applicationContext.xml")
public class FuncionalidadeTest {

	@Autowired
	private FuncionalidadeDAO dao;

	public void testaSeVaiAtualizarORegistroComSave() {
		Funcionalidade funcByChave = dao.findOne(9L);
		funcByChave.setDescricao(funcByChave.getDescricao() + "teste");
		Assert.assertEquals(funcByChave, dao.save(funcByChave));
	}

	@Test
	public void test() {
		Funcionalidade esperado = new Funcionalidade(9L, "menuCadastroFuncionalidade",
		        "menuCadastroFuncionalidade", "Menu relacionado aos cadastros de funcionalidade",
		        dao.findOne(8L));

		Funcionalidade funcionalidade = dao.findOne(9l);
		funcionalidade.setDescricao(funcionalidade.getDescricao() + "teste");
		System.out.println(funcionalidade.getDescricao());
		salva(funcionalidade);

		Assert.assertEquals(esperado, funcionalidade);
	}

	private void salva(Funcionalidade funcionalidade) {
		dao.save(funcionalidade);
	}

}
