package br.com.delfos.view;

import java.awt.Toolkit;
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
 * @author Leonardo Braz
 *
 */
public class AlertAdapter {

	private static Alert alert;

	public static final String ALERT_ERRO_SALVAR = "";
	public static final String ALERT_ERRO_EXCLUIR = "";
	public static final String ALERT_ERRO_PESQUISAR = "";
	public static final String ALERT_CONFIRM_EXCLUSAO = "Deseja realmente excluir o registro?";
	public static final String ALERT_CONFIRM_EXIT = "Deseja realmente sair?";

	public static void warning(String mensagem) {
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Aviso do sistema");
		alert.setContentText("Aviso importante");
		alert.setHeaderText(mensagem);
		beep();

		alert.showAndWait();
	}

	public static void beep() {
		Toolkit.getDefaultToolkit().beep();
	}

	public static void error(String msg) {
		error(msg, null, false);
	}

	public static void error(Exception ex) {
		error(ex.getMessage(), ex, true);
	}

	private static void error(String msg, Exception ex, boolean expandable) {
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro encontrado");
		alert.setContentText(msg == null ? ex.getMessage() : msg);
		alert.setHeaderText("Ocorreu uma falha inesperada.");

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
			expContent.setMaxHeight(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			alert.getDialogPane().setExpandableContent(expContent);
		}
		beep();
		alert.showAndWait();
	}

	public static void information(String mensagem) {
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Mensagem de Informação");
		alert.setHeaderText(mensagem);
		alert.setContentText("Notificação do sistema");
		alert.showAndWait();
	}

	public static boolean confirmation(String mensagem) {
		alert = new Alert(AlertType.CONFIRMATION);
		// alert.setAlertType(AlertType.CONFIRMATION);
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
