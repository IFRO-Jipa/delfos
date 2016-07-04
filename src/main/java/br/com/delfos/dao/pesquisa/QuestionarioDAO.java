package br.com.delfos.dao.pesquisa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.QuestionarioRepository;

@Repository
public class QuestionarioDAO extends AbstractDAO<Questionario, Long, QuestionarioRepository> {

	@Autowired
	private PerguntaDAO daoPergunta;

	public List<Questionario> findByPesquisa(Pesquisa pesquisa) {
		return repository.findByPesquisa(pesquisa.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Questionario> Optional<S> save(S newValue) {
		if (newValue.getId() == null) {
			return Optional.ofNullable(repository.save(newValue));
		} else {
			Questionario questionarioAntigo = this.findOne(newValue.getId());
			questionarioAntigo.setAutenticavel(newValue.isAutenticavel());
			questionarioAntigo.setActive(newValue.isActive());
			questionarioAntigo.setDataFinalizacao(newValue.getDataFinalizacao());
			questionarioAntigo.setDataInicio(newValue.getDataInicio());
			questionarioAntigo.setDescricao(newValue.getDescricao());
			questionarioAntigo.setNome(newValue.getNome());
			questionarioAntigo.setVencimento(newValue.getVencimento());
			newValue.getPerguntas().ifPresent(perguntas -> perguntas.forEach(pergunta -> daoPergunta.save(pergunta)
					.ifPresent(persisted -> questionarioAntigo.addPergunta(Optional.ofNullable(persisted)))));
			return (Optional<S>) Optional.ofNullable(repository.save(questionarioAntigo));
		}
	}

}
