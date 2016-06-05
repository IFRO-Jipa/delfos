package br.com.delfos.control.pesquisa;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.MultiplaEscolha;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


@Controller
public class ConfigMultiplaEscolhaController {
	@FXML
	private Button btnSalvar;

	@FXML
	private TableColumn<MultiplaEscolha, String> columnItem;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TextField txtNome;

	@FXML
	private TableColumn<MultiplaEscolha, Double> columnValor;

	@FXML
	private TableView<MultiplaEscolha> tbAlternativas;

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

	@FXML
	private void handleButtonAddAlternativa(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {

	}

}
