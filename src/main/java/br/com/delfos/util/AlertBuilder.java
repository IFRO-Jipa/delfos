package br.com.delfos.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Classe responsável por manipular os alertas (<code>Alert</code>) gerados pelo
 * software. Seus tipos são: INFO, WARNING, ERROR, NOTIFICATION e DEFAULT.
 * 
 * 
 * @author lhleo
 *
 */
public class AlertBuilder {

	private static Alert alert;

	static {
		alert = new Alert(AlertType.INFORMATION);
	}

	public static void warning(String mensagem) {
		alert.setAlertType(AlertType.WARNING);
		alert.setTitle("Aviso do sistema");
		alert.setHeaderText("Aviso importante");
		alert.setContentText(mensagem);

		alert.showAndWait();
	}

	public static void error(String msg, Exception ex, boolean expandable) {
		alert.setTitle("Erro encontrado");
		alert.setAlertType(AlertType.ERROR);
		alert.setHeaderText(msg == null ? ex.getMessage() : msg);
		alert.setContentText("Falha localizada");

		if (expandable) {
			// Create expandable Exception.
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();

			Label label = new Label("Detalhes técnicos: ");

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			alert.getDialogPane().setExpandableContent(expContent);
		}

		alert.showAndWait();
	}

	public static void information(String mensagem) {
		alert.setAlertType(AlertType.INFORMATION);
		alert.setTitle("Mensagem de Informação");
		alert.setHeaderText(mensagem);
		alert.setContentText("Notificação do sistema");
		alert.showAndWait();
	}

	public static boolean confirmation(String mensagem) {
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.setTitle("Mensagem de Confirmação");
		alert.setHeaderText(mensagem);
		alert.setContentText("Escolha a opção desejada para a solicitação");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

}
