package br.com.delfos.control;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.util.view.FXValidator;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;

public abstract class AbstractController<Type extends AbstractModel<Type>, DataAcessObject extends AbstractDAO<Type, Long, ?>>  implements Initializable{

	private DataAcessObject dao;

	protected abstract Type monta();

	protected abstract void posiciona(Type type);

	public void posicionaRegistro(Type type) {
		this.posiciona(type);
	}

	public Type montaRegistro() {
		return this.monta();
	}

	protected Type salvar(Type value, Pane rootPane) throws FXValidatorException {
		
		if (FXValidator.validate(rootPane)) {
			Optional<Type> save = dao.save(value);
			return save.get();
		} else
			return null;
	}

	public Type pesquisaPorCodigo(Pane rootPane) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("PRÉVIA - Consulta de Registros");
		dialog.setContentText("informe o código do registro para prosseguir");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Optional<Type> optional = Optional.ofNullable(dao.findOne(Long.parseLong(result.get())));
			return optional.isPresent() ? optional.get() : null;
		}

		return null;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}