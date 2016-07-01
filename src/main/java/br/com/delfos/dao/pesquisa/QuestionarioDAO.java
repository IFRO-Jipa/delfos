package br.com.delfos.dao.pesquisa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.repository.pesquisa.QuestionarioRepository;
import br.com.delfos.view.AlertBuilder;

@Repository
public class QuestionarioDAO extends AbstractDAO<Questionario, Long, QuestionarioRepository> {

	@Autowired
	private PerguntaDAO daoPergunta;

	public List<Questionario> findByPesquisa(Pesquisa pesquisa) {
		return repository.findByPesquisa(pesquisa.getId());
	}

	@Override
	public <S extends Questionario> Optional<S> save(S newValue) {
		Optional<S> resultado = Optional.empty();

		try {
			if (newValue.getId() == null) {
				final Set<Pergunta<?>> minhaLista = new HashSet<>();

				newValue.getPerguntas().ifPresent(perguntas -> minhaLista.addAll(perguntas));

				newValue.clearPerguntas();
				minhaLista.forEach(pergunta -> {
					newValue.addPergunta(daoPergunta.save(pergunta));
				});
				
				 // TODO: Continuar implementação
				// resultado = Optional.ofNullable((S)
				// repository.save(newValue));
			} else {
				// S value = (S) this.findOne(newValue.getId());
				// ((Upgrader<S>) value).update(value, newValue);
				// resultado = Optional.ofNullable((S)
				// repository.save(newValue));
			}
		} catch (RuntimeException ex) {
			AlertBuilder.error(ex);
		}

		return resultado;
	}

}
