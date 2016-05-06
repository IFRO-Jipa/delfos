package br.com.delfos.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.QuestionarioDAO;
import br.com.delfos.model.Questionario;
import br.com.delfos.util.ManipuladorDeComponentes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
	private DatePicker dtVencimento;

	@FXML
	private Label lblStatus;

	@FXML
	private Button btnSalvar;

	@FXML
	private DatePicker dtInicio;

	@FXML
	private CheckBox cbAutenticavel;

	@FXML
	private Button btnPesquisa;

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
		this.txtCod.setText("");
		this.txtDesc.setText("");
		this.txtNome.setText("");
		this.tbPerguntas.getItems().clear();
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(this.rootPane)) {
			this.daoQuestionario.save(this.montaRegistro());
		}
	}

	private Questionario montaRegistro() {
		Questionario q = new Questionario();
		q.setId(this.txtCod.getText().isEmpty() ? null : Long.parseLong(this.txtCod.getText()));
		q.setNome(this.txtNome.getText());
		q.setDescricao(this.txtDesc.getText());
		q.setVencimento(this.dtVencimento.getValue());
		q.setAutenticavel(this.cbAutenticavel.isSelected());
		if (this.lblStatus.getStyle().equals("-fx-background-color: #ff5c33")) {
			this.lblStatus.setStyle("-fx-background-color: #3f7");
		} else {
			this.lblStatus.setStyle("-fx-background-color: #ff5c33");
		}
		return q;
	}

}
