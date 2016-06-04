package br.com.delfos.except.pesquisa;

public class LimiteDeEspecialistasAtingidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public LimiteDeEspecialistasAtingidoException() {
		super();
	}

	public LimiteDeEspecialistasAtingidoException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LimiteDeEspecialistasAtingidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public LimiteDeEspecialistasAtingidoException(String message) {
		super(message);
	}

	public LimiteDeEspecialistasAtingidoException(Throwable cause) {
		super(cause);
	}

}
