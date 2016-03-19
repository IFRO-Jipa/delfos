package delfos;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;

import br.com.delfos.dao.CidadeDAO;
import br.com.delfos.dao.PessoaDAO;
import br.com.delfos.dao.TipoLogradouroDAO;
import br.com.delfos.model.Cidade;
import br.com.delfos.model.Endereco;
import br.com.delfos.model.Pessoa;
import br.com.delfos.model.TipoLogradouro;
import br.com.delfos.util.ContextFactory;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Cidade c = ContextFactory.getBean(CidadeDAO.class).findOne(1L);

		Endereco endereco = new Endereco();
		endereco.setBairro("Cafezinho");
		endereco.setCep("76913093");
		endereco.setLogradouro("Sena Madureira");
		TipoLogradouro tipoLogradouro = ContextFactory.getBean(TipoLogradouroDAO.class).findOne(4L);

		// TODO corrigir problema com tipo do logradouro.
		System.out.println(tipoLogradouro);
		endereco.setTipoLogradouro(tipoLogradouro);
		endereco.setCidade(c);

		Pessoa p = new Pessoa();
		p.setNome("Leonardo Henrique de Braz");
		p.setEmail("lhleonardo@hotmail.com");
		p.setDataNascimento(LocalDate.of(1999, Month.FEBRUARY, 05));
		p.setEndereco(endereco);

		ContextFactory.getBean(PessoaDAO.class).save(p);
	}

}
