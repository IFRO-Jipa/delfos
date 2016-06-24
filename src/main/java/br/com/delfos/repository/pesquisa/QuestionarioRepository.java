package br.com.delfos.repository.pesquisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Questionario;

public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {

	// TODO: Implementar com JPQL
	@Query(value = "select p.* from pesquisa_especialistas pe join Pesquisa p on p.id = pe.Pesquisa_id where pe.especialistas_id = ?1", nativeQuery = true)
	List<Questionario> findByEspecialista(Pessoa pessoa);
}
