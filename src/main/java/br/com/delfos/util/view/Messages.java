package br.com.delfos.util.view;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import br.com.delfos.view.AlertBuilder;
import javafx.scene.control.Control;

class Messages {
	private static Properties props;
	private static String defaultMessage;

	private static String notFoundMessage;

	public static String getDefaultMessage() {
		if (defaultMessage == null) {
			loadFile();
		}
		return defaultMessage;
	}

	private static void loadFile() {
		try {
			props = new Properties();
			// props.load(new FileInputStream(
			// Messages.class.getClassLoader().getResource("config/messages_validation.properties").toString()));
			props.load(Messages.class.getClassLoader().getResourceAsStream("config/messages_validation.properties"));
			defaultMessage = props.getProperty("defaultMessage");
			notFoundMessage = props.getProperty("notFoundMessage");

		} catch (IOException e) {
			AlertBuilder.error(e);
		}
	}

	public static Optional<String> getMessage(String name) {
		if (props == null) {
			loadFile();
		}
		return Optional.ofNullable(props.getProperty(name));
	}

	public static boolean isMessagePresent(String name) {
		return getMessage(name).isPresent();
	}

	public static Object semEntradaDeTextoPara(Control component) {
		if (notFoundMessage == null) {
			loadFile();
		}
		return String.format(notFoundMessage, component == null ? "componente desconhecido" : component.getId());
	}
}