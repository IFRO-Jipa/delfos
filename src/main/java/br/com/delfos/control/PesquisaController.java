package br.com.delfos.control;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;


@Controller
public class PesquisaController {

    @FXML
    private Hyperlink linkAdicionaQuestionario;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private ListView<?> listQuestionario;

    @FXML
    private DatePicker datePesquisa;

    @FXML
    private Button btnSalvar;

    @FXML
    private ListView<?> listViewEspecialista;

    @FXML
    private TextField txtLimite;

    @FXML
    private Button pesquisaCodigoDaPesquisa;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnNovo;

    @FXML
    private Hyperlink linkAdicionaEspecialista;

    @FXML
    private ListView<?> listViewPesquisadores;

    @FXML
    private Hyperlink linkAdicionaPesquisador;
    
    @FXML
	private AnchorPane rootPane;

    @FXML
    void handleLinkAdicionaQuestionario(ActionEvent event) {
    	
    }

    @FXML
    void handleLinkAdicionaEspecialista(ActionEvent event) {

    }
    
    @FXML
    void handleLinkAdicionaPesquisador(ActionEvent event) {

    }

 
    @FXML
    void handleButtonSalvar(ActionEvent event) {
    	
    	

    }

    

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		
		this.datePesquisa.setValue(LocalDate.now());

	}

	@SuppressWarnings("unused")
	private Pesquisa montaRegistro() {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();
		return p;
	}
	
	private Callback<DatePicker, DateCell> factoryDeVencimento = param -> new DateCell() {
		@Override
		public void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);

			if (item.isBefore(PesquisaController.this.datePesquisa.getValue().plusDays(1))) {
				this.setDisable(true);
				this.setStyle("-fx-background-color: #ffc0cb;");
			}

			long p = ChronoUnit.DAYS.between(PesquisaController.this.datePesquisa.getValue(), item);
			this.setTooltip(new Tooltip(String.format("Sua pesquisa durar� %d dia(s).", p)));
		};
	};

	@FXML
	private void pesquisa() {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
		this.datePesquisa.setValue(LocalDate.now());
	}
	
}
