package br.com.delfos.except;

public class IllegalSintaxMenuException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public IllegalSintaxMenuException() {
		super();
	}

	public IllegalSintaxMenuException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalSintaxMenuException(String s) {
		super(s);
	}

	public IllegalSintaxMenuException(Throwable cause) {
		super(cause);
	}

}
