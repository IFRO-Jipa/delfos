package br.com.delfos.dao.pesquisa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.except.pesquisa.LimiteDeEspecialistasAtingidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.PesquisaRepository;
import br.com.delfos.view.AlertBuilder;

@Repository
public class PesquisaDAO extends AbstractDAO<Pesquisa, Long, PesquisaRepository> {

	@Autowired
	private QuestionarioDAO daoQuestionario;

	public List<Questionario> findQuestionarios(Long idPesquisa) {
		return repository.findQuestionarios(idPesquisa);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Pesquisa> Optional<S> save(S detached) {
		if (detached.getId() == null) {
			return Optional.ofNullable(repository.save(detached));
		} else {
			Pesquisa managed = this.findOne(detached.getId());
			try {
				if (detached.isAtivo())
					managed.setAtivo();
				if (detached.isFinalizada())
					managed.finalizadaEm(detached.getDataFinalizada());
				managed.setDataInicio(detached.getDataInicio());
				managed.setDataVencimento(detached.getDataVencimento());
				managed.setDescricao(detached.getDescricao());
				managed.setLimite(detached.getLimite());
				managed.setNome(detached.getNome());
				managed.clearEspecialistas();
				managed.clearPesquisadores();
				managed.clearQuestionarios();
				managed.addEspecialistas(detached.getEspecialistas());
				managed.addPesquisadores(detached.getPesquisadores());
				detached.getQuestionarios().forEach(questionario -> {
					this.daoQuestionario.save(questionario).ifPresent(persisted -> managed.addQuestionario(persisted));
				});
			} catch (LimiteDeEspecialistasAtingidoException e) {
				AlertBuilder.error(e);
			}
			return (Optional<S>) Optional.ofNullable(repository.save(managed));
		}
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
			return result.stream().filter(pesquisa -> pesquisa.isValida() && !pesquisa.isEmptyQuestionario())
					.collect(Collectors.toList());
		} else
			throw new IllegalArgumentException(String.format(
					"%s não possuí questionários para responder pois não é um especialista válido.", pessoa.getNome()));
	}

}
