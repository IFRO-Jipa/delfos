package br.com.delfos.dao.pesquisa;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.PesquisaRepository;

@Repository
public class PesquisaDAO extends AbstractDAO<Pesquisa, Long, PesquisaRepository> {

	public List<Questionario> findQuestionarios(Long idPesquisa) {
		return repository.findQuestionarios(idPesquisa);
	}

}
