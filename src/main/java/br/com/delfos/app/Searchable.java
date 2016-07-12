package br.com.delfos.app;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import br.com.delfos.control.search.AbstractSearchableController;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.view.AlertAdapter;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Seletor padrão e personalizavel de entidades criadas para persistencia, onde seu filtro é
 * gerenciado sob uma lista de entidades válidas, conforme implementação.
 * 
 * <br>
 * exemplo de utilização:
 * 
 * <pre>
 * <code>
 * 
 *	List<Pessoa> pessoas = //...
 *
 *	Map<String, Function<Pessoa, String>> comparators = new HashMap<>();
 *
 *	comparators.put("Nome", Pessoa::getNome);
 *	comparators.put("CPF", Pessoa::getCpf);
 *	comparators.put("RG", Pessoa::getRg);
 *
 *	Searchable<Pessoa>seletor = new Searchable<Pessoa>(FXCollections.observableArrayList(pessoas), comparators);
 *	Optional<Pessoa>selected = seletor.setText(Pessoa::getNome).setDescription(this::configNome).showAndWait();
 * 	
 *  // caso obtenha algum retorno
 *	selected.ifPresent(pessoa -> {
 *		System.out.println("Elemento selecionado: " + pessoa);
 *	});
 *
 *</code>
 * </pre>
 * 
 * @author Leonardo Braz
 *
 * @param <T>
 *            Entidade do tipo AbstractModel
 * 
 * @see AbstractModel
 * @see Function
 * @see Optional
 * @since 1.8
 * @version 1.0
 */
public class Searchable<T extends AbstractModel<T>> {

	private static Stage stage;
	private AbstractSearchableController<T> controller;

	private class SearchableApp extends Application {

		AbstractSearchableController<?> controller;

		private BorderPane pane;

		@Override
		public void start(Stage primaryStage) throws Exception {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Searchable.class.getClassLoader().getResource("fxml/generic/Search.fxml"));
			loader.setController(this.controller);
			pane = loader.load();

			primaryStage.setScene(new Scene(pane));
			primaryStage.setTitle("Selecione um registro");
			primaryStage.showAndWait();

			Searchable.stage = primaryStage;
		}

	}

	public Searchable(ObservableList<T> itens, Map<String, Function<T, String>> comparators) {

		this.controller = new AbstractSearchableController<>(itens, comparators);
	}

	public Searchable(AbstractSearchableController<T> impl) {
		this.controller = impl;
	}

	public Searchable<T> setComparators(Map<String, Function<T, String>> comparators) {
		controller.setComparators(comparators);
		return this;
	}

	public Map<String, Function<T, String>> getComparators() {
		return controller.getComparators();
	}

	protected Optional<T> getSelected() {
		return controller.getSelected();
	}

	public Searchable<T> setTarget(ObservableList<T> itens) {
		controller.setTarget(itens);
		return this;
	}

	public Searchable<T> setDescription(Function<T, String> function) {
		controller.setDescription(function);
		return this;
	}

	public Searchable<T> setText(Function<T, String> text) {
		controller.setText(text);
		return this;
	}

	public Optional<T> showAndWait() {
		try {
			Searchable<T>.SearchableApp app = this.new SearchableApp();
			app.controller = this.controller;
			app.start(getStage());

			return this.getSelected();
		} catch (Exception e) {
			AlertAdapter.error(e);
			return Optional.empty();
		}
	}

	private Stage getStage() {
		if (stage != null) {
			return stage;
		} else {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			return stage;
		}
	}

}
