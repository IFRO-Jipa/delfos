package br.com.delfos.dao.pesquisa;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.repository.pesquisa.QuestionarioRepository;

@Repository
public class QuestionarioDAO extends AbstractDAO<Questionario, Long, QuestionarioRepository> {

}
