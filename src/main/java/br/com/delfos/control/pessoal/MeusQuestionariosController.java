package br.com.delfos.control.pessoal;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.TableCellFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

@Controller
public class MeusQuestionariosController implements Initializable {

	@FXML
	private TextField txtFiltro;

	@FXML
	private ListView<Questionario> listViewQuestionarios;

	@Autowired
	private PesquisaDAO pesquisaDAO;

	@FXML
	private void handleFiltraQuestionario(ActionEvent event) {

	}

	private void populaListView() {

		List<Pesquisa> pesquisas = pesquisaDAO.findByEspecialista(Autenticador.getUsuarioAutenticado().getPessoa());
		Set<Questionario> questionarios = new HashSet<>();
		pesquisas.forEach(pesquisa -> questionarios.addAll(pesquisa.getQuestionarios()));

		this.listViewQuestionarios.getItems().addAll(questionarios);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configListView();
		populaListView();
	}

	private void configListView() {
		listViewQuestionarios
		        .setCellFactory(new TableCellFactory<Questionario>(null).getCellFactory(q -> configTexto(q)));
	}

	private String configTexto(Questionario q) {
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}

}
