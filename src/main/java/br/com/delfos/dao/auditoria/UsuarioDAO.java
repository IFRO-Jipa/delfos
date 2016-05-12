package br.com.delfos.dao.auditoria;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.repository.auditoria.UsuarioRepository;

@Repository
public class UsuarioDAO extends AbstractDAO<Usuario, Long, UsuarioRepository> {

	public Usuario findByLoginAndSenha(String login, String senha) {
		return repository.findByLoginAndSenha(login, senha);
	}

	public Optional<Usuario> findByLogin(String login) {
		return repository.findByLogin(login);
	}

	public boolean isItAvaliable(String login) {
		return !findByLogin(login).isPresent();
	}

}
