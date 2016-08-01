package br.com.delfos;

import java.io.IOException;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.springframework.context.ApplicationContext;

import br.com.delfos.app.LoginApp;
import br.com.delfos.app.SplashScreenApp;
import br.com.delfos.util.ContextFactory;
import br.com.delfos.view.AlertAdapter;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class Inicializador extends Application {

	private static String[] args;

	@Override
	public void start(Stage primaryStage) throws Exception {
		loadFontAwesome();
		initialize(primaryStage);
	}

	private void loadFontAwesome() {
		GlyphFont font = new FontAwesome(
				Inicializador.class.getClassLoader().getResourceAsStream("fonts/fontawesome.ttf"));
		GlyphFontRegistry.register(font);
	}

	private void initialize(Stage primaryStage) {
		Service<ApplicationContext> service = new Service<ApplicationContext>() {

			@Override
			protected Task<ApplicationContext> createTask() {

				return new Task<ApplicationContext>() {
					@Override
					protected ApplicationContext call() throws Exception {
						// TODO Auto-generated method stub
						System.out.println("Antes do context");
						try {
							ContextFactory.getContext();
						} catch (Throwable e) {
							AlertAdapter.error("Falha na inicialização.\nConsulte os detalhes técnicos.",
									e.getMessage());
						}
						System.out.println("Depois do context");
						return null;
					}
				};
			}
		};

		service.start();
		service.setOnRunning(evt -> {
			try {
				new SplashScreenApp().start();

			} catch (IOException e) {
				AlertAdapter.erroLoadFXML(e);
			}
		});
		service.setOnSucceeded(evt -> {
			try {
				new LoginApp().start(primaryStage);
			} catch (IOException e) {
				AlertAdapter.erroLoadFXML(e);
			}
		});

	}

	public static void main(String[] args) throws Exception {
		Inicializador.args = args;
		launch(args);
	}

}
