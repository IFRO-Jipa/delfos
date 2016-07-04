package br.com.delfos.dao.basic;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.basic.Endereco;
import br.com.delfos.repository.basic.EnderecoRepository;

@Repository
public class EnderecoDAO extends AbstractDAO<Endereco, Long, EnderecoRepository> {

}
