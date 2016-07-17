package br.com.delfos.control.relatorio;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.LeitorDeFXML;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

@Controller
public class ResumoKaController implements Initializable {

	@FXML
	private Accordion paneQuestionarios;

	@FXML
	private ScrollPane rootPane;

	private Optional<Pesquisa> pesquisa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.paneQuestionarios.getPanes().clear();
		configScroll();
	}

	private void configScroll() {
		rootPane.viewportBoundsProperty().addListener((ChangeListener<Bounds>) (observable, oldValue, newValue) -> {
			paneQuestionarios.setPrefWidth(newValue.getWidth());
		});
	}

	public void set(Pesquisa pesquisa) {
		this.pesquisa = Optional.ofNullable(pesquisa);

		criaPaineisQuestionario(pesquisa.getQuestionarios());
	}

	private void criaPaineisQuestionario(Set<Questionario> questionarios) {
		questionarios.forEach(questionario -> {
			try {
				FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/ka/TemplateResumoKa.fxml");
				AnchorPane panel = loader.load();
				AnchorPane.setTopAnchor(panel, 0.0);
				AnchorPane.setBottomAnchor(panel, 0.0);
				AnchorPane.setLeftAnchor(panel, 0.0);
				AnchorPane.setRightAnchor(panel, 0.0);
				TemplateResumoKaController controller = loader.getController();

				TitledPane titledPane = new TitledPane();
				titledPane.setText(questionario.getNome());
				titledPane.setContent(panel);
				titledPane.setAlignment(Pos.TOP_LEFT);
				titledPane.setPrefHeight(panel.getHeight());
				paneQuestionarios.getPanes().add(titledPane);
			} catch (IOException e) {

			}
		});
	}

}
