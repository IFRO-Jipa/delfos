package br.com.delfos.except.auditoria;

public class UserNotAuthenticatedException extends IllegalAccessException {

	private static final long serialVersionUID = 1L;

	public UserNotAuthenticatedException() {
		super("Falha na autentica��o. Usu�rio e/ou senha incorretas");
	}

	public UserNotAuthenticatedException(String s) {
		super(s);
	}

}
