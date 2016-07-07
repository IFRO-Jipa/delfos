package br.com.delfos.repository.pesquisa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.pesquisa.pergunta.Alternativa;

public interface AlternativaRepository extends JpaRepository<Alternativa, Long> {

}
