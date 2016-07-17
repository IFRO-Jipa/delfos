package br.com.delfos.control.pessoal.resposta;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.RespostaApp;
import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.except.pesquisa.resposta.QuestionarioRespondidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.view.AlertAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

@Controller
public class RespostaController implements Initializable {

	@Autowired
	private RespostaDAO daoResposta;

	@FXML
	private Label txtNomeQuestionario;

	@FXML
	private ScrollPane scrollPerguntas;

	@FXML
	private Label txtDescricao;

	@FXML
	private Button btnRegistrar;

	@FXML
	private Button btnLimpar;

	@FXML
	private Accordion panelPerguntas;

	private Questionario questionario;

	private List<RespostaControllerImpl<?, ?>> controllers;

	private List<Resposta<?>> respostas;

	public RespostaController() {
		this.respostas = new ArrayList<>();
	}

	@FXML
	private void handleBtnRegistrar(ActionEvent event) {
		try {
			if (isRespostasPreenchidas()) {
				if (AlertAdapter.confirmation(
						"Você só poderá responder esse questionário uma única vez. Deseja enviar agora?")) {

					List<Resposta<?>> respostas = montaRespostas();
					if (!respostas.isEmpty()) {
						List<Resposta<?>> itensSalvos = daoResposta.save(respostas);
						if (itensSalvos != null && !itensSalvos.isEmpty()) {
							this.respostas = itensSalvos;
							AlertAdapter.information("Registrado com sucesso.");
							RespostaApp.close();
						}
					}

					this.btnRegistrar.setDisable(true);
					this.btnLimpar.setDisable(true);
				}
			}
		} catch (QuestionarioRespondidoException ex) {
			AlertAdapter.error("Não foi possível submeter o questionário pois já consta nos registros um envio com "
					+ "suas credenciais para esse questionário.\nSe o erro persistir, entre em contato com o Administrador.");
		}
	}

	private List<Resposta<?>> montaRespostas() {
		List<Resposta<?>> respostas = new ArrayList<>();
		Optional<Pessoa> expert = Optional.ofNullable(Autenticador.getDonoDaConta());

		controllers.forEach(controller -> {
			Resposta<?> resposta = controller.getResposta(this.questionario);
			resposta.setExpert(expert);
			respostas.add(resposta);
		});

		return respostas;
	}

	private boolean isRespostasPreenchidas() {
		for (RespostaControllerImpl<?, ?> controller : this.controllers) {
			if (!controller.isSelected()) {
				AlertAdapter.warning("Responda todas as perguntas antes de prosseguir.");
				return false;
			}
		}

		return true;
	}

	@FXML
	private void handleButtonLimpar(ActionEvent event) {
		if (AlertAdapter.confirmation("Todo o trabalho será perdido. Deseja continuar?")) {
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
		optionalQuestionario
				.orElseThrow(() -> new IllegalStateException("É necessário que o questionário seja informado."));
		optionalQuestionario.ifPresent(questionario -> {
			this.txtNomeQuestionario.setText(questionario.getNome());
			this.txtDescricao.setText(questionario.getDescricao());

			createPanels(questionario.getPerguntas());

			this.questionario = questionario;
		});
	}

	private void createPanels(Optional<Set<Pergunta<?>>> optionalPerguntas) {
		panelPerguntas.getPanes().clear();
		System.out.println("RespostaController.createPanels()");
		optionalPerguntas.ifPresent(
				perguntas -> perguntas.stream().sorted(Comparator.comparing(Pergunta::getId)).forEach(pergunta -> {

					System.out.printf("Pergunta: %4d - %10s [Alternativa: %20s]\n", pergunta.getId().intValue(),
							pergunta.getNome(), pergunta.getAlternativa());
					TitledPane titledPane = createTitledPane(pergunta);
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
			// FXMLLoader loader = LeitorDeFXML.getLoader(pergunta.getTipo().getLocationResposta());
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					RespostaController.class.getClassLoader().getResource(pergunta.getTipo().getLocationResposta()));
			AnchorPane pane = loader.load();
			RespostaControllerImpl<Alternativa, ?> controller = loader.getController();
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

	public List<Resposta<?>> getRespostas() {
		return respostas;
	}
}
