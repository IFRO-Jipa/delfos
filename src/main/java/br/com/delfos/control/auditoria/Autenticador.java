package br.com.delfos.control.auditoria;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delfos.app.MudarSenhaApp;
import br.com.delfos.dao.auditoria.UsuarioDAO;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.view.AlertAdapter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Service
public class Autenticador {

	@Autowired
	private UsuarioDAO dao;
	private static Optional<Usuario> usuario;

	public boolean autentica(String login, String senha) {
		usuario = dao.autentica(login, senha);

		usuario.ifPresent(autenticado -> {
			if (autenticado.getPessoa().getCpf().equals(senha)) {
				AlertAdapter.information("Primeiro acesso ao sistema",
						"Por ser a primeira vez que você acessa ao sistema, é necessário que crie uma nova senha.\nModifique-a após fechar essa mensagem (a tela aparecerá automaticamente).");
				try {
					MudarSenhaApp frame = new MudarSenhaApp();
					frame.requerMudanca(true);
					frame.start(new Stage(StageStyle.UNDECORATED));

				} catch (IOException e) {
					AlertAdapter.erroLoadFXML(e);
				}
			}
		});

		return usuario.isPresent();
	}

	public static Usuario getUsuarioAutenticado() {
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