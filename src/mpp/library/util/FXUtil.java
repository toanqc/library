package mpp.library.util;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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

}
