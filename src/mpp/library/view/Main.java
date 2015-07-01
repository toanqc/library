package mpp.library.view;

import javafx.application.Application;
import javafx.stage.Stage;
import mpp.library.controller.FunctionType;

public class Main extends Application  {

	@Override
	public void start(Stage stage) throws Exception {
		new MemberListStage();
		//new MemberStage();
	}

	public static void main(String[] args) {
		launch(Main.class, args);
	}

	public static void showMemberStage(FunctionType functionType) {
		new MemberStage(functionType, 0);
	}

	public static void showMemberStage(FunctionType functionType, int memberId) {
		new MemberStage(functionType, memberId);
	}
}
