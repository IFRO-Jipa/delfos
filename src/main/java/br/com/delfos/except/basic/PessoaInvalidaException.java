package br.com.delfos.except.basic;

public class PessoaInvalidaException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public PessoaInvalidaException() {
		super();
	}

	public PessoaInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PessoaInvalidaException(String message) {
		super(message);
	}

	public PessoaInvalidaException(Throwable cause) {
		super(cause);
	}

}
