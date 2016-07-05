package br.com.delfos.repository.pesquisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta<?>, Long> {
	List<Resposta<?>> findByPergunta(Pergunta<?> pergunta);

	// List<Resposta<?>> findByExpertAndQuestionarioAndPesquisa(Pessoa expert,
	// Questionario questionario,
	// Pesquisa pesquisa);
}
