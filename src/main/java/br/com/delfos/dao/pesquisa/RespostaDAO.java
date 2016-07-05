package br.com.delfos.dao.pesquisa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.except.pesquisa.resposta.QuestionarioRespondidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.repository.pesquisa.RespostaRepository;

@Repository
public class RespostaDAO extends AbstractDAO<Resposta<?>, Long, RespostaRepository> {

	@Override
	public <S extends Resposta<?>> Optional<S> save(S newValue) {
		if (newValue.getId() == null) {
			return Optional.ofNullable(this.repository.save(newValue));
		} else {
			throw new QuestionarioRespondidoException("Parece que você já respondeu esse questionario.");
		}
	}

	public boolean isAnswered(Pesquisa pesquisa, Questionario questionario, Pessoa donoDaConta) {
		// TODO: Corrigir
		// return
		// !repository.findByExpertAndQuestionarioAndPesquisa(donoDaConta,
		// questionario, pesquisa).isEmpty();
		return false;
	}

}
