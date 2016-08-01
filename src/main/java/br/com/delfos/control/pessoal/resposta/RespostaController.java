package br.com.delfos.control.pessoal.resposta;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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

	@FXML
	private Text txtPercentual;

	@FXML
	private ProgressBar progressBar;

	private Questionario questionario;

	private ObservableList<RespostaControllerImpl<?, ?>> controllers;

	private List<Resposta<?>> respostas;

	public RespostaController() {
		this.respostas = new ArrayList<>();
	}

	@FXML
	private void handleBtnRegistrar(ActionEvent event) {
		try {
			if (isRespostasPreenchidas()) {
				if (AlertAdapter.confirmation("Enviar questionário?",
						"Você só poderá responder esse questionário uma única vez. Deseja enviar agora?")) {

					List<Resposta<?>> respostas = montaRespostas();
					if (!respostas.isEmpty()) {
						List<Resposta<?>> itensSalvos = daoResposta.save(respostas);
						if (itensSalvos != null && !itensSalvos.isEmpty()) {
							this.respostas = itensSalvos;
							AlertAdapter.information("Registrado com sucesso.",
									"Suas respostas foram enviadas e não poderão ser modificadas.");
							RespostaApp.close();
						}
					}

					this.btnRegistrar.setDisable(true);
					this.btnLimpar.setDisable(true);
				}
			}
		} catch (QuestionarioRespondidoException ex) {
			AlertAdapter.error("Falha ao enviar",
					"Não foi possível submeter o questionário pois o sistema avalia duplicidade nos dados "
							+ "(parece que você já respondeu esse questionário)\nSe o erro persistir, entre em contato com o Administrador.");
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
				AlertAdapter.requiredDataNotInformed("Responda todas as perguntas para enviar o questionário.");
				return false;
			}
		}

		return true;
	}

	@FXML
	private void handleButtonLimpar(ActionEvent event) {
		if (AlertAdapter.confirmation("Limpar todas as perguntas?",
				"Todo o trabalho será perdido. Deseja continuar?")) {
			controllers.forEach(RespostaControllerImpl::clearSelected);
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize(URL location, ResourceBundle resources) {
		configScrollPane();
		this.controllers = FXCollections.observableArrayList();

		controllers.addListener(new ListChangeListener<RespostaControllerImpl>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends RespostaControllerImpl> change) {
				if (change.next()) {
					ObservableList<RespostaControllerImpl<?, ?>> lista = (ObservableList<RespostaControllerImpl<?, ?>>) change.getList();
				
					int marcados = lista.stream().filter(controller -> controller.isSelected() || controller.isIgnored()).collect(Collectors.toList()).size();
					
					// TODO fazer ouvinte capturar as telas.
					
				}
			}

		});

		// TODO implementar a barra de progresso para as pesquisas criadas.
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
		optionalPerguntas.ifPresent(
				perguntas -> perguntas.stream().sorted(Comparator.comparing(Pergunta::getId)).forEach(pergunta -> {

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
