package br.com.delfos.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.auditoria.Funcionalidade;

public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {

	Funcionalidade findByChave(String chave);

	Set<Funcionalidade> findByPreRequisito(Funcionalidade preRequisito);
}
