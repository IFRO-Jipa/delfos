package br.com.delfos.control.pessoal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.model.auditoria.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

@Controller
public class MeusQuestionariosController {

	@FXML
	private TextField txtFiltro;

	@FXML
	private ListView<?> listViewQuestionarios;

	private Usuario usuario;

	@Autowired
	private QuestionarioDAO questionario;

	@FXML
	private void handleFiltraQuestionario(ActionEvent event) {

	}

	public MeusQuestionariosController() {
		usuario = Autenticador.getUsuarioAutenticado();
	}

	private void populaListView() {

	}

}
