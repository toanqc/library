package mpp.library.controller;

import javafx.fxml.FXML;
import mpp.library.view.ControlledScreen;
import mpp.library.view.ScreenController;
import mpp.library.view.Screen;

public class HomeController implements ControlledScreen {

	ScreenController myController;

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}

	@FXML
	public void openMemberListScreen() {
		myController.setScreen(Screen.MEMBER_LIST);
		myController.setSize(Screen.MEMBER_LIST.getWidth(),
				Screen.MEMBER_LIST.getHeight());
	}
}
