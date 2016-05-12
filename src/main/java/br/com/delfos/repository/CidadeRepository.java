package br.com.delfos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.registro.Cidade;
import br.com.delfos.model.registro.Estado;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	List<Cidade> findByEstado(Estado estado);

	@Query("select c from Cidade c where c.nome = ?1")
	Cidade findByFullName(String name);

	@Query("select c from Cidade c where c.nome like %?1")
	List<Cidade> findByName(String name);
}
