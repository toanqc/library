package mpp.library.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import mpp.library.util.LibraryConstant;

public class ScreenFramework extends Application {

	@Override
	public void start(Stage primaryStage) {

		ScreenController mainContainer = new ScreenController();
		mainContainer.loadScreen(Screen.HOME, Screen.HOME.getValue());
		mainContainer.loadScreen(Screen.MEMBER, Screen.MEMBER.getValue());
		mainContainer.loadScreen(Screen.MEMBER_LIST,
				Screen.MEMBER_LIST.getValue());

		mainContainer.setScreen(Screen.HOME);
		mainContainer.setPrimaryStage(primaryStage);

		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		mainContainer.getChildren();

		BackgroundImage myBI = new BackgroundImage(new Image(getClass()
				.getResourceAsStream(LibraryConstant.BACKGROUND_IMAGE)),
				BackgroundRepeat.ROUND, BackgroundRepeat.ROUND,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		// then you set to your node
		mainContainer.setBackground(new Background(myBI));

		Scene scene = new Scene(root);
		primaryStage.setTitle(LibraryConstant.LIBRAR_TITLE);
		primaryStage.getIcons().add(
				new Image(getClass().getResourceAsStream(
						LibraryConstant.APP_ICON)));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
