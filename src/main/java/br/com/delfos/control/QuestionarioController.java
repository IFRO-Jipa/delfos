package br.com.delfos.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.QuestionarioDAO;
import br.com.delfos.model.Identificator;
import br.com.delfos.model.Questionario;
import br.com.delfos.util.ManipuladorDeComponentes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Controller
public class QuestionarioController {

	@Autowired
	private QuestionarioDAO daoQuestionario;

	@FXML
	private Button btnSalvar;

	@FXML
	private TableView<?> tbPerguntas;

	@FXML
	private AnchorPane anchorPaneEndereco;

	@FXML
	private Tab tbEndereco;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCod;

	@FXML
	private TextArea txtDesc;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		txtCod.setText("");
		txtDesc.setText("");
		txtNome.setText("");
		tbPerguntas.getItems().clear();
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(rootPane)) {
			daoQuestionario.save(montaRegistro());
		}
	}

	private Questionario montaRegistro() {
		Questionario q = new Questionario();
		q.setId(txtCod.getText().isEmpty() ? null : Long.parseLong(txtCod.getText()));
		q.setNome(txtNome.getText());
		q.setDescricao(txtDesc.getText());
		return q;
	}

}
