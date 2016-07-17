package br.com.delfos.repository.pesquisa;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaIntervalo;
import br.com.delfos.model.pesquisa.resposta.RespostaMultiplaEscolha;
import br.com.delfos.model.pesquisa.resposta.RespostaQuestionario;

public interface RespostaRepository extends JpaRepository<Resposta<?>, Long> {
	Set<Resposta<?>> findByPergunta(Pergunta<?> pergunta);

	@Query("select r from Resposta r where r.pergunta = ?1 and type(r) = RespostaMultiplaEscolha")
	Set<RespostaMultiplaEscolha> findByPerguntaMultiplaEscolha(Pergunta<?> pergunta);

	@Query("select r from Resposta r where r.pergunta = ?1 and type(r) = RespostaIntervalo")
	Set<RespostaIntervalo> findByPerguntaIntervalo(Pergunta<Intervalo> pergunta);

	@Query("select r from Resposta r where r.questionario = ?1 and expert = ?2 and type(r) = RespostaQuestionario")
	Set<RespostaQuestionario> findByQuestionarioAndExpert(Questionario questionario, Pessoa expert);

	@Query("select r from Resposta r where r.questionario = ?1 and type(r) = RespostaMultiplaEscolha")
	Set<Resposta<?>> findByQuestionarioAndTipoMultiplaEscolha(Questionario q);

	@Query("select r from Resposta r where r.questionario = ?1 and type(r) = RespostaIntervalo")
	Set<Resposta<?>> findByQuestionarioAndTipoIntervalo(Questionario q);

	@Query("select case when count(r.id) > 0 then true else false end from Resposta r where r.questionario = ?1")
	boolean existeRespostaParaOQuestionario(Questionario questionario);

}
