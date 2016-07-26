package br.com.delfos.view;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import br.com.delfos.except.view.FXValidatorException;
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

	public static final String ALERT_ERRO_DADOS_OBRIGATORIOS = "#4 - Dados obrigatórios não foram informados ou inválidos";
	public static final String ALERT_ERRO_FXML_NOT_FOUND = "#5 - Falha ao carregar o arquivo(.fxml) da tela.";
	private static final String ALERT_ERRO_REGISTRO_BASE = "#3 - Registros de base (utilizados para compor outras informações) não podem ser excluídos.";
	private static final String ALERT_ERRO_PARAMETRO_INVALIDO = "#2 - Parâmetros inválidos";
	private static final String ALERT_ERRO_UNKNOWN = "#1 - Erro não identificado";

	public static void information(String resumo, String mensagem) {
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informações do sistema");
		alert.setHeaderText(resumo);
		alert.setContentText(mensagem);
		beep();

		alert.showAndWait();
	}

	public static void warning(String resumo, String mensagem) {
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Aviso do sistema");
		alert.setContentText(mensagem);
		alert.setHeaderText(resumo);
		beep();

		alert.showAndWait();
	}

	private static void beep() {
		Toolkit.getDefaultToolkit().beep();
	}

	public static void error(String resumo, String mensagem) {
		error(resumo, mensagem, null, false);
	}

	public static void error(String resumo, Exception ex) {
		error(resumo, ex.getMessage(), ex, true);
	}

	private static void error(String resumo, String msg, Exception ex, boolean expandable) {
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro encontrado");
		alert.setContentText(msg == null ? ex.getMessage() : msg);
		alert.setHeaderText(resumo);

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

	public static boolean confirmation(String resumo, String mensagem) {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmação requerida");
		alert.setHeaderText(resumo);
		alert.setContentText(mensagem);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			if (result.get() == ButtonType.OK) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static void requiredDataNotInformed(FXValidatorException e) {
		error(ALERT_ERRO_DADOS_OBRIGATORIOS, e.getMessage());
	}

	public static void requiredDataNotInformed(String msg) {
		error(ALERT_ERRO_DADOS_OBRIGATORIOS, msg);
	}

	public static void requiredDataNotInformed(ConstraintViolationException e) {
		StringBuilder mensagem = new StringBuilder();

		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			mensagem.append("-" + violation.getMessage());
			mensagem.append("\n");
		}

		error(ALERT_ERRO_DADOS_OBRIGATORIOS, mensagem.toString());
	}

	public static void erroLoadFXML(IOException e) {
		error(ALERT_ERRO_FXML_NOT_FOUND, e);
	}

	public static void unknownError(String msg) {
		error("#1 - Erro não identificado", msg);
	}

	public static void unknownError(Exception e) {
		error(ALERT_ERRO_UNKNOWN, e.getMessage(), e, true);
	}

	public static void invalidParameters(String msg) {
		error(ALERT_ERRO_PARAMETRO_INVALIDO, msg);
	}

	public static void dataIntegrityViolation(String mensagem) {
		error(ALERT_ERRO_REGISTRO_BASE, mensagem);
	}

}
