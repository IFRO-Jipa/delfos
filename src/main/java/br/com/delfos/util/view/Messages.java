package br.com.delfos.util.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import javafx.scene.control.Control;

class Messages {
	private static Properties props;
	private static String defaultMessage;

	private static String notFoundMessage;

	static {
		try {
			props = new Properties();
			props.load(new FileInputStream(
					Messages.class.getClassLoader().getResource("config/messages_validation.properties").toString()));
			defaultMessage = props.getProperty("defaultMessage");
			notFoundMessage = props.getProperty("notFoundMessage");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDefaultMessage() {
		return defaultMessage;
	}

	public static Optional<String> getMessage(String name) {
		return Optional.ofNullable(props.getProperty(name));
	}

	public static boolean isMessagePresent(String name) {
		return getMessage(name).isPresent();
	}

	public static Object semEntradaDeTextoPara(Control component) {
		return String.format(notFoundMessage, component == null ? "componente desconhecido" : component.getId());
	}
}