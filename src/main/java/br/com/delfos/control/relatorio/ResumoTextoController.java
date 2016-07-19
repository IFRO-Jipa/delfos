package br.com.delfos.control.relatorio;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import br.com.delfos.model.pesquisa.resposta.RespostaParagrafo;
import br.com.delfos.model.pesquisa.resposta.RespostaTexto;
import br.com.delfos.util.LeitorDeFXML;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

@Controller
public class ResumoTextoController implements Initializable {

	@FXML
	private VBox boxTextos;

	@FXML
	private ScrollPane scrollTexto;

	private Optional<Pergunta<Texto>> perguntaTexto;

	@Autowired
	private RespostaDAO daoResposta;

	private Optional<Set<RespostaTexto>> respostasTexto;

	private Optional<Pergunta<Paragrafo>> perguntaParagrafo;

	private Optional<Set<RespostaParagrafo>> respostasParagrafo;

	public Optional<Set<RespostaTexto>> setTexto(Pergunta<Texto> value) {
		this.perguntaTexto = Optional.ofNullable(value);

		this.perguntaTexto.ifPresent(pergunta -> {
			this.respostasTexto = Optional.ofNullable(daoResposta.findByPerguntaTexto(pergunta));

			this.respostasTexto.ifPresent(respostas -> {
				respostas.forEach(resposta -> {
					try {
						FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/texto/TemplateResumoTexto.fxml");
						AnchorPane pane = loader.load();
						TemplateResumoTextoController controller = loader.getController();
						controller.set(resposta);
						boxTextos.getChildren().add(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			});
		});

		return respostasTexto;
	}

	public Optional<Set<RespostaParagrafo>> setParagrafo(Pergunta<Paragrafo> value) {
		this.perguntaParagrafo = Optional.ofNullable(value);

		// TODO corrigir
		this.perguntaParagrafo.ifPresent(pergunta -> {
			this.respostasParagrafo = Optional.ofNullable(daoResposta.findByPerguntaParagrafo(pergunta));

			this.respostasTexto.ifPresent(respostas -> {
				respostas.forEach(resposta -> {
					try {
						FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/texto/TemplateResumoTexto.fxml");
						AnchorPane pane = loader.load();
						TemplateResumoTextoController controller = loader.getController();
						controller.set(resposta);
						boxTextos.getChildren().add(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			});
		});

		return respostasParagrafo;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.scrollTexto.viewportBoundsProperty()
				.addListener((ChangeListener<Bounds>) (observable, oldValue, newValue) -> {
					this.boxTextos.setPrefWidth(newValue.getWidth());
				});
	}

}
