package br.com.delfos.dao.auditoria;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.repository.auditoria.UsuarioRepository;

@Repository
public class UsuarioDAO extends AbstractDAO<Usuario, Long, UsuarioRepository> {

	private Optional<Usuario> findByLoginAndSenha(String login, String senha) {
		String senhaCriptografada = getSenhaCriptografada(login, senha);
		return repository.findByLoginAndSenha(login, senhaCriptografada);
	}

	private Optional<Usuario> findByLogin(String login) {
		return repository.findByLogin(login);
	}

	public Optional<Usuario> autentica(String login, String senha) {
		return this.findByLoginAndSenha(login, senha);
	}

	public boolean exists(String login) {
		return !findByLogin(login).isPresent();
	}

	private String getSenhaCriptografada(String usuario, String minhaSenha) {

		try {
			StringBuilder builder = new StringBuilder();
			builder.append(usuario.hashCode());
			builder.append(minhaSenha);
			// builder.append(getSenhaCriptografada(getSenhaCriptografada("software",
			// "delfos").hashCode(),
			// getSenhaCriptografada("programa de", "qualidade e segurança")).hashCode());
			//
			// builder.append("softwaredelfosprogramadequalidade".hashCode());
			builder.append("delfosdoneriozao".hashCode());
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(builder.toString().getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return "";
		}
	}

}
