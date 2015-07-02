package mpp.library.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import mpp.library.controller.ValidationDialog;

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
}
