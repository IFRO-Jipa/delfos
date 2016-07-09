package br.com.delfos.app;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import br.com.delfos.control.pessoal.resposta.RespostaController;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RespostaApp extends Application {

	private Optional<Questionario> questionario;

	private RespostaController controller;

	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws IOException {
		RespostaApp.primaryStage = primaryStage;

		FXMLLoader loader = LeitorDeFXML.getLoader("fxml/resposta/RespostaView.fxml");
		AnchorPane pane = loader.load();
		this.controller = loader.getController();
		controller.set(questionario);

		primaryStage.setTitle("Responder: " + questionario.get().getNome());
		primaryStage.setScene(new Scene(pane));
		primaryStage.showAndWait();
	}

	public void setQuestionario(Optional<Questionario> questionario) {
		this.questionario = questionario;
	}

	public Optional<Questionario> getQuestionario() {
		return questionario;
	}

	public List<Resposta<?>> showAndWait() throws IOException {
		try {
			this.start(getStage());

			return controller.getRespostas();
		} catch (IllegalStateException e) {
			AlertBuilder.error(e);
			return null;
		}
	}

	public static Stage getStage() {
		if (primaryStage != null) {
			return primaryStage;
		} else {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initStyle(StageStyle.UTILITY);
			return stage;
		}
	}

	public static void close() {
		RespostaApp.primaryStage.close();
	}
}
