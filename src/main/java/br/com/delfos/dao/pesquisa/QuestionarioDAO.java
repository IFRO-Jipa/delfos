package br.com.delfos.dao.pesquisa;

import java.util.ArrayList;
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

//	@SuppressWarnings("unchecked")
//	@Override
//	public <S extends Questionario> Optional<S> save(S newValue) {
//		Optional<Questionario> resultado = Optional.empty();
//
//		try {
//			if (newValue.getId() == null) {
//
//				List<Pergunta<?>> perguntasPersistidas = new ArrayList<>();
//
//				newValue.getPerguntas().ifPresent(perguntas -> perguntas.forEach(pergunta -> {
//					daoPergunta.save(pergunta).ifPresent(persistida -> perguntasPersistidas.add(persistida));
//				}));
//
//				newValue.clearPerguntas();
//
//				perguntasPersistidas.forEach(
//						pergunta -> newValue.addPergunta(Optional.ofNullable(daoPergunta.findOne(pergunta.getId()))));
//
//				resultado = Optional.ofNullable(repository.save(newValue));
//				// Set<Pergunta<?>> minhaLista = new HashSet<>();
//				//
//				// newValue.getPerguntas().ifPresent(perguntas ->
//				// minhaLista.addAll(perguntas));
//				//
//				// newValue.clearPerguntas();
//				// if (!minhaLista.isEmpty())
//				// minhaLista.forEach(pergunta ->
//				// newValue.addPergunta(daoPergunta.save(pergunta)));
//				//
//				// resultado = Optional.ofNullable(repository.save(newValue));
//
//				// TODO: Continuar implementação
//			} else {
//				Set<Pergunta<?>> perguntas = newValue.getPerguntas().orElse(null);
//
//				List<Pergunta<?>> perguntasSalvas = new ArrayList<>();
//
//				if (!perguntas.isEmpty())
//					perguntas.forEach(
//							pergunta -> daoPergunta.save(pergunta).ifPresent(salva -> perguntasSalvas.add(salva)));
//
//				Questionario value = this.findOne(newValue.getId());
//				value.update(value, newValue);
//				value.clearPerguntas();
//				value.addPerguntas(Optional.ofNullable(perguntasSalvas));
//
//				resultado = Optional.ofNullable(repository.save(value));
//			}
//		} catch (RuntimeException ex) {
//			AlertBuilder.error(ex);
//		}
//
//		return (Optional<S>) resultado;
//	}

}
