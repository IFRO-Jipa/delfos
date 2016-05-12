package br.com.delfos.control;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Controller
public class PesquisaController {

	@FXML
	private DatePicker datePesquisa;

	@FXML
	private Button btnSalvar;

	@FXML
	private TextField txtLimite;

	@FXML
	private TextField txtNome;

	@FXML
	private Button pesquisaCodigoDaPesquisa;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private TableView<?> tbEspecialistas;

	@FXML
	private ListView<?> listQuestionario;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	@FXML
	private TableView<?> tbPesquisadores;

	@Autowired
	private PesquisaDAO dao;

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		// ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(this))
			salva();
	}

	private void salva() {
		Pesquisa pesquisa = montaRegistro();

		Optional<Pesquisa> returned = dao.save(pesquisa);
		if (returned.isPresent()) {
			abreRegistro(returned.get());
			AlertBuilder.information("Salvo com sucesso");
		} else {
			AlertBuilder.warning("Não foi salvo... tente novamente");
		}

	}

	private void abreRegistro(Pesquisa pesquisa) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void handleButtonpesquisaCodigoDaPesquisa(ActionEvent event) {

	}

	@SuppressWarnings("unused")
	private Pesquisa montaRegistro() {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();
		return p;
	}

	@FXML
	private void pesquisa() {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
		this.datePesquisa.disabledProperty();
	}

}
