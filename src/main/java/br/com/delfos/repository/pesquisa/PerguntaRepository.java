package br.com.delfos.repository.pesquisa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.pesquisa.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta<?>, Long> {

}
