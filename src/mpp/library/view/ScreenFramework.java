package mpp.library.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenFramework extends Application {

	@Override
	public void start(Stage primaryStage) {

		ScreenController mainContainer = new ScreenController();
		mainContainer.loadScreen(Screen.HOME,
				Screen.HOME.getValue());
		mainContainer.loadScreen(Screen.MEMBER,
				Screen.MEMBER.getValue());
		mainContainer.loadScreen(Screen.MEMBER_LIST,
				Screen.MEMBER_LIST.getValue());

		mainContainer.setScreen(Screen.HOME);
		mainContainer.setPrimaryStage(primaryStage);

		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
