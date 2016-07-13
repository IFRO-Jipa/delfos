package br.com.delfos.control.search;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
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

class AbstractSearchableController<T extends AbstractModel<T>> implements SearchableImpl<T>, Initializable {

	@FXML
	private ComboBox<String> cbFiltro;

	@FXML
	private TextField txtFiltro;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private ListView<T> listViewItens;

	private Map<String, BiPredicate<T, String>> comparators;

	private T selectedItem;

	private FilteredList<T> itens;

	private Function<T, String> functionDescription;

	private Function<T, String> functionText;

	private boolean closeAfterDoubleClick = true;

	private Optional<Consumer<T>> consumerPersonalizadoDoubleClick;

	/*
	 * Personalização dos estados da funcionalidade
	 */

	public AbstractSearchableController(ObservableList<T> itens, Map<String, BiPredicate<T, String>> comparators) {
		initComparators();
		setComparators(comparators);
		setTarget(new FilteredList<>(itens));
	}

	private void initComparators() {
		if (this.comparators == null)
			this.comparators = new HashMap<>();
		comparators.put("ID", (obj, filtro) -> {
			return obj.getId().toString().contains(filtro);
		});
	}

	public AbstractSearchableController(ObservableList<T> itens) {
		initComparators();
		setTarget(itens);
	}

	public void setConsumerPersonalizadoDoubleClick(Consumer<T> consumerPersonalizadoDoubleClick) {
		this.consumerPersonalizadoDoubleClick = Optional.ofNullable(consumerPersonalizadoDoubleClick);
	}

	@Override
	public void setComparators(Map<String, BiPredicate<T, String>> comparators) {
		this.comparators = comparators;
	}

	@Override
	public Map<String, BiPredicate<T, String>> getComparators() {
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
		// TODO retirar da implementação
	}

	@Override
	public void setDescription(Function<T, String> function) {
		this.functionDescription = function;
	}

	@Override
	public void setText(Function<T, String> text) {
		this.functionText = text;
	}

	public void setOnCloseAfterDoubleClick(boolean status) {
		this.closeAfterDoubleClick = status;
	}

	/*
	 * Configuração a partir das configurações fornecidas
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.configureFilter();
		this.configureListView();
		this.configureComboBox();
	}

	private void configureComboBox() {
		this.cbFiltro.setItems(FXCollections.observableArrayList(this.comparators.keySet()));
	}

	private void configureListView() {
		this.listViewItens.setCellFactory(new TableCellFactory<T>(listViewItens).getCellFactory(functionText));

		this.listViewItens.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
			this.txtDescricao.setText(this.functionDescription.apply(newV));
		});

		this.listViewItens.setItems(itens);

		this.configDoubleClickListView();
	}

	private void configDoubleClickListView() {
		this.listViewItens.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				T selectedItem = this.listViewItens.getSelectionModel().getSelectedItem();
				if (consumerPersonalizadoDoubleClick.isPresent()) {
					// executa o código personalizado
					Consumer<T> consumer = this.consumerPersonalizadoDoubleClick.get();

					consumer.accept(selectedItem);
				} else {
					// marca o item selecionado e diz que a tela deve ser fechada
					this.selectedItem = selectedItem;
					this.closeAfterDoubleClick = true;
				}
				fechaTela();
			}
		});
	}

	private void fechaTela() {
		if (closeAfterDoubleClick) {
			br.com.delfos.control.search.AbstractSearchable.close();
		}
	}

	private void configureFilter() {
		this.txtFiltro.textProperty().addListener((obs, oldValue, newValue) -> {
			this.itens.setPredicate(obj -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String key = this.cbFiltro.getValue();

				if (key != null && !key.isEmpty() && this.comparators.containsKey(key))
					return this.comparators.get(key).test(obj, newValue);
				else if (!this.comparators.containsKey(key)) {
					return true;
				}

				return false;
			});
		});
	}

	@Override
	public void addComparing(String key, BiPredicate<T, String> comparation) {
		this.comparators.put(key, comparation);
	}

}
