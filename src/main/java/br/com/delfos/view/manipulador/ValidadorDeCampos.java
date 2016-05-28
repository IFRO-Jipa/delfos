package br.com.delfos.view.manipulador;

import java.lang.reflect.Field;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * <p>
 * Classe responsável por realizar validação automática de campos (<code>Control</code> do JavaFX,
 * tais como <code>TextField</code>, <code>CheckBox</code>, <code>TextArea</code>, etc), afim de
 * descobrir se o usuário informou todas as informações necessárias.
 * 
 * <p>
 * Para definir que um campo tem seu preenchimento obrigatório, adicione a anotação @NotNull acima
 * do elemento que deseja validar.
 * 
 * 
 * <p>
 * <strong>Exemplo:</strong>
 * 
 * <blockquote>
 * 
 * <pre>
 * &#64;FXML
 * &#64;NotNull
 * private TextField textField;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author Leonardo Braz
 *
 */
public class ValidadorDeCampos {

	/*
	 * Mensagem padrão para as exceptions que serão lançadas.
	 */
	private static final String INVALID_FIELD_MESSAGE = "O campo %s é de preenchimento obrigatório.";

	/**
	 * <p>
	 * Esse método faz a verificação dos campos nulos de uma determinada classe (geralmente é um
	 * Controller para um FXML). Sua validação será feita a partir dos atributos da classe passada
	 * por parâmetro.
	 * <p>
	 * Caso o atributo marcado (lembrando que seja um tipo de Control visual do JavaFX), será feita
	 * a verificação para seu determinado tipo, afim de saber se foi informado ou não algum valor
	 * pelo usuário
	 * 
	 * <p>
	 * <strong>Exemplo</strong>
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * public class ExampleController {
	 *     &#64;FXML
	 *     &#64;NotNull
	 *     private TextField textField;
	 * 
	 *     &#64;FXML
	 *     private TextArea textArea;
	 * 
	 *     &#64;FXML
	 *     &#64;NotNull
	 *     private ComboBox<?> comboBox;
	 * 
	 *     public void action() {
	 *         if (ValidadorDeCampos.validateAll(this)) {
	 *             // vai verificar se o textField possui texto e se foi selecionado algum
	 *             // valor no comboBox. Será deixado de lado o textArea, já que não foi
	 *             // anotado como NotNull
	 *         }
	 *     }
	 * 
	 * }
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param controller
	 *            - Classe controladora (conterá os campos)
	 * @return status da validação (campos preenchidos ou não).
	 */
	public static boolean validateAll(Object controller) {
		boolean valid = true;

		try {
			Class<?> clazz = controller.getClass();

			for (Field field : clazz.getDeclaredFields()) {
				if (!valid)
					break;
				if (field.isAnnotationPresent(NotNull.class)) {
					field.setAccessible(true);
					Object obj = field.get(controller);
					valid = validaComponent((Node) obj);

				}
			}

		} catch (SecurityException e) {
			System.out.println(e);

		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.println(e);
		}
		return valid;
	}

	private static boolean validaComponent(Node obj) {
		if (obj instanceof TextField) {
			return valida((TextField) obj);
		} else if (obj instanceof CheckBox) {
			return valida((CheckBox) obj);
		} else if (obj instanceof ComboBox<?>) {
			return valida((ComboBox<?>) obj);
		} else if (obj instanceof TextArea) {
			return valida((TextArea) obj);
		}

		return false;
	}

	public static boolean valida(CheckBox obj) {
		if (!obj.isSelected())
			lancaException(obj);
		return true;
	}

	private static void lancaException(Control obj) {
		obj.requestFocus();
		throw new ValidationException(getMessage(obj));
	}

	public static boolean valida(ComboBox<?> obj) {
		if (obj.getSelectionModel().getSelectedItem() == null)
			lancaException(obj);
		return true;
	}

	public static boolean valida(TextArea obj) throws ValidationException {
		if (obj.getText().isEmpty())
			lancaException(obj);
		return true;
	}

	public static boolean valida(TextField obj) {
		if (obj.getText().isEmpty())
			lancaException(obj);
		return true;
	}

	private static String getMessage(Control name) {
		return String.format(INVALID_FIELD_MESSAGE,
		        name.getTooltip() != null ? name.getTooltip().getText() : name.getId());
	}

}
