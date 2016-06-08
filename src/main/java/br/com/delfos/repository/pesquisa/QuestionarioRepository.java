package br.com.delfos.repository.pesquisa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.pesquisa.Questionario;

public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {

}
