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
import br.com.delfos.view.AlertAdapter;

@Service
public class Autenticador {

	@Autowired
	private UsuarioDAO dao;
	private static Optional<Usuario> usuario;

	public boolean autentica(String login, String senha) {
		usuario = dao.autentica(login, senha);

		usuario.ifPresent(autenticado -> {
			if (autenticado.getPessoa().getCpf().equals(senha)) {
				AlertAdapter.warning(
						"CUIDADO! Sua senha não foi modificada desde a criação padrão.\nPara maior segurança, modifique-a (Barra de Menu > Conta > Mudar Senha)");
			}
		});

		return usuario.isPresent();
	}

	public static Usuario getUsuarioAutenticado() {
		System.out.println(usuario.get());
		return usuario.get();
	}

	public static Set<Funcionalidade> getPermissoesDeAcesso() {
		return Collections.unmodifiableSet(usuario.get().getPerfilAcesso().getPermissoes());
	}

	public static Pessoa getDonoDaConta() {
		return getUsuarioAutenticado().getPessoa();
	}

	public static void logout() {
		usuario = null;
	}

}