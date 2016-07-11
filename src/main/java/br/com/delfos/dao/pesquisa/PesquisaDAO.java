package br.com.delfos.dao.pesquisa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.PesquisaRepository;

@Repository
public class PesquisaDAO extends AbstractDAO<Pesquisa, Long, PesquisaRepository> {

	public List<Questionario> findQuestionarios(Long idPesquisa) {
		return repository.findQuestionarios(idPesquisa);
	}

	/**
	 * Esse método só traz as pesquisa que não estão finalizadas ou vencidas.
	 * 
	 * @param pessoa
	 * @return
	 */
	public List<Pesquisa> findByEspecialista(Pessoa pessoa) {
		if (pessoa.isEspecialista()) {
			List<Pesquisa> result = repository.findByEspecialista(pessoa);
			return result.stream().filter(this::isValidAndNotEmpty).collect(Collectors.toList());
		} else
			throw new IllegalArgumentException(String.format(
					"%s não possuí questionários para responder pois não é um especialista válido.", pessoa.getNome()));
	}

	private boolean isValidAndNotEmpty(Pesquisa pesquisa) {
		return pesquisa.isValida() && !pesquisa.isEmptyQuestionario();
	}

	public List<Pesquisa> findByPesquisador(Pessoa pessoa) {
		if (!pessoa.isPesquisador()) {
			throw new IllegalArgumentException(String.format(
					"%s não possuí questionários para responder pois não é um especialista válido.", pessoa.getNome()));
		}

		return repository.findByPesquisador(pessoa);
	}

}
