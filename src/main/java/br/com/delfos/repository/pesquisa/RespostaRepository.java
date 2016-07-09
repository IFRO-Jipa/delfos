package br.com.delfos.repository.pesquisa;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaQuestionario;

public interface RespostaRepository extends JpaRepository<Resposta<?>, Long> {
	List<Resposta<?>> findByPergunta(Pergunta<?> pergunta);

	@Query("select r from Resposta r where r.questionario = ?1 and expert = ?2 and type(r) = RespostaQuestionario")
	Set<RespostaQuestionario> findByQuestionarioAndExpert(Questionario questionario, Pessoa expert);

}
