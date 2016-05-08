package br.com.delfos.dao;

import org.springframework.stereotype.Repository;

import br.com.delfos.model.Questionario;
import br.com.delfos.repository.QuestionarioRepository;

@Repository
public class QuestionarioDAO extends AbstractDAO<Questionario, Long, QuestionarioRepository> {

}
