package br.com.delfos.control;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delfos.dao.UsuarioDAO;
import br.com.delfos.model.Funcionalidade;
import br.com.delfos.model.Usuario;

@Service
class AutenticadorDeUsuario {

	@Autowired
	private UsuarioDAO dao;

	private static Optional<Usuario> usuario;

	public boolean autentica(String login, String senha) {
		usuario = Optional.ofNullable(dao.findByLoginAndSenha(login, senha));

		if (usuario.isPresent())
			return true;

		return false;
	}

	public static Usuario getUsuarioAutenticado() {
		return usuario.get();
	}

	public static Set<Funcionalidade> getPermissoesDeAcesso() {
		return usuario.get().getPerfilAcesso().getPermissoes();
	}

	public static void logout() {
		AutenticadorDeUsuario.usuario = null;
	}
}
