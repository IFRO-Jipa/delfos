package br.com.delfos.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.basic.TipoLogradouro;

public interface TipoLogradouroRepository extends JpaRepository<TipoLogradouro, Long> {
	List<TipoLogradouro> findByNome(String nome);

	TipoLogradouro findBySigla(String sigla);
}
