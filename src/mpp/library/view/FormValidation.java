package mpp.library.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import mpp.library.util.LibraryConstant;

public class FormValidation {

	public static boolean isEmpty(TextField textField) {
		if (textField.getText() == null || textField.getText().isEmpty()) {
			textField.getStyleClass().add(LibraryConstant.STYLE_ERROR);
			return true;
		}

		textField.getStyleClass().remove(LibraryConstant.STYLE_ERROR);
		return false;
	}

	public static void addLengthLimiter(TextField textField, int maxLength) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov,
					final String oldValue, final String newValue) {
				if (textField.getText().length() > maxLength) {
					String s = textField.getText().substring(0, maxLength);
					textField.setText(s);
				}
			}
		});
	}
}
