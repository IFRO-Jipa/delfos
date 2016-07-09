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

@Repository
public class QuestionarioDAO extends AbstractDAO<Questionario, Long, QuestionarioRepository> {

	@Autowired
	private PerguntaDAO daoPergunta;

	public List<Questionario> findByPesquisa(Pesquisa pesquisa) {
		return repository.findByPesquisa(pesquisa.getId());
	}

	@Override
	public <S extends Questionario> Optional<S> save(S newValue) {
		newValue.getPerguntas().ifPresent(this::savePerguntas);
		return super.save(newValue);
	}

	private Set<Pergunta<?>> savePerguntas(Set<Pergunta<?>> perguntas) {
		Set<Pergunta<?>> persistidas = new HashSet<>();
		perguntas.forEach(pergunta -> daoPergunta.save(pergunta).ifPresent(persistidas::add));
		return persistidas;
	}

}
