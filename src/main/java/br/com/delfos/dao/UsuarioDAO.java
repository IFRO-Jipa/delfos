package br.com.delfos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.Usuario;
import br.com.delfos.repository.UsuarioRepository;

@Repository
public class UsuarioDAO {

	@Autowired
	private UsuarioRepository repository;

	public long count() {
		return repository.count();
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public void delete(Usuario arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<Usuario> findAll() {
		return repository.findAll();
	}

	public Usuario findByLoginAndSenha(String login, String senha) {
		return repository.findByLoginAndSenha(login, senha);
	}

	public Usuario findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public Usuario getOne(Long arg0) {
		return repository.getOne(arg0);
	}

	public <S extends Usuario> List<S> save(Iterable<S> arg0) {
		return repository.save(arg0);
	}

	public <S extends Usuario> S save(S arg0) {
		return repository.save(arg0);
	}

	public Usuario findByLogin(String login) {
		return repository.findByLogin(login);
	}

}
