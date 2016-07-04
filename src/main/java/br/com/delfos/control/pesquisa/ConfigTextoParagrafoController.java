package br.com.delfos.control.pesquisa;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;

import br.com.delfos.control.dialog.EditDialog;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import br.com.delfos.util.view.FXValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Controller
public class ConfigTextoParagrafoController implements EditDialog<Pergunta<?>> {

	@FXML
	private Button btnSalvar;

	@FXML
	private AnchorPane rootPane;

	@FXML
	@NotNull
	private TextField txtNome;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private Text lblTipo;

	private Pergunta<?> value;

	private boolean okClicked = false;

	private Stage dialogStage;

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (FXValidator.validate(this)) {
			this.value.setDescricao(txtDescricao.getText());
			this.value.setNome(this.txtNome.getText());

			this.okClicked = true;

			this.dialogStage.close();
		}
	}

	@Override
	public boolean isOkCliked() {
		return this.okClicked;
	}

	@Override
	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;
	}

	@Override
	public void setValue(Pergunta<?> value) {
		if (value.getAlternativa() instanceof Texto || value.getAlternativa() instanceof Paragrafo) {
			this.value = value;

			this.txtDescricao.setText(value.getDescricao());
			this.txtNome.setText(value.getNome());
			this.lblTipo.setText((value.getAlternativa() instanceof Texto ? "Texto" : "Parágrafo"));
		} else {
			throw new IllegalArgumentException(
			        "Essa janela de diálogo aceita apenas perguntas do tipo Texto e Parágrafo.");
		}
	}

	@Override
	public Pergunta<?> getValue() {
		return this.value;
	}

}
