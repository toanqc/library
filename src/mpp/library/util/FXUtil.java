package mpp.library.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
}
