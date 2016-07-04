package br.com.delfos.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.basic.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
}
