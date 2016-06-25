package br.com.delfos.dao.pesquisa;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.QuestionarioRepository;

@Repository
public class QuestionarioDAO extends AbstractDAO<Questionario, Long, QuestionarioRepository> {

	public List<Questionario> findByPesquisa(Pesquisa pesquisa) {
		return repository.findByPesquisa(pesquisa.getId());
	}

}
