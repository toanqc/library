package mpp.library.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application  {

	@Override
	public void start(Stage stage) throws Exception {
		//new MemberListStage();
		new MemberStage();
	}

	public static void main(String[] args) {
		launch(Main.class, args);
	} 
}
