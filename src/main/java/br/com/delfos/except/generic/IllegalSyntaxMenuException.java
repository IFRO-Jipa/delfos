package br.com.delfos.except.generic;

public class IllegalSyntaxMenuException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public IllegalSyntaxMenuException() {
		super();
	}

	public IllegalSyntaxMenuException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalSyntaxMenuException(String s) {
		super(s);
	}

	public IllegalSyntaxMenuException(Throwable cause) {
		super(cause);
	}

}
