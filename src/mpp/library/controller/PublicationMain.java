package mpp.library.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PublicationMain extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/mpp/library/view/addPublication.fxml"));
		stage.setTitle("LM");
		stage.setScene(new Scene(root, 500, 500));
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
