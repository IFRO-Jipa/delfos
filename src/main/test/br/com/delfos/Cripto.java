package br.com.delfos;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cripto {
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		String senhaSalva = getSenhaCriptografada("root", "root");

		String senhaInformada = getSenhaCriptografada("root", "root");

		System.out.printf("%s : %s\n", senhaSalva, senhaInformada);

		System.out.println(senhaSalva.equals(senhaInformada));
	}

	public static String getSenhaCriptografada(String usuario, String minhaSenha)
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
}
