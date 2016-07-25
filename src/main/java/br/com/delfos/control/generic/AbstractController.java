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

	protected boolean deleteIf(Predicate<Type> predicate) {
		try {
			getValue().ifPresent(value -> {
				if (predicate.test(value)) {
					if (AlertAdapter.confirmation(AlertAdapter.ALERT_CONFIRM_EXCLUSAO,
							"Isso fará com que a informação seja apagada e sem a possibilidade de restauração.\nDeseja continuar?")) {
						dao.delete(value.getId());
						AlertAdapter.information("Exclusão realizada",
								"A informação foi apagada sem nenhuma complicação.");
					}
				} else {
					AlertAdapter.warning("Nada em aberto",
							"Nenhuma informação está pronta para ser apagada. Selecione algum registro e refaça a operação.");
				}
			});
			return true;
		} catch (DataIntegrityViolationException e) {
			AlertAdapter.dataIntegrityViolation(
					"Não foi possível excluir esse registro.\nEle está sendo utilizado para compor informações de outros dados no sistema.\n");
			return false;
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
				AlertAdapter.warning("Nada encontrado", "Não foram encontradas informações na consulta.");
			}

		}

		return Optional.empty();

	}

	protected DataAcessObject getDao() {
		return dao;
	}

}