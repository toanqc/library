package mpp.library.view.checkout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
		primaryStage.setTitle("Checkout");
		primaryStage.setScene(new Scene(root, 500, 400));
		primaryStage.show();
		primaryStage.setResizable(false);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
