package br.com.delfos.control.pesquisa;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;

import br.com.delfos.control.dialog.EditDialog;
import br.com.delfos.converter.table.cell.ConverterComboBoxToCell;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.TipoPergunta;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.util.view.FXValidator;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.table.factory.ComboBoxCellFactory;
import br.com.delfos.view.table.factory.TextFieldCellFactory;
import br.com.delfos.view.table.property.PerguntaProperty;
import br.com.delfos.view.table.property.PerguntaPropertyUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

@Controller
public class PerguntaController implements Initializable {

	@FXML
	private TableView<PerguntaProperty<?>> tbPerguntas;

	@FXML
	private TableColumn<PerguntaProperty<?>, String> columnNome;

	@FXML
	private TableColumn<PerguntaProperty<?>, TipoPergunta> columnTipo;

	@FXML
	private TableColumn<PerguntaProperty<?>, PerguntaProperty<?>> columnAcao;

	private final ObservableList<TipoPergunta> tiposDePergunta = FXCollections
			.observableArrayList(TipoPergunta.getAll());

	private ObservableList<PerguntaProperty<?>> dadosTabela = FXCollections.observableArrayList();

	@FXML
	@NotNull
	private ComboBox<TipoPergunta> cbTipoPergunta;

	@FXML
	private Button btnAddPergunta;

	@FXML
	@NotNull
	private TextField txtNomePergunta;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTablePergunta();
		this.cbTipoPergunta.setItems(tiposDePergunta);
	}

	private void initTablePergunta() {

		initColumnNome();
		initColumnTipoPergunta();
		initColumnAcao();

		initTablePerguntas();

	}

	private void initTablePerguntas() {
		this.tbPerguntas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.tbPerguntas.setEditable(true);
		this.tbPerguntas.setItems(dadosTabela);

		this.tbPerguntas.setContextMenu(getContextMenu());
	}

	private ContextMenu getContextMenu() {
		ContextMenu context = new ContextMenu();
		MenuItem menuRemover = new MenuItem("Remover");
		menuRemover.setOnAction(event -> {
			if (tbPerguntas.getSelectionModel().getSelectedIndex() >= 0) {
				tbPerguntas.getItems().remove(tbPerguntas.getSelectionModel().getSelectedIndex());
			}
		});

		MenuItem menuRemoverTodos = new MenuItem("Remover Todos");
		menuRemoverTodos.setOnAction(event -> {
			if (AlertBuilder.confirmation("Deseja realmente excluir todas as perguntas?")) {
				tbPerguntas.getItems().clear();
			}
		});

		context.getItems().add(menuRemover);
		context.getItems().add(menuRemoverTodos);
		return context;
	}

	protected void handleButtonAction(PerguntaProperty<?> item) {
		Optional<PerguntaProperty<?>> optional = Optional.ofNullable(item);
		optional.ifPresent(property -> {
			if (property.getTipoPergunta() != null) {
				try {
					String location = property.getTipoPergunta().getLocation();

					FXMLLoader loader = LeitorDeFXML.getLoader(location);
					AnchorPane load = (AnchorPane) loader.load();

					// tipo de controladora para telas auxiliares.
					EditDialog<Pergunta<?>> controller = loader.getController();
					controller.setValue(converterProperty(property));

					Stage dialogStage = new Stage();
					dialogStage.setScene(new Scene(load));
					// dialogStage.initModality(Modality.APPLICATION_MODAL);
					dialogStage.initStyle(StageStyle.UTILITY);
					controller.setDialogStage(dialogStage);
					dialogStage.showAndWait();

					if (controller.isOkCliked()) {
						// aqui vai o código que atualiza a informação da
						// pergunta.
						converterPergunta(property, controller.getValue());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void converterPergunta(PerguntaProperty<?> property, Pergunta<?> value) {
		property.setNome(value.getNome());
		property.setDescricao(value.getDescricao());
		property.setAlternativa(value.getAlternativa());

	}

	private Pergunta<?> converterProperty(PerguntaProperty<?> property) {
		Pergunta<Alternativa> pergunta = new Pergunta<>();
		pergunta.setId(property.getId() == 0 ? null : property.getId());
		pergunta.setNome(property.getNome());
		pergunta.setDescricao(property.getDescricao());

		pergunta.setAlternativa(property.getAlternativa());
		return pergunta;
	}

	@FXML
	private void handleButtonAddPergunta(ActionEvent event) {
		if (FXValidator.validate(this)) {
			String nome = txtNomePergunta.getText();
			TipoPergunta tipoPergunta = cbTipoPergunta.getValue();
			PerguntaProperty<?> pergunta = new PerguntaProperty<>(nome, tipoPergunta);
			tbPerguntas.getItems().add(pergunta);
		}
	}

	public void setPerguntas(Optional<Set<Pergunta<?>>> perguntas) {
		this.dadosTabela.clear();

		perguntas.ifPresent(values -> values
				.forEach(pergunta -> this.dadosTabela.add(PerguntaPropertyUtil.fromPergunta(pergunta))));

	}

	public List<Pergunta<?>> getPerguntas() {
		ObservableList<Pergunta<?>> perguntas = FXCollections.observableArrayList();

		this.tbPerguntas.getItems().forEach(perguntaProperty -> {
			Pergunta<?> pergunta = PerguntaPropertyUtil.toValue(perguntaProperty);

			perguntas.add(pergunta);
		});

		return perguntas;

	}

	/*
	 * Código que inicializa as colunas da TableView, com entradas de TextField,
	 * ComboBox e Button em suas células. é normal não entender o código de
	 * primeira.
	 */

	private void initColumnNome() {
		this.columnNome.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
		this.columnNome.setCellFactory(getTextFieldFactory());
		this.columnNome.setPrefWidth(tbPerguntas.getWidth() * 0.5);
	}

	private void initColumnTipoPergunta() {
		this.columnTipo.setCellValueFactory(cellData -> cellData.getValue().getTipoPerguntaProperty());
		this.columnTipo.setCellFactory(getComboBoxFactory());
		this.columnTipo.setPrefWidth(tbPerguntas.getWidth() * 0.3);
	}

	private void initColumnAcao() {
		columnAcao.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		columnAcao.setResizable(false);
		columnAcao.setCellFactory(getButtonFactory());
	}

	/**
	 * Cria um combobox na célula
	 * 
	 * @return célula com combobox
	 */
	private Callback<TableColumn<PerguntaProperty<?>, TipoPergunta>, TableCell<PerguntaProperty<?>, TipoPergunta>> getComboBoxFactory() {

		return param -> new ComboBoxCellFactory<PerguntaProperty<?>, TipoPergunta>(tiposDePergunta,
				new ConverterComboBoxToCell<TipoPergunta>().setToString(obj -> obj.name()).convert());
	}

	/**
	 * Cria um textfield na célula
	 * 
	 * @return célula com textfield
	 */
	private Callback<TableColumn<PerguntaProperty<?>, String>, TableCell<PerguntaProperty<?>, String>> getTextFieldFactory() {
		return param -> new TextFieldCellFactory<PerguntaProperty<?>>();
	}

	/**
	 * Cria uma célula com botão
	 * 
	 * @return botão dentro da célula.
	 */
	private Callback<TableColumn<PerguntaProperty<?>, PerguntaProperty<?>>, TableCell<PerguntaProperty<?>, PerguntaProperty<?>>>

			getButtonFactory() {
		return param -> new TableCell<PerguntaProperty<?>, PerguntaProperty<?>>() {
			Button button = new Button("...");

			{
				button.setMinWidth(columnAcao.getWidth() - 10);
			}

			@Override
			protected void updateItem(PerguntaProperty<?> item, boolean empty) {
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

}
