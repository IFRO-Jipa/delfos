package br.com.delfos;

import org.springframework.context.ApplicationContext;

import br.com.delfos.app.LoginApp;
import br.com.delfos.app.SplashScreenApp;
import br.com.delfos.util.ContextFactory;
import br.com.delfos.view.AlertBuilder;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize(primaryStage);
	}

	private void initialize(Stage primaryStage) {
		Service<ApplicationContext> service = new Service<ApplicationContext>() {

			@Override
			protected Task<ApplicationContext> createTask() {
				// TODO Auto-generated method stub
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

			} catch (Exception e) {
				AlertBuilder.error(e);
			}
		});
		service.setOnSucceeded(evt -> {
			try {
				new LoginApp().start(primaryStage);
			} catch (Exception e) {
				AlertBuilder.error(e);
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}
