package br.com.delfos.control.pesquisa;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;

import br.com.delfos.control.dialog.EditDialog;
import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.view.AlertBuilder;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Controller
public class ConfigMultiplaEscolhaController implements EditDialog<Pergunta<MultiplaEscolha>>, Initializable {
	@FXML
	private Button btnSalvar;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TextField txtNome;

	@FXML
	private TableColumn<ObservableMap.Entry<String, Double>, String> columnItem;

	@FXML
	private TableColumn<ObservableMap.Entry<String, Double>, Double> columnValor;

	@FXML
	@NotNull
	private TableView<ObservableMap.Entry<String, Double>> tbAlternativas;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private TextField txtValor;

	@FXML
	private TextField txtItem;

	@FXML
	private Button txtAddAlternativa;

	@FXML
	private Text lblTipo;

	private boolean okCliked = false;

	private Stage dialogStage;

	private ObservableMap<String, Double> itens = FXCollections.observableHashMap();

	private Pergunta<MultiplaEscolha> value;

	public ConfigMultiplaEscolhaController() {
	}

	@FXML
	private void handleButtonAddAlternativa(ActionEvent event) {
		if (!txtItem.getText().isEmpty() && !txtValor.getText().isEmpty()) {
			String item = txtItem.getText();
			Double valor = Double.parseDouble(txtValor.getText());
			this.itens.put(item, valor);
			this.initTableView();
		} else {
			AlertBuilder.error("Preencha os campos corretamente para prosseguir.");
		}
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (!txtNome.getText().isEmpty()) {
			this.value.setNome(txtNome.getText());
			this.value.setDescricao(txtDescricao.getText());

			this.value.getAlternativa().clearEscolhas();
			this.value.getAlternativa().addAll(Optional.ofNullable(this.itens));
			
			this.okCliked = true;

			this.dialogStage.close();
		}
	}

	@Override
	public boolean isOkCliked() {
		return this.okCliked;
	}

	@Override
	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;
	}

	@Override
	public void setValue(Pergunta<MultiplaEscolha> value) {
		this.value = value;

		this.txtNome.setText(value.getNome());
		this.txtDescricao.setText(value.getDescricao());

		tbAlternativas.getItems().clear();

		MultiplaEscolha alternativa = value.getAlternativa();
		alternativa.get().ifPresent(values -> this.itens.putAll(values));

		populaTableView();
	}

	@Override
	public Pergunta<MultiplaEscolha> getValue() {
		this.value.getAlternativa().addAll(Optional.ofNullable(this.itens));
		return this.value;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initColumnItem();
		initColumnValor();

		initTableView();

		configTableView();
	}

	private void configTableView() {
		this.tbAlternativas.getColumns().clear();
		this.tbAlternativas.getColumns().add(columnItem);
		this.tbAlternativas.getColumns().add(columnValor);
		this.tbAlternativas.setEditable(true);
	}

	private void initTableView() {
		this.tbAlternativas.setItems(null);
		this.tbAlternativas.setContextMenu(getContextMenu());
		populaTableView();

	}

	private ContextMenu getContextMenu() {
		ContextMenu context = new ContextMenu();
		MenuItem menuRemover = new MenuItem("Remover");
		menuRemover.setOnAction(event -> {
			if (tbAlternativas.getSelectionModel().getSelectedIndex() >= 0) {
				tbAlternativas.getItems().remove(tbAlternativas.getSelectionModel().getSelectedIndex());
			}
		});

		MenuItem menuRemoverTodos = new MenuItem("Remover Todos");
		menuRemoverTodos.setOnAction(event -> {
			if (AlertBuilder.confirmation("Deseja realmente excluir todas as perguntas?")) {
				tbAlternativas.getItems().clear();
			}
		});

		context.getItems().add(menuRemover);
		context.getItems().add(menuRemoverTodos);
		return context;
	}

	private void populaTableView() {
		ObservableList<ObservableMap.Entry<String, Double>> itens = FXCollections
				.observableArrayList(this.itens.entrySet());
		this.tbAlternativas.setItems(itens);
	}

	private void initColumnValor() {
		this.columnValor = new TableColumn<>("Item");
		this.columnValor.setCellValueFactory(p -> new SimpleObjectProperty<Double>(p.getValue().getValue()));
		this.columnValor.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {

			@Override
			public String toString(Double object) {
				return String.valueOf(object);
			}

			@Override
			public Double fromString(String string) {
				try {
					return Double.parseDouble(string);
				} catch (NumberFormatException e) {
					return 0.0;
				}
			}
		}));

		this.columnValor.setOnEditCommit(event -> {
			System.out.printf("[antigo=%.2f, novo=%.2f]\n", event.getOldValue(), event.getNewValue());
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(event.getNewValue());
		});
	}

	private void initColumnItem() {
		this.columnItem = new TableColumn<>("Valor");
		this.columnItem.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
		this.columnItem.setCellFactory(TextFieldTableCell.forTableColumn());
		this.columnItem.setOnEditCommit(event -> {
			System.out.printf("[antigo=%s, novo=%s]\n", event.getOldValue(), event.getNewValue());
			if (!this.itens.containsKey(event.getNewValue())) {
				this.itens.put(event.getNewValue(), this.itens.get(event.getOldValue()));
				this.itens.remove(event.getOldValue());
				populaTableView();
			}
		});
	}

}
