package fxml.tab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TabTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		TabPane tabpane = new TabPane();
		Tab tab1 = new Tab("Nome 1");
		tab1.setGraphic(buildImage(TabTest.class.getClassLoader().getResource("imgs/apply.png").toExternalForm()));
		tabpane.getTabs().add(tab1);
		pane.setCenter(tabpane);

		stage.setTitle("Teste");
		stage.setScene(new Scene(pane));

		stage.setMaximized(true);
		stage.show();
	}

	private static ImageView buildImage(String imgPatch) {
		Image i = new Image(imgPatch);
		ImageView imageView = new ImageView();
		// You can set width and height
		imageView.setFitHeight(16);
		imageView.setFitWidth(16);
		imageView.setImage(i);
		return imageView;
	}

	public static void main(String[] args) {
		String x = "aaaaaaaaaaaa.fxml";
		System.out.println(x.substring(x.indexOf(".fxml")));
	}

}
