package br.com.delfos.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("select p from Pessoa p join p.tipos t where t = ?1")
	public List<Pessoa> findByTipo(TipoPessoa tipo);

}
