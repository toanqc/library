package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import mpp.library.view.ControlledScreen;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * Controller for home screen
 * 
 * @author Toan Quach
 *
 */
public class HomeController implements ControlledScreen {

	ScreenController myController;
	@FXML
	ImageView memberIcon;
	@FXML
	ImageView publicationIcon;
	@FXML
	ImageView checkoutIcon;
	@FXML
	ImageView overdueIcon;
	@FXML
	ImageView printIcon;

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}

	private void addHoverEffect(ImageView imageView) {
		// apply a shadow effect.
		imageView.setEffect(new DropShadow(20, Color.web("#7690ff")));
		imageView.setCursor(Cursor.HAND);
	}

	private void removeHoverEffect(ImageView imageView) {
		imageView.setEffect(null);
	}

	@FXML
	public void gotoMemberList() {
		myController.loadScreen(Screen.MEMBER_LIST,
				Screen.MEMBER_LIST.getValue());
		myController.setScreen(Screen.MEMBER_LIST);
	}

	@FXML
	public void gotoCheckout(MouseEvent event) {
		myController.loadScreen(Screen.CHECKOUT, Screen.CHECKOUT.getValue());
		myController.setScreen(Screen.CHECKOUT);
	}

	@FXML
	public void gotoPrintCheckoutRecord(MouseEvent event) {
		myController.loadScreen(Screen.PRINT_CHECKOUT_RECORD,
				Screen.PRINT_CHECKOUT_RECORD.getValue());
		myController.setScreen(Screen.PRINT_CHECKOUT_RECORD);
	}

	@FXML
	public void gotoPublication() {
		myController.loadScreen(Screen.PUBLICATION,
				Screen.PUBLICATION.getValue());
		myController.setScreen(Screen.PUBLICATION);
	}

	@FXML
	public void gotoOverdue() {
		myController.loadScreen(Screen.PUBLICATION_OVERDUE,
				Screen.PUBLICATION_OVERDUE.getValue());
		myController.setScreen(Screen.PUBLICATION_OVERDUE);
	}

	@Override
	public void repaint() {
		throw new UnsupportedOperationException(
				"Repaint method not need for home screen");
	}

	@FXML
	public void addMemberEffect() {
		addHoverEffect(memberIcon);
	}

	@FXML
	public void removeMemberEffect() {
		removeHoverEffect(memberIcon);
	}

	@FXML
	public void addCheckoutEffect() {
		addHoverEffect(checkoutIcon);
	}

	@FXML
	public void addPublicationEffect() {
		addHoverEffect(publicationIcon);
	}

	@FXML
	public void addOverdueEffect() {
		addHoverEffect(overdueIcon);
	}

	@FXML
	public void addPrintCheckoutEffect() {
		addHoverEffect(printIcon);
	}

	@FXML
	public void removePublicationEffect() {
		removeHoverEffect(publicationIcon);
	}

	@FXML
	public void removeCheckoutEffect() {
		removeHoverEffect(checkoutIcon);
	}

	@FXML
	public void removeOverdueEffect() {
		removeHoverEffect(overdueIcon);
	}

	@FXML
	public void removePrintCheckoutEffect() {
		removeHoverEffect(printIcon);
	}
}
