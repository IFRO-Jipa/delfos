package br.com.delfos.control;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.text.TableView.TableCell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.basic.TipoLogradouroDAO;
import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.TipoPergunta;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;
import br.com.delfos.view.table.factory.ComboBoxCellFactory;
import br.com.delfos.view.table.property.PerguntaProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
	private TableView<PerguntaProperty> tbPerguntas;

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
	private Label lblDuracao;

	@FXML
	private TableColumn<PerguntaProperty, String> nome;

	@FXML
	private TableColumn<PerguntaProperty, TipoPergunta> tipoPergunta;

	private final ObservableList<TipoPergunta> data = FXCollections.observableArrayList(TipoPergunta.getAll());

	private final ObservableList<PerguntaProperty> dadosTabela = FXCollections.observableArrayList(
	        new PerguntaProperty("Qual é o seu nome?", TipoPergunta.INTERVALO),
	        new PerguntaProperty("Qual é o seu grau de conhecimento em Java?", TipoPergunta.MULTIPLA_ESCOLHA),
	        new PerguntaProperty("Deixe seu comentário sobre a pesquisa?", TipoPergunta.PARAGRAFO));

	private Callback<DatePicker, DateCell> factoryDeVencimento = param -> new DateCell() {
		@Override
		public void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);

			if (item.isBefore(QuestionarioController.this.dtInicio.getValue().plusDays(1))) {
				this.setDisable(true);
				this.setStyle("-fx-background-color: #ffc0cb;");
			}

			long p = QuestionarioController.this.getTotalDeDias(item);
			this.setTooltip(new Tooltip(String.format("Seu questionário durará %d dia(s).", p)));
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
		// this.lblStatus.setText((q.isActive() ? "Ativo" : "Inativo"));
		// this.lblStatus.setStyle("-fx-text-fill: " + (q.isActive() ? "#33ff77"
		// : "#ff5c33"));
		return q;
	}

	@FXML
	private void pesquisa() {
		this.dtInicio.setEditable(false);
		this.dtInicio.disarm();
	}

	@FXML
	private void printa(ActionEvent event) {
		this.setDias(this.getTotalDeDias(this.dtVencimento.getValue()));
	}

	private void setDias(Long a) {
		if (a == 0) {
			this.lblDuracao.setVisible(false);
		} else {
			this.lblDuracao.setText(String.format("Duração: %d dia(s)", a));
			this.lblDuracao.setVisible(true);

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.dtInicio.setEditable(false);
		this.dtInicio.disarm();
		this.dtInicio.setValue(LocalDate.now());

		this.dtVencimento.setDayCellFactory(this.factoryDeVencimento);

		this.nome.setCellValueFactory(new PropertyValueFactory<PerguntaProperty, String>("nome"));
		this.tbPerguntas.setEditable(true);

		initColumnTipoPergunta();

		this.tbPerguntas.setItems(this.dadosTabela);

	}

	private void initColumnTipoPergunta() {

		this.tipoPergunta.setCellValueFactory(cellData -> cellData.getValue().getTipoPerguntaProperty());
		this.tipoPergunta.setCellFactory(getComboBoxFactory());
	}

	private Callback<TableColumn<PerguntaProperty, TipoPergunta>, javafx.scene.control.TableCell<PerguntaProperty, TipoPergunta>>
	        getComboBoxFactory() {

		return (TableColumn<PerguntaProperty, TipoPergunta> param) -> new ComboBoxCellFactory<PerguntaProperty, TipoPergunta>(
		        data, new StringConverter<TipoPergunta>() {

			        @Override
			        public TipoPergunta fromString(String string) {
				        return null;
			        }

			        @Override
			        public String toString(TipoPergunta object) {
				        return object.name();
			        }
		        });
	}

	private long getTotalDeDias(LocalDate item) {
		return ChronoUnit.DAYS.between(QuestionarioController.this.dtInicio.getValue(), item);
	}

}
