package br.com.delfos.control.search;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

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
public class AbstractSearchable<T extends AbstractModel<T>> {

	private static Stage stage;
	private AbstractSearchableController<T> controller;

	public AbstractSearchable(ObservableList<T> itens, Map<String, BiPredicate<T, String>> comparators) {

		this.controller = new AbstractSearchableController<>(itens, comparators);
	}

	public AbstractSearchable(AbstractSearchableController<T> impl) {
		this.controller = impl;
	}

	public AbstractSearchable<T> setComparators(Map<String, BiPredicate<T, String>> comparators) {
		controller.setComparators(comparators);
		return this;
	}

	public Map<String, BiPredicate<T, String>> getComparators() {
		return controller.getComparators();
	}

	protected Optional<T> getSelected() {
		return controller.getSelected();
	}

	public AbstractSearchable<T> setTarget(ObservableList<T> itens) {
		controller.setTarget(itens);
		return this;
	}

	public AbstractSearchable<T> setDescription(Function<T, String> function) {
		controller.setDescription(function);
		return this;
	}

	public AbstractSearchable<T> setText(Function<T, String> text) {
		controller.setText(text);
		return this;
	}

	public AbstractSearchable<T> setConsumerPersonalizadoDoubleClick(Consumer<T> consumerPersonalizadoDoubleClick) {
		controller.setConsumerPersonalizadoDoubleClick(consumerPersonalizadoDoubleClick);
		return this;
	}

	public AbstractSearchable<T> setOnCloseAfterDoubleClick(boolean status) {
		controller.setOnCloseAfterDoubleClick(status);
		return this;
	}

	/*
	 * ---------------------------------------------
	 */

	private class SearchableApp extends Application {

		AbstractSearchableController<?> controller;

		public BorderPane getPanel() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AbstractSearchable.class.getClassLoader().getResource("fxml/generic/Search.fxml"));
			loader.setController(this.controller);
			return loader.load();
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			BorderPane pane = getPanel();

			primaryStage.setScene(new Scene(pane));
			primaryStage.setTitle("Selecione um registro");
			primaryStage.showAndWait();

			AbstractSearchable.stage = primaryStage;
		}

	}

	public Optional<T> showAndWait() {
		try {
			AbstractSearchable<T>.SearchableApp app = this.new SearchableApp();
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

	public static void close() {
		stage.hide();
	}

	/*
	 * --------------------------------------------------------------------
	 */

	public Optional<BorderPane> getPanel() {
		try {
			AbstractSearchable<T>.SearchableApp app = this.new SearchableApp();
			app.controller = this.controller;
			return Optional.ofNullable(app.getPanel());
		} catch (IOException e) {
			AlertAdapter.error(e);
			return Optional.empty();
		}
	}

}
