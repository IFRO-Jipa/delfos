package br.com.delfos.control.pessoal.resposta;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.TipoPergunta;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

@Controller
public class RespostaController implements Initializable {

	@FXML
	private Text txtNomeQuestionario;

	@FXML
	private ScrollPane scrollPerguntas;

	@FXML
	private Text txtDescricao;

	@FXML
	private Button btnRegistrar;

	@FXML
	private Button btnLimpar;

	@FXML
	private Accordion panelPerguntas;

	private Questionario questionario;

	private List<RespostaControllerImpl<?, ?>> controllers;

	@FXML
	private void handleBtnRegistrar(ActionEvent event) {
		// SALVAR NO BANCO DE DADOS
	}

	@FXML
	private void handleButtonLimpar(ActionEvent event) {
		if (AlertBuilder.confirmation("Todo o trabalho será perdido. Deseja continuar?")) {
			controllers.forEach(RespostaControllerImpl::clearSelected);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configScrollPane();
	}

	private void configScrollPane() {
		this.scrollPerguntas.viewportBoundsProperty().addListener((obs, old, newValue) -> {
			panelPerguntas.setPrefWidth(newValue.getWidth());
		});
	}

	public void set(Optional<Questionario> optionalQuestionario) {
		optionalQuestionario.ifPresent(questionario -> {
			this.txtNomeQuestionario.setText(questionario.getNome());
			this.txtDescricao.setText(questionario.getDescricao());

			createPanels(questionario.getPerguntas());

			this.questionario = questionario;
		});
	}

	private void createPanels(Optional<Set<Pergunta<?>>> optionalPerguntas) {
		optionalPerguntas.ifPresent(perguntas -> perguntas.forEach(pergunta -> {
			TitledPane titledPane = createTitledPane(pergunta);
			panelPerguntas.getPanes().clear();
			panelPerguntas.getPanes().add(titledPane);
		}));
	}

	private TitledPane createTitledPane(Pergunta<?> pergunta) {
		TitledPane titledPane = new TitledPane(pergunta.getNome(), createPane(pergunta.getTipo()));
		titledPane.setCollapsible(false);
		titledPane.setExpanded(true);
		return titledPane;
	}

	private AnchorPane createPane(TipoPergunta tipo) {
		try {
			FXMLLoader loader = LeitorDeFXML.getLoader(tipo.getLocationResposta());
			AnchorPane pane = loader.load();
			RespostaControllerImpl<?, ?> controller = loader.getController();
			// TODO: Passar a pergunta para o controlador.
			this.controllers.add(controller);
			return pane;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void clear() {
		this.txtNomeQuestionario.setText("Nome do questionário aqui");
		this.txtDescricao.setText("Descrição padrão");

		panelPerguntas.getPanes().clear();
	}

	public Optional<Questionario> get() {
		return Optional.ofNullable(this.questionario);
	}
}
