package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
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
	
	@FXML
	public void gotoCheckout(MouseEvent event) {
		myController.loadScreen(Screen.CHECKOUT, Screen.CHECKOUT.getValue());
		myController.setScreen(Screen.CHECKOUT);
		myController.setSize(Screen.CHECKOUT.getWidth(),
				Screen.CHECKOUT.getHeight());
	}
	
	@FXML
	public void gotoPrintCheckoutRecord(MouseEvent event) {
		myController.loadScreen(Screen.PRINT_CHECKOUT_RECORD, Screen.PRINT_CHECKOUT_RECORD.getValue());
		myController.setScreen(Screen.PRINT_CHECKOUT_RECORD);
		myController.setSize(Screen.PRINT_CHECKOUT_RECORD.getWidth(), Screen.PRINT_CHECKOUT_RECORD.getHeight());
	}
}
