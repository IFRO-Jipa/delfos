package br.com.delfos.dao.basic;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Cidade;
import br.com.delfos.repository.basic.CidadeRepository;

@Repository
public class CidadeDAO extends AbstractDAO<Cidade, Long, CidadeRepository> {

}
