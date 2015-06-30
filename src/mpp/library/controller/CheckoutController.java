package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class CheckoutController {

	@FXML protected void gotoMainScreen(MouseEvent event) {
		System.out.println("Goto MainScreen");
	}
	
	@FXML protected void proceedCheckout(MouseEvent event) {
		System.out.println("proceed checkout");
	}
	
	@FXML protected void handleCancel(MouseEvent event) {
		System.out.println("handle Cancel");
	}
	
	@FXML protected void printCheckoutRecord(MouseEvent event) {
		System.out.println("Print Check out Record");
	}
	
	@FXML protected void search(MouseEvent event) {
		System.out.println("Search Member ID");
	}
	
	@FXML protected void checkoutBook(MouseEvent event) {
		System.out.println("Rearrange Book UI");
	}
	
	@FXML protected void checkoutPeriodical(MouseEvent event) {
		System.out.println("Rearrange Periodical UI");
	}
}
