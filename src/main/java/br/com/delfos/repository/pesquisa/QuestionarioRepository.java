package br.com.delfos.repository.pesquisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.pesquisa.Questionario;

public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {

	@Query("select p.questionarios from Pesquisa p where p.id = ?1")
	List<Questionario> findByPesquisa(Long pesquisa);

}
