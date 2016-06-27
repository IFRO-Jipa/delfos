package br.com.delfos.repository.pesquisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {

	@Query("select q from Questionario q join Pesquisa p where p.id = ?1")
	public List<Questionario> findQuestionarios(Long idPesquisa);

	@Query("select p from Pesquisa p where p.especialistas.id = ?1")
	List<Pesquisa> findByEspecialista(Long idEspecialista);

}
