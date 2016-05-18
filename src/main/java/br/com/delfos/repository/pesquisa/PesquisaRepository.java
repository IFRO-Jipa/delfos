package br.com.delfos.repository.pesquisa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.pesquisa.Pesquisa;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {

}
