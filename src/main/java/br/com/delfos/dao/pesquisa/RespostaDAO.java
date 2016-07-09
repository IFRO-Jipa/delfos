package br.com.delfos.dao.pesquisa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.except.pesquisa.resposta.QuestionarioRespondidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaQuestionario;
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
		return false;
	}

	public Set<RespostaQuestionario> getRespostaDosQuestionarios(Pesquisa pesquisa, Pessoa especialista) {
		if (pesquisa == null || especialista == null)
			throw new IllegalStateException(
					"RespostaDAO.getRespostasDaPesquisa: Pesquisa e/ou especialista informados são inválidos. ");
		if (!especialista.isEspecialista())
			throw new IllegalStateException(
					"RespostaDAO.getRespostasDaPesquisa: A pessoa informada não é um especialista válido.");

		Set<RespostaQuestionario> respostas = new HashSet<>();
		pesquisa.getQuestionarios().forEach(questionario -> {
			Set<RespostaQuestionario> result = repository.findByQuestionarioAndExpert(questionario, especialista);
			if (result != null) {
				if (!result.isEmpty()) {
					respostas.addAll(result);
				}
			}
		});

		return respostas;

	}

	@SuppressWarnings("unchecked")
	public <S extends Resposta<?>> List<S> save(Iterable<S> entities) {
		List<S> saved = repository.save(entities);

		if (!saved.isEmpty()) {
			RespostaQuestionario resposta = new RespostaQuestionario();
			resposta.setQuestionario(saved.get(0).getQuestionario());
			resposta.setExpert(Optional.ofNullable(saved.get(0).getExpert()));
			this.save(resposta).ifPresent(persisted -> saved.add((S) persisted));
		}

		return saved;
	}
}
