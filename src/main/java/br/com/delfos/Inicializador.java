package br.com.delfos;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.jdom2.JDOMException;
import org.springframework.context.ApplicationContext;

import br.com.delfos.app.LoginApp;
import br.com.delfos.app.SplashScreenApp;
import br.com.delfos.util.ContextFactory;
import br.com.delfos.util.PersistenceModifyLoader;
import br.com.delfos.view.AlertAdapter;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class Inicializador extends Application {

	private static String[] args;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Fazendo a fonte de ícones do ControlsFX ser carregada, deixando de lado o repositório CDN
		// padrão.
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
						ApplicationContext context = ContextFactory.getContext();
						int max = context.getBeanDefinitionCount();
						updateProgress(0, max);

						for (int k = 0; k < max; k++) {
							Thread.sleep(50);
							updateProgress(k + 1, max);
						}
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

		loadProperties(args);
		launch(args);
	}

	private static void loadProperties(String[] args) throws JDOMException, IOException, Exception {
		if (args != null) {
			PersistenceModifyLoader persistence = new PersistenceModifyLoader();
			String user = "", pass = "", url = "";
			for (String arg : args) {
				final String[] arguments = arg.split("=");
				if (arguments[0].contains("url"))
					url = arguments[1];
				if (arguments[0].contains("password")) {
					pass = arguments[1];
				}
				if (arguments[0].contains("user")) {
					user = arguments[1];
				}
			}
			persistence.setProperties(url, user, pass);
		} else
			throw new Exception("Propriedades de configuração não informada.");
	}
}
