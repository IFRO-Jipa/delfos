package br.com.delfos.control;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

@Controller
public class PesquisaController {

	@FXML
	private ListView<?> listViewQuestionario;

	@FXML
	private ListView<?> listViewPesquisador;

	@FXML
	private Hyperlink linkAdicionaQuestionario;

	@FXML
	private TextField txtNome;

	@FXML
	private ListView<?> listViewPesquisadores;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private DatePicker datePesquisa;

	@FXML
	private Button btnSalvar;

	@FXML
	private ListView<?> listViewEspecialista;

	@FXML
	private TextField txtLimite;

	@FXML
	private Hyperlink linkAdicionaPesquisador;

	@FXML
	private Button pesquisaCodigoDaPesquisa;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	@FXML
	private Hyperlink linkAdicionaEspecialista;

	@FXML
	private AnchorPane rootPane;

	@Autowired
	private PesquisaDAO dao;
	

	private List<Pesquisa> pegaEspecialista() {
		return listViewEspecialista.getItems().isEmpty() ? null : listViewEspecialista.getItems();
	}
	
	@FXML
	private void handleLinkAdicionaEspecialista(ActionEvent event) {
		
		try {
			ListSelection<Pesquisa> seletor = new ListSelection<>("Selecione as Pesquisas",
			        filtraEspecialistaInexistentes());

			seletor.setCellFactory(p -> configuraTextoNaCelula());

			Optional<List<Funcionalidade>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewEspecialista.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	private ListCell<Pesquisa> configuraTextoNaCelula() {
		ListCell<Pesquisa> cell = new ListCell<Pesquisa>() {

			@Override
			protected void updateItem(final Pesquisa p, final boolean bln) {
				super.updateItem(p, bln);
				if (p != null) {
					setText(p.getNome());
				} else {
					setText(null);
				}
			}

		};
		return cell;
	}

	private List<Pesquisa> filtraEspecialistaInexistentes() {
		List<Pesquisa> result = new ArrayList<>();

		if (listViewEspecialista.getItems().isEmpty()) {
			result.addAll(pesquisa);
		} else {
			Pesquisa.forEach(pesquisa -> {
				if (!listViewEspecialista.getItems().contains(Pesquisa)) {
					result.add(pesquisa);
				}

			});
		}

		return result;
	}
	
	
	@FXML
	private void handleLinkAdicionaPesquisador(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		
		this.salvar(montaRegistro());
	}

	private void salvar(Pesquisa value) {
		if (ManipuladorDeComponentes.validaCampos(rootPane)) {
			Optional<Pesquisa> save = dao.save(value);
			save.ifPresent(pesquisa -> {
				txtCodigo.setText(String.valueOf(pesquisa.getId()));
				AlertBuilder.information("Salvo com sucesso");
			});

			if (!save.isPresent())
				AlertBuilder.information(
				        "Não foi salvo, algo de estranho aconteceu.\nTente novamente mais tarde");
		}
	}

	private Pesquisa montaRegistro() {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();

		//Continuar inicialização de variáveis
		
		return p;
	}
	

	@FXML
	private void handleLinkAdicionaQuestionario(ActionEvent event) {

	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		// TODO novo
		
		ManipuladorDeTelas.limpaCampos(rootPane);
		//Montar registro
	}


	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		// TODO excluir
		excluiRegistro();

	}

	private void excluiRegistro() {
		if (!txtCodigo.getText().isEmpty()) {
			if (AlertBuilder.confirmation("Deseja realmente excluir o registro?")) {
				dao.delete(Long.parseLong(txtCodigo.getText()));
				ManipuladorDeTelas.limpaCampos(rootPane);
				AlertBuilder.information("Excluído com sucesso");
			}
		} else
			return;
	}

	
	//private Pesquisa montaRegistro() {
		//
	//}

	private Callback<DatePicker, DateCell> factoryDeVencimento = param -> new DateCell() {
		@Override
		public void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);

			if (item.isBefore(PesquisaController.this.datePesquisa.getValue().plusDays(1))) {
				this.setDisable(true);
				this.setStyle("-fx-background-color: #ffc0cb;");
			}

			long p = ChronoUnit.DAYS.between(PesquisaController.this.datePesquisa.getValue(), item);
			this.setTooltip(new Tooltip(String.format("Sua pesquisa durará %d dia(s).", p)));
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
