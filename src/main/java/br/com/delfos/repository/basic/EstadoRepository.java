package br.com.delfos.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.basic.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	List<Estado> findByNome(String nome);
	Estado findByUf(String uf);
}
