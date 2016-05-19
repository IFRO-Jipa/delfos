package br.com.delfos.dao.pesquisa;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.repository.pesquisa.PesquisaRepository;

@Repository
public class PesquisaDAO extends AbstractDAO<Pesquisa, Long, PesquisaRepository> {

}
