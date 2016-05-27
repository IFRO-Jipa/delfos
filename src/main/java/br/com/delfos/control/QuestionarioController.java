package br.com.delfos.control;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * @author 00685193209
 *
 */
@Controller
public class QuestionarioController implements Initializable {

	
	
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
	private TabPane tbPerguntas;

	@FXML
	private Label lblDuracao;

	private Callback<DatePicker, DateCell> factoryDeVencimento = param -> new DateCell() {
		@Override
		public void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);

			if (item.isBefore(QuestionarioController.this.dtInicio.getValue().plusDays(1))) {
				this.setDisable(true);
				this.setStyle("-fx-background-color: #ffc0cb;");
			}

			long p = QuestionarioController.this.getTotalDeDias(item);
			this.setTooltip(new Tooltip(String.format("Seu question�rio durar� %d dia(s).", p)));
		};
	};

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(this.rootPane);
		this.lblDuracao.setVisible(false);
		this.dtInicio.setValue(LocalDate.now());
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		this.dtInicio.setValue(LocalDate.now());
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(this.rootPane)) {
			Optional<Questionario> save = this.daoQuestionario.save(this.montaRegistro());

			save.ifPresent(questionario -> {
				this.txtCod.setText(String.valueOf(questionario.getId()));
				AlertBuilder.information("Salvo com sucesso");
			});
		}

	}

	private Questionario montaRegistro() {
		Questionario q = new Questionario();
		q.setId(this.txtCod.getText().isEmpty() ? null : Long.parseLong(this.txtCod.getText()));

		q.setNome(this.txtNome.getText());
		q.setDescricao(this.txtDesc.getText());
		q.setDataInicio(this.dtInicio.getValue());
		q.setVencimento(this.dtVencimento.getValue());
		q.setAutenticavel(this.cbAutenticavel.isSelected());
		return q;
	}

	@FXML
	private void pesquisa() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Consulta por c�digo");
		dialog.setHeaderText("PR�VIA - Consulta de Registros");
		dialog.setContentText("informe o c�digo da quest");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Optional<Questionario> optional = Optional
					.ofNullable(this.daoQuestionario.findOne(Long.parseLong(result.get())));
			if (optional.isPresent()) {
				this.posicionaRegistro(optional.get());
			} else {
				ManipuladorDeTelas.limpaCampos(this.rootPane);
				AlertBuilder.warning("Nenhum registro foi encontrado.");
			}
		} else {
			ManipuladorDeTelas.limpaCampos(this.rootPane);
			AlertBuilder.warning("Nenhum registro foi encontrado.");
		}
	}

	private void posicionaRegistro(Questionario quest) {
		this.txtCod.setText(String.valueOf(quest.getId()));
		this.txtNome.setText(quest.getNome());
		this.txtDesc.setText(quest.getDescricao());
		this.dtInicio.setValue(quest.getDataInicio());
		this.dtVencimento.setValue(quest.getVencimento());
		this.cbAutenticavel.setSelected(quest.isAutenticavel());

	}

	@FXML
	private void printa(ActionEvent event) {
		this.setDias(this.getTotalDeDias(this.dtVencimento.getValue()));
	}

	private void setDias(Long a) {
		if (a == 0) {
			this.lblDuracao.setVisible(false);
		} else {
			this.lblDuracao.setText(String.format("Dura��o: %d dia(s)", a));
			this.lblDuracao.setVisible(true);

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.dtInicio.setEditable(false);
		this.dtInicio.disarm();
		this.dtInicio.setValue(LocalDate.now());

		this.dtVencimento.setDayCellFactory(this.factoryDeVencimento);

		// ABRE TELA DE PERGUNTA DENTRO DA ABA CORRETA
		this.configTabPergunta();

	}

	private void configTabPergunta() {
		try {
			AnchorPane load = (AnchorPane) LeitorDeFXML.carrega("/fxml/PerguntaView.fxml");

			Tab tab = new Tab("Perguntas");
			tab.setContent(load);
			this.tbPerguntas.getTabs().add(tab);
			this.tbPerguntas.getSelectionModel().select(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private long getTotalDeDias(LocalDate item) {
		return ChronoUnit.DAYS.between(QuestionarioController.this.dtInicio.getValue(), item);
	}

}
