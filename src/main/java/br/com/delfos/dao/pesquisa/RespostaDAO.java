package br.com.delfos.dao.pesquisa;

import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.except.pesquisa.resposta.QuestionarioRespondidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaIntervalo;
import br.com.delfos.model.pesquisa.resposta.RespostaMultiplaEscolha;
import br.com.delfos.model.pesquisa.resposta.RespostaParagrafo;
import br.com.delfos.model.pesquisa.resposta.RespostaQuestionario;
import br.com.delfos.model.pesquisa.resposta.RespostaTexto;
import br.com.delfos.repository.pesquisa.RespostaRepository;

@Repository
public class RespostaDAO extends AbstractDAO<Resposta<?>, Long, RespostaRepository> {

	@PersistenceContext(unitName = "mysqlDataSource")
	private EntityManager em;
	
	public Set<RespostaTexto> findByPerguntaTexto(Pergunta<Texto> pergunta) {
		return repository.findByPerguntaTexto(pergunta);
	}

	public Set<RespostaParagrafo> findByPerguntaParagrafo(Pergunta<Paragrafo> pergunta) {
		return repository.findByPerguntaParagrafo(pergunta);
	}

	public Set<Resposta<?>> findByPergunta(Pergunta<?> pergunta) {
		return repository.findByPergunta(pergunta);
	}

	public Set<RespostaMultiplaEscolha> findByPerguntaMultiplaEscolha(Pergunta<?> pergunta) {
		return repository.findByPerguntaMultiplaEscolha(pergunta);
	}

	public Set<RespostaIntervalo> findByPerguntaIntervalo(Pergunta<Intervalo> pergunta) {
		return repository.findByPerguntaIntervalo(pergunta);
	}

	public Set<Resposta<?>> findByQuestionarioAndTipoIntervalo(Questionario q) {
		return repository.findByQuestionarioAndTipoIntervalo(q);
	}

	public Set<Resposta<?>> findByQuestionarioAndTipoMultiplaEscolha(Questionario q) {
		return repository.findByQuestionarioAndTipoMultiplaEscolha(q);
	}

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
	public synchronized <S extends Resposta<?>> List<S> save(Iterable<S> entities) {
		List<S> saved = repository.save(entities);

		if (!saved.isEmpty()) {
			RespostaQuestionario resposta = new RespostaQuestionario();
			resposta.setQuestionario(saved.get(0).getQuestionario());
			resposta.setExpert(Optional.ofNullable(saved.get(0).getExpert()));
			this.save(resposta).ifPresent(persisted -> saved.add((S) persisted));
		}

		return saved;
	}

	@SuppressWarnings("unchecked")
	public Map<Questionario, Map<Month, Number>> getRespostasAgrupadosPelaData(Pesquisa p) {
		Map<Questionario, Map<Month, Number>> resultado = new HashMap<>();

		String SQL = "select month(r.horaResposta) as mes, count(r.id) from Resposta r where "
				+ "r.questionario.id = :idquestionario and type(r) = RespostaQuestionario group by year(r.horaResposta), month(r.horaResposta)";
		p.getQuestionarios().forEach(questionario -> {
			Map<Month, Number> estatistica = populaComMeses();
			Query query = em.createQuery(SQL);
			query.setParameter("idquestionario", questionario.getId());
			List<Object[]> mesQuantidade = query.getResultList();
			if (!mesQuantidade.isEmpty()) {
				int mes = (int) mesQuantidade.get(0)[0];
				Number qtd = (Number) mesQuantidade.get(0)[1];
				estatistica.put(Month.of(mes), qtd);
			}

			resultado.put(questionario, estatistica);
		});

		return resultado;
	}

	private Map<Month, Number> populaComMeses() {
		Map<Month, Number> resultado = new HashMap<>();

		for (Month m : Month.values())
			resultado.put(m, 0);
		return resultado;
	}

	public boolean existeRespostaParaOQuestionario(Questionario questionario) {
		return repository.existeRespostaParaOQuestionario(questionario);
	}

}
