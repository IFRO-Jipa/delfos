package br.com.delfos;

import java.io.IOException;

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
		try {
			loadProperties(args);
		} catch (JDOMException e1) {
			AlertAdapter.error("Manipulação incorreta",
					"A manipulação das configurações de inicialização não funcionaram adequadamente.");
		} catch (IOException e1) {
			AlertAdapter.error("File not found (META-INF/persistence.xml)", e1);
		} catch (Exception e1) {
			AlertAdapter.unknownError(e1);
		}
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

					// apenas para carregar o cache do hibernate
					@Override
					protected ApplicationContext call() throws Exception {
						ContextFactory.getContext();
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

	private static void loadProperties(String[] args) throws JDOMException, IOException, Exception {
		if (args.length > 0) {
			PersistenceModifyLoader persistence = new PersistenceModifyLoader();
			System.out.println("têm parâmetro");
			String user = "", pass = "", url = "";
			for (String arg : args) {
				final String[] arguments = arg.split("=");
				if (arguments[0].contains("url")) {
					System.out.println("Url : " + arguments[1]);
					url = arguments[1];
				}
				if (arguments[0].contains("password")) {
					System.out.println("Password : " + arguments[1]);
					pass = arguments[1];
				}
				if (arguments[0].contains("user")) {
					System.out.println("User : " + arguments[1]);
					user = arguments[1];
				}
			}
			persistence.setProperties(url, user, pass);
		} else {
			System.out.println("Default configs.");
			PersistenceModifyLoader persistence = new PersistenceModifyLoader();
			persistence.setProperties("jdbc:mysql://localhost:3306/delfos", "root", "root");
		}
	}
}
