package br.com.delfos.control.pesquisa;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

@Controller
public class RespostaController {

	@FXML
	private Text txtAutor;

	@FXML
	private Button btnEnviar;

	@FXML
	private VBox panePerguntas;

	@FXML
	private Text txtDataInicio;

	@FXML
	private Text txtDataFinalizada;

	@FXML
	private Text txtNomePesquisa;

}
