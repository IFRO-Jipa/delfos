package br.com.delfos.control;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delfos.dao.auditoria.UsuarioDAO;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.model.auditoria.Usuario;

@Service
class AutenticadorDeUsuario {

	@Autowired
	private UsuarioDAO dao;

	private static Optional<Usuario> usuario;

	public boolean autentica(String login, String senha) {

		try {
			String senhaInformada = getSenhaCriptografada(login, senha);
			usuario = Optional.ofNullable(dao.findByLoginAndSenha(login, senhaInformada));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return usuario.isPresent();
	}

	
	
	private String getSenhaCriptografada(String usuario, String minhaSenha)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		builder.append(usuario.hashCode());
		builder.append(minhaSenha);
		builder.append("delfosdoneriozao".hashCode());
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(builder.toString().getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		return hexString.toString();
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
