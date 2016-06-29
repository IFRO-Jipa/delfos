package br.com.delfos.util.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@Controller
public class Main extends Application {

	@Autowired
	private PesquisaDAO pesquisa;

	@Autowired
	private PessoaDAO pessoa;

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane pane = new AnchorPane();
		ListView<Questionario> listQuestionarios = new ListView<>();
		List<Pesquisa> pesquisas = pesquisa.findByEspecialista(pessoa.findOne(1L));

		listQuestionarios.getItems().addAll(pesquisas.get(0).getQuestionarios());
		pane.getChildren().add(listQuestionarios);

		primaryStage.setScene(new Scene(pane));
		primaryStage.setTitle("Titulo");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
