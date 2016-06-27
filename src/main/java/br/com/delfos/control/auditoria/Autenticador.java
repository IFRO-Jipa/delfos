package br.com.delfos.control.auditoria;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delfos.dao.auditoria.UsuarioDAO;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.model.basic.Pessoa;

@Service
public class Autenticador {

	@Autowired
	private UsuarioDAO dao;
	private static Optional<Usuario> usuario;

	public boolean autentica(String login, String senha) {
		usuario = dao.autentica(login, senha);

		return usuario.isPresent();
	}

	public static Usuario getUsuarioAutenticado() {
		return usuario.get();
	}

	public static Set<Funcionalidade> getPermissoesDeAcesso() {
		return Collections.unmodifiableSet(usuario.get().getPerfilAcesso().getPermissoes());
	}

	public static Pessoa getDonoDaConta() {
		return usuario.get().getPessoa();
	}

	public static void logout() {
		usuario = null;
	}

}