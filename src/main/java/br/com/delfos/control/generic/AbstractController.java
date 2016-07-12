package br.com.delfos.control.generic;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.util.Regex;
import br.com.delfos.util.view.FXValidator;
import br.com.delfos.util.view.TextFieldFormatter;
import br.com.delfos.view.AlertAdapter;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.util.converter.IntegerStringConverter;

public abstract class AbstractController<Type extends AbstractModel<Type>, DataAcessObject extends AbstractDAO<Type, Long, ?>>
		implements Initializable {

	@Autowired
	private DataAcessObject dao;

	protected abstract Type toValue();

	protected abstract void posiciona(Optional<Type> value);

	protected void posiciona(Type value) {
		this.posiciona(Optional.ofNullable(value));
	}

	public void posicionaRegistro(Optional<Type> type) {
		this.posiciona(type);
	}

	public Optional<Type> getValue() {
		return Optional.ofNullable(this.toValue());
	}

	protected Optional<Type> salvar(Type value, Object controller) throws FXValidatorException {
		if (FXValidator.validate(controller) && value != null) {
			return dao.save(value);
		} else
			return Optional.empty();
	}

	protected void deleteIf(Predicate<Type> predicate) {
		try {
			getValue().ifPresent(value -> {
				if (predicate.test(value)) {
					if (AlertAdapter.confirmation(AlertAdapter.ALERT_CONFIRM_EXCLUSAO)) {
						dao.delete(value.getId());
						AlertAdapter.information("Excluído com sucesso");
					}
				} else {
					AlertAdapter.warning("Selecione um registro para excluir.");
				}
			});
		} catch (DataIntegrityViolationException e) {
			AlertAdapter
					.error("Não foi possível excluir esse registro.\nEle está sendo associado com outras informações.");
		}
	}

	protected Optional<Type> pesquisaPorCodigo() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("PRÉVIA - Consulta de Registros");
		dialog.setContentText("informe o código do registro para prosseguir");

		dialog.getEditor().setTextFormatter(
				TextFieldFormatter.getFormatter(new IntegerStringConverter(), 0, Regex.APENAS_NUMEROS));

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Optional<Type> value = Optional.ofNullable(dao.findOne(Long.parseLong(result.get())));

			if (value.isPresent()) {
				this.posiciona(value);
				return value;
			} else {
				AlertAdapter.warning("Nenhum registro foi encontrado.");
			}

		}

		return Optional.empty();

	}

	protected boolean removeDaTabela(TableView<Type> tableView, Predicate<Type> predicate) {
		return tableView.getItems().removeIf(predicate);
	}
}