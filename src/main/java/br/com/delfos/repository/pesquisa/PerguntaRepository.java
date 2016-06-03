package br.com.delfos.repository.pesquisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.pesquisa.Pergunta;
import br.com.delfos.model.pesquisa.Questionario;


@SuppressWarnings("rawtypes")
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
	
	@Query("select p.nome, p.alternativa_id from Pergunta p inner join Questionario q on p.id = q.id where q.id=?")
	List<Pergunta> findByQuestionario(Questionario questionario);

}
