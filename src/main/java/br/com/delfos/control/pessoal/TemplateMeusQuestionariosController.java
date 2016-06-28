package br.com.delfos.control.pessoal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Controller;

import br.com.delfos.app.PrincipalApp;
import br.com.delfos.control.pessoal.resposta.RespostaController;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

@Controller
public class TemplateMeusQuestionariosController implements Initializable {

	@FXML
	private Text txtNomePesquisa;

	@FXML
	private Text txtResponsaveis;

	@FXML
	private Text txtVencimento;

	@FXML
	private TextField txtFiltro;

	@FXML
	private ListView<Questionario> listViewQuestionarios;

	public void set(Pesquisa pesquisa) {
		this.txtNomePesquisa.setText(pesquisa.getNome());
		this.txtResponsaveis.setText(criaStringComVirgulaEPonto(pesquisa.getPesquisadores()));
		this.txtVencimento.setText("04/94/2222");

		this.listViewQuestionarios.getItems().setAll(pesquisa.getQuestionarios());

	}

	private String criaStringComVirgulaEPonto(Set<Pessoa> pesquisadores) {
		List<String> nomes = new ArrayList<>();
		pesquisadores.forEach(pessoa -> nomes.add(pessoa.getNome()));
		return nomes.toString().replace("[", "").replace("]", "");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.listViewQuestionarios.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2)
				abreTelaResposta(listViewQuestionarios.getSelectionModel().getSelectedItem());
		});
	}

	private void abreTelaResposta(Questionario selectedItem) {
		try {
			FXMLLoader loader = LeitorDeFXML.getLoader("/fxml/pergunta/resposta/RespostaView.fxml");
			AnchorPane pane = loader.load();
			RespostaController controller = loader.getController();
			controller.set(Optional.ofNullable(selectedItem));

			PrincipalApp.openWindow(pane, "Responder: " + selectedItem.getNome());
		} catch (IOException e) {
			AlertBuilder.error(e);
		}
	}

}
