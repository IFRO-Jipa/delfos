package br.com.delfos.repository.pesquisa;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.pesquisa.Pergunta;
import br.com.delfos.model.pesquisa.Questionario;

public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {

	@Query("select q.perguntas from Questionario q where q.id = ?1")
	        Set<Pergunta<?>> findByQuestionario(Long id);

}
