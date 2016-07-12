package br.com.delfos.control.search;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.util.TableCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AbstractSearchableController<T extends AbstractModel<T>> implements Searchable<T>, Initializable {

	@FXML
	private ComboBox<String> cbFiltro;

	@FXML
	private TextField txtFiltro;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private ListView<T> listViewItens;

	private Map<String, Function<T, String>> comparators;

	private T selectedItem;

	private FilteredList<T> itens;

	private Function<T, String> functionDescription;

	private Function<T, String> functionText;

	public AbstractSearchableController(ObservableList<T> itens, Map<String, Function<T, String>> comparators) {
		initComparators();
		setComparators(comparators);
		setTarget(new FilteredList<>(itens));
	}

	private void initComparators() {
		if (this.comparators == null)
			this.comparators = new HashMap<>();
		comparators.put("ID", obj -> obj.getId().toString());
	}

	public AbstractSearchableController(ObservableList<T> itens) {
		initComparators();
		setTarget(itens);
	}

	@Override
	public void setComparators(Map<String, Function<T, String>> comparators) {
		this.comparators = comparators;
	}

	@Override
	public Map<String, Function<T, String>> getComparators() {
		return this.comparators;
	}

	@Override
	public Optional<T> getSelected() {
		return Optional.ofNullable(selectedItem);
	}

	protected ObservableList<T> getTarget() {
		return this.itens;
	}

	@Override
	public void setTarget(ObservableList<T> itens) {
		this.itens = new FilteredList<>(itens);
	}

	@Override
	public void search() {

	}

	@Override
	public void setDescription(Function<T, String> function) {
		this.functionDescription = function;
	}

	@Override
	public void setText(Function<T, String> text) {
		// listViewCellFactory = new TableCellFactory<T>(listViewItens).getCellFactory(text);
		this.functionText = text;
	}

	private void configureComboBox() {
		this.cbFiltro.setItems(FXCollections.observableArrayList(this.comparators.keySet()));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.configureFilter();
		this.configureListView();
		this.configureComboBox();
	}

	private void configureListView() {
		this.listViewItens.setCellFactory(new TableCellFactory<T>(listViewItens).getCellFactory(functionText));

		this.listViewItens.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
			this.txtDescricao.setText(this.functionDescription.apply(newV));
		});

		this.listViewItens.setItems(itens);
	}

	private void configureFilter() {
		this.txtFiltro.textProperty().addListener((obs, oldValue, newValue) -> {
			this.itens.setPredicate(obj -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String key = this.cbFiltro.getValue();

				if (key != null && !key.isEmpty() && this.comparators.containsKey(key))
					return this.compare(obj, newValue, this.comparators.get(key));
				else if (!this.comparators.containsKey(key)) {
					return true;
				}

				return false;
			});
		});
	}

	@Override
	public void addComparing(String key, Function<T, String> comparation) {
		this.comparators.put(key, comparation);
	}

	public boolean compare(T obj, String filter, Function<T, String> function) {
		System.out.println("1 - " + filter.toLowerCase());
		System.out.println("2 - " + function.apply(obj).toLowerCase());
		System.out.println("3 - " + filter.toLowerCase().contains(function.apply(obj).toLowerCase()));
		System.out.println("4 - " + function.apply(obj).toLowerCase().contains(filter.toLowerCase()));
		System.out.println(" -------------------------- ");
		return function.apply(obj).toLowerCase().contains(filter.toLowerCase());
	}

}
