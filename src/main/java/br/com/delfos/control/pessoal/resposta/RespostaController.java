package br.com.delfos.control.pessoal.resposta;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;
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

	@Autowired
	private RespostaDAO daoResposta;

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
		Pessoa expert = Autenticador.getDonoDaConta();

		controllers.forEach(controller -> {
			Resposta<?> resposta = controller.getResposta();
			resposta.setExpert(Optional.ofNullable(expert));
			resposta.setQuestionario(this.questionario);
			daoResposta.save(resposta);
		});
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
		this.controllers = new ArrayList<>();
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
		panelPerguntas.getPanes().clear();

		optionalPerguntas.ifPresent(perguntas -> perguntas.forEach(pergunta -> {
			TitledPane titledPane = createTitledPane(pergunta);
			// panelPerguntas.getPanes().clear();
			panelPerguntas.getPanes().add(titledPane);
		}));
	}

	private TitledPane createTitledPane(Pergunta<?> pergunta) {
		TitledPane titledPane = new TitledPane(pergunta.getNome(), createPane(pergunta));
		titledPane.setExpanded(true);
		titledPane.setCollapsible(true);
		return titledPane;
	}

	private AnchorPane createPane(Pergunta<?> pergunta) {
		try {
			FXMLLoader loader = LeitorDeFXML.getLoader(pergunta.getTipo().getLocationResposta());
			AnchorPane pane = loader.load();
			RespostaControllerImpl<Alternativa, ?> controller = loader.getController();
			// TODO: Passar a pergunta para o controlador.
			controller.set(Optional.ofNullable(pergunta));
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
