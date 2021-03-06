package br.com.delfos.app;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import br.com.delfos.control.pesquisa.QuestionarioController;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.LeitorDeFXML;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class QuestionarioApp extends Application {

	private static final String LOCATION = "fxml/pergunta/QuestionarioView.fxml";
	private static QuestionarioController controller;
	private static Stage primaryStage;
	private Optional<Questionario> initValue = Optional.empty();
	private LocalDate vencimentoPesquisa;

	public QuestionarioApp(LocalDate vencimentoPesquisa) {
		this.vencimentoPesquisa = vencimentoPesquisa;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = LeitorDeFXML.getLoader(LOCATION);
		AnchorPane pane = loader.load();

		configStage(primaryStage, pane);

		QuestionarioApp.primaryStage = primaryStage;
		QuestionarioApp.controller = loader.getController();

		controller.init(initValue, Optional.ofNullable(this.vencimentoPesquisa));

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
			}
		});
		primaryStage.showAndWait();

	}

	private void configStage(Stage primaryStage, AnchorPane pane) {
		primaryStage.setScene(new Scene(pane));
		primaryStage.setTitle("Criação de Questionários");
	}

	public static Stage getStage() {
		if (primaryStage != null) {
			return primaryStage;
		} else {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			return stage;
		}
	}

	public static QuestionarioController getController() {
		return controller;
	}

	public Optional<Questionario> showAndWait() throws IOException {
		this.start(getStage());

		return controller.getValue();
	}

	public static void close() {
		QuestionarioApp.primaryStage.hide();
	}

	public void init(Optional<Questionario> questionario) {
		this.initValue = questionario;
	}

}
