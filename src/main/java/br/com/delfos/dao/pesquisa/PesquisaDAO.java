package br.com.delfos.dao.pesquisa;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.PesquisaRepository;

@Repository
public class PesquisaDAO extends AbstractDAO<Pesquisa, Long, PesquisaRepository> {

	public List<Questionario> findQuestionarios(Long idPesquisa) {
		return repository.findQuestionarios(idPesquisa);
	}

	public List<Pesquisa> findByEspecialista(Pessoa pessoa) {
		if (pessoa.getTipo().contains(TipoPessoa.ESPECIALISTA)) {
			return repository.findByEspecialista(pessoa.getId());
		} else
			throw new IllegalArgumentException(String.format(
			        "%s não possuí questionários para responder pois não é um especialista válido.", pessoa.getNome()));
	}

}
