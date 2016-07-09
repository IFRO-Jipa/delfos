package br.com.delfos.dao.pesquisa;

import java.util.List;
import java.util.stream.Collectors;

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


	/**
	 * Esse método só traz as pesquisa que não estão finalizadas ou vencidas.
	 * 
	 * @param pessoa
	 * @return
	 */
	public List<Pesquisa> findByEspecialista(Pessoa pessoa) {
		// TODO trazer apenas as que não foram respondido pelo especialista.
		// TODO trazer apenas as que possuem questionários
		if (pessoa.getTipo().contains(TipoPessoa.ESPECIALISTA)) {
			List<Pesquisa> result = repository.findByEspecialista(pessoa.getId());
			return result.stream().filter(this::isValidAndNotEmpty).collect(Collectors.toList());
		} else
			throw new IllegalArgumentException(String.format(
					"%s não possuí questionários para responder pois não é um especialista válido.", pessoa.getNome()));
	}

	private boolean isValidAndNotEmpty(Pesquisa pesquisa) {
		return pesquisa.isValida() && !pesquisa.isEmptyQuestionario();
	}

	// private List<Pesquisa> isNotAnswered(List<Pesquisa> pesquisas) {
	// if (pesquisas != null) {
	// if (pesquisas.size() > 0) {
	// List<Pesquisa> resultado = new ArrayList<>();
	//
	// for (Pesquisa pesquisa : pesquisas) {
	// List<Questionario> questionarios = new ArrayList<>();
	// pesquisa.getQuestionarios().forEach(questionario -> {
	// if (!daoResposta.isAnswered(pesquisa, questionario,
	// Autenticador.getDonoDaConta())) {
	// questionarios.add(questionario);
	// }
	// });
	// }
	//
	// // TODO: Continuação
	// return null;
	// } else
	// return new ArrayList<>();
	// } else
	// return null;
	// }
}
