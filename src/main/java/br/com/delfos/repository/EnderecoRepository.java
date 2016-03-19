package br.com.delfos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
}
