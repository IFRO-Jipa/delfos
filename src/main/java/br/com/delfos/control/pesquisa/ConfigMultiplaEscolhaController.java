package br.com.delfos.control.pesquisa;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;

import br.com.delfos.control.dialog.EditDialog;
import br.com.delfos.model.pesquisa.MultiplaEscolha;
import br.com.delfos.model.pesquisa.Pergunta;
import br.com.delfos.util.view.FXValidator;
import br.com.delfos.view.AlertBuilder;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Controller
public class ConfigMultiplaEscolhaController implements EditDialog<Pergunta<MultiplaEscolha>>, Initializable {
	@FXML
	private Button btnSalvar;

	@FXML
	private TableColumn<ObservableMap.Entry<String, String>, String> columnItem;

	@FXML
	private AnchorPane rootPane;

	@FXML
	@NotNull
	private TextField txtNome;

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

	private ObservableMap<String, Double> itens = FXCollections.observableMap(new HashMap<>());
	private Pergunta<MultiplaEscolha> value;

	public ConfigMultiplaEscolhaController() {
	}

	@FXML
	private void handleButtonAddAlternativa(ActionEvent event) {
		if (!txtItem.getText().isEmpty() && !txtValor.getText().isEmpty()) {
			String item = txtItem.getText();
			Double valor = Double.parseDouble(txtValor.getText());
			this.itens.put(item, valor);

			this.itens.forEach((chave, valorChave) -> System.out.printf("%s:%s", chave, String.valueOf(valorChave)));
		} else {
			AlertBuilder.error("Preencha os campos corretamente para prosseguir.");
		}
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (FXValidator.validate(this)) {
			this.value.setNome(txtNome.getText());
			this.value.setDescricao(txtDescricao.getText());

			MultiplaEscolha multiplaEscolha = new MultiplaEscolha();
			multiplaEscolha.addAll(Optional.ofNullable(itens));
			this.value.setAlternativa(multiplaEscolha);
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

		value.getAlternativa();
	}

	@Override
	public Pergunta<MultiplaEscolha> getValue() {
		return this.value;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initColumnItem();
		initColumnValor();
		this.tbAlternativas.setItems(FXCollections.observableArrayList(this.itens.entrySet()));
	}

	private void initColumnValor() {
		this.columnValor = new TableColumn<>("Value");
		this.columnValor.setCellValueFactory(valor -> {
			return new SimpleObjectProperty<Double>(valor.getValue().getValue());
		});
	}

	private void initColumnItem() {
		this.columnItem = new TableColumn<>("Key");
		this.columnItem.setCellValueFactory(item -> {
			return new SimpleStringProperty(item.getValue().getKey());
		});
	}

}
