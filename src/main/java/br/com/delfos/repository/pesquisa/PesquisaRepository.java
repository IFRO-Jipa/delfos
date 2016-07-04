package br.com.delfos.repository.pesquisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {

	@Query("select p.questionarios from Pesquisa p where p.id = ?1")
	public List<Questionario> findQuestionarios(Long idPesquisa);

	@Query("select p from Pesquisa p inner join fetch p.especialistas es where es.id = ?1 and p.dataFinalizada is null")
	List<Pesquisa> findByEspecialista(Long idEspecialista);

}
