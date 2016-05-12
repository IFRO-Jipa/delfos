package br.com.delfos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.registro.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
}
