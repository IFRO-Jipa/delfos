package br.com.delfos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	List<Estado> findByNome(String nome);
	Estado findByUf(String uf);
}
