package mpp.library.util;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import mpp.library.controller.ValidationDialog;

/**
 * All utilites regarding FX resides here.
 * 
 * @author Anil
 *
 */
public class FXUtil {

	public void clearTextFields(Pane pane) {
		ObservableList<Node> nodes = pane.getChildren();
		for (Node node : nodes) {
			if (node instanceof TextField) {
				TextField textField = (TextField) node;
				textField.clear();
			}
		}
	}

	public void createDialogAndRequestFocus(String message, TextField textField) {
		ValidationDialog.showWarning(message);
		textField.requestFocus();
	}

	/**
	 * Creates fadeout effect on node.
	 * 
	 * @param node
	 *            {@link Node} to be fadeout
	 * @param time
	 *            time in milli seconds
	 * @return {@link FadeTransition}
	 */
	public FadeTransition createFadeOutEffect(Node node, int time) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(time));
		fadeTransition.setNode(node);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		return fadeTransition;
	}

	public void showErrorMessage(Label messageBox, String message) {
		messageBox.setText(message);
		messageBox.getStyleClass().remove(LibraryConstant.SUCCESS_CLASS);
		if (!messageBox.getStyleClass().contains(LibraryConstant.ERROR_CLASS)) {
			messageBox.getStyleClass().add(LibraryConstant.ERROR_CLASS);
		}
		messageBox.setVisible(true);
	}

	public void showSuccessMessage(Label messageBox, String message) {
		messageBox.setText(message);
		messageBox.getStyleClass().remove(LibraryConstant.ERROR_CLASS);
		if (!messageBox.getStyleClass().contains(LibraryConstant.SUCCESS_CLASS)) {
			messageBox.getStyleClass().add(LibraryConstant.SUCCESS_CLASS);
		}
		messageBox.setVisible(true);
	}
	
	/**
	 * Creates fadein effect on node.
	 * 
	 * @param node
	 *            {@link Node} to be fadeout
	 * @param time
	 *            time in milli seconds
	 * @return {@link FadeTransition}
	 */
	public FadeTransition createFadeInEffect(Node node, int time) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(time));
		fadeTransition.setNode(node);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		return fadeTransition;
	}

}
