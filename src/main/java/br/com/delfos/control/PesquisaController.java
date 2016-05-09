package br.com.delfos.control;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.Pesquisa;
import br.com.delfos.util.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Controller
public class PesquisaController {

	@FXML
	private Button removeEspecialistas;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TextField txtNome;

	@FXML
	private Button addPesquisador;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private TableView<?> tbEspecialistas;

	@FXML
	private ListView<?> listQuestionario;

	@FXML
	private DatePicker datePesquisa;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button removePesquisador;

	@FXML
	private Button removeQuestionario;

	@FXML
	private TextField txtLimite;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button addEspecialistas;

	@FXML
	private Button addQuestionario;

	@FXML
	private Button btnNovo;

	@FXML
	private TableView<?> tbPesquisadores;

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {

	}

	@SuppressWarnings("unused")
	private Pesquisa montaRegistro() {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();

		return p;

	}

}
