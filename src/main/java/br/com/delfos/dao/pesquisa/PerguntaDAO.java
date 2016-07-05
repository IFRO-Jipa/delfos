package br.com.delfos.dao.pesquisa;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.repository.pesquisa.PerguntaRepository;

@Repository
public class PerguntaDAO extends AbstractDAO<Pergunta<?>, Long, PerguntaRepository> {

	@Override
	@SuppressWarnings("unchecked")
	public <S extends Pergunta<?>> Optional<S> save(S newValue) {
		if (newValue.getId() == null) {
			return Optional.ofNullable(repository.save(newValue));
		} else {
			Pergunta<Alternativa> pergunta = (Pergunta<Alternativa>) this.findOne(newValue.getId());
			// adiciono os itens aqui, caso seja uma MultiplaEscolha.
			if (pergunta.getAlternativa() instanceof MultiplaEscolha) {
				Optional<Map<String, Double>> itens = ((MultiplaEscolha) newValue.getAlternativa()).get();
				((MultiplaEscolha) pergunta.getAlternativa()).addAll(itens);
			} else {
				pergunta.setAlternativa(newValue.getAlternativa());
			}
			pergunta.setNome(newValue.getNome());
			pergunta.setDescricao(newValue.getDescricao());
			return (Optional<S>) Optional.ofNullable(repository.save(pergunta));
		}
	}

}
