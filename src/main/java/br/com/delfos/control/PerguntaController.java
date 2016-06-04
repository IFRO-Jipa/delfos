package br.com.delfos.control;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import br.com.delfos.converter.table.cell.ConverterComboBoxToCell;
import br.com.delfos.model.pesquisa.TipoPergunta;
import br.com.delfos.view.table.factory.ComboBoxCellFactory;
import br.com.delfos.view.table.factory.TextFieldCellFactory;
import br.com.delfos.view.table.property.PerguntaProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

@Controller
public class PerguntaController implements Initializable {

	@FXML
	private TableView<PerguntaProperty> tbPerguntas;

	@FXML
	private TableColumn<PerguntaProperty, String> columnNome;

	@FXML
	private TableColumn<PerguntaProperty, TipoPergunta> columnTipo;

	@FXML
	private TableColumn<PerguntaProperty, PerguntaProperty> columnAcao;

	private final ObservableList<TipoPergunta> tiposDePergunta = FXCollections
	        .observableArrayList(TipoPergunta.getAll());

	private ObservableList<PerguntaProperty> dadosTabela = FXCollections.emptyObservableList();

	@FXML
	private ComboBox<TipoPergunta> txtTipoPergunta;

	@FXML
	private Button btnAddPergunta;

	@FXML
	private TextField txtNomePergunta;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTablePergunta();
	}

	private void initTablePergunta() {

		initColumnNome();
		initColumnTipoPergunta();
		initColumnAcao();

		this.tbPerguntas.setEditable(true);
		this.tbPerguntas.setItems(dadosTabela);

	}

	private void initColumnAcao() {
		columnAcao.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		columnAcao.setResizable(false);
		columnAcao.setCellFactory(getButtonFactory());

	}

	private Callback<TableColumn<PerguntaProperty, PerguntaProperty>, TableCell<PerguntaProperty, PerguntaProperty>>

	        getButtonFactory() {
		return param -> new TableCell<PerguntaProperty, PerguntaProperty>() {
			final Button button = new Button("...");

			{
				button.setMinWidth(columnAcao.getWidth() - 6);
			}

			@Override
			protected void updateItem(PerguntaProperty item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setGraphic(button);
				} else {
					setGraphic(null);
				}
				button.setOnAction(event -> handleButtonAction(item));
			}

		};
	}

	protected void handleButtonAction(PerguntaProperty item) {
		Optional<PerguntaProperty> optional = Optional.ofNullable(item);
		optional.ifPresent(property -> {
			if (property.getTipoPergunta() != null) {
				System.out.println("Um tipo de pergunta foi selecionado.");
			}

			System.out.println(item);
		});
	}

	private void initColumnNome() {
		this.columnNome.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
		this.columnNome.setCellFactory(getTextFieldFactory());
		this.columnNome.setPrefWidth(1000);
	}

	private void initColumnTipoPergunta() {
		this.columnTipo.setCellValueFactory(cellData -> cellData.getValue().getTipoPerguntaProperty());
		this.columnTipo.setCellFactory(getComboBoxFactory());
	}

	private Callback<TableColumn<PerguntaProperty, TipoPergunta>, TableCell<PerguntaProperty, TipoPergunta>>
	        getComboBoxFactory() {

		return param -> new ComboBoxCellFactory<PerguntaProperty, TipoPergunta>(tiposDePergunta,
		        new ConverterComboBoxToCell<TipoPergunta>().setToString(obj -> obj.name()).convert());
	}

	private Callback<TableColumn<PerguntaProperty, String>, TableCell<PerguntaProperty, String>> getTextFieldFactory() {
		return param -> new TextFieldCellFactory<PerguntaProperty>();
	}

}
