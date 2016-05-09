package br.com.delfos.control;

import java.util.Optional;

import br.com.delfos.dao.AbstractDAO;
import br.com.delfos.model.AbstractModel;
import br.com.delfos.util.ManipuladorDeComponentes;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;

public abstract class AbstractController<Type extends AbstractModel<Type>, DataAcessObject extends AbstractDAO<Type, Long, ?>> {

	private DataAcessObject dao;

	protected abstract Type monta();

	protected abstract void posiciona(Type type);

	public void posicionaRegistro(Type type) {
		this.posiciona(type);
	}

	public Type montaRegistro() {
		return this.monta();
	}

	protected Type salvar(Type value, Pane rootPane) {
		if (ManipuladorDeComponentes.validaCampos(rootPane)) {
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
}