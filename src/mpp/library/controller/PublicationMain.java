package mpp.library.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mpp.library.util.LibraryConstant;

public class PublicationMain extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/mpp/library/view/member/memberList.fxml"));
		stage.setTitle("LM");
		stage.setScene(new Scene(root, LibraryConstant.WINDOW_HEIGHT, LibraryConstant.WINDOW_WIDTH));
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
