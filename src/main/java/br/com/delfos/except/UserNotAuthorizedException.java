package br.com.delfos.except;

public class UserNotAuthorizedException extends IllegalAccessException {

	private static final long serialVersionUID = 1L;

	public UserNotAuthorizedException() {
		super("Falha na autenticação. Usuário e/ou senha incorretas");
	}

	public UserNotAuthorizedException(String s) {
		super(s);
	}

}
