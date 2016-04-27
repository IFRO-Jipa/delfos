
import java.util.Arrays;

import br.com.delfos.view.ListSelection;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestaSelector extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ListSelection<String> list = new ListSelection<>(Arrays.asList("A", "B", "C", "D"));

		list.show();
		list.show();
		list.show();

	}
}
