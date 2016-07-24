package fxml;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmClose extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				int retVal = JOptionPane.showConfirmDialog(null, "Really close?", "Confirm", JOptionPane.YES_NO_OPTION);
				if ((retVal != JOptionPane.YES_OPTION)) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							primaryStage.show();
						}
					});
				}
			}
		});
		primaryStage.show();
	}
}