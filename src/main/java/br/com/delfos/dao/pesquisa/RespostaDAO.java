package br.com.delfos.dao.pesquisa;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.repository.pesquisa.RespostaRepository;

@Repository
public class RespostaDAO extends AbstractDAO<Resposta<?>, Long, RespostaRepository> {

}
