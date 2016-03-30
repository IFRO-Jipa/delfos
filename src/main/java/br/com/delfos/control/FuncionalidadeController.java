package br.com.delfos.control;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;

import br.com.delfos.util.AlertBuilder;
import br.com.delfos.util.ManipuladorDeComponentes;
import br.com.delfos.util.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Controller
public class FuncionalidadeController {
	@FXML
	private Button btnSalvar;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<?> tbRegistros;

	@FXML
	@NotNull
	private TextField txtNome;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	@NotNull
	private TextField txtChave;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnPesquisar;

	@FXML
	private Button btnNovo;

	@FXML
	private void handleBtnPesquisar(ActionEvent event) {

	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		if (!txtCodigo.getText().isEmpty()) {
			// TODO: Lógica para exclusão do registro
		} else {
			AlertBuilder.information("Selecione um registro para poder excluir");
		}
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(this)) {
			// Lógica para salvar o registro
			// faz nada
			System.out.println("fim");
		}
	}
}
