package mpp.library.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import mpp.library.util.LibraryConstant;

public class FormValidation {

	public static boolean isEmpty(TextField textField) {
		if (textField.getText() == null || textField.getText().trim().isEmpty()) {
			textField.getStyleClass().add(LibraryConstant.STYLE_ERROR);
			return true;
		}

		textField.getStyleClass().remove(LibraryConstant.STYLE_ERROR);
		return false;
	}

	public static boolean isNumber(TextField textField) {
		if (textField.getText() != null
				&& textField.getText().trim().matches("^[0-9]+")) {
			return true;
		}

		return false;
	}

	public static boolean isNumberAndExactLength(TextField textField, int length) {
		if (textField.getText() != null
				&& textField.getText().trim()
						.matches("^[0-9]{" + length + "}$")) {
			return true;
		}

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

	public static void addNumbericLimiter(TextField textField) {
		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {

				if (newValue.intValue() > oldValue.intValue()) {
					char ch = textField.getText().charAt(oldValue.intValue());
					// Check if the new character is the number or other's
					if (!(ch >= '0' && ch <= '9')) {
						// if it's not number then just setText to previous one
						textField.setText(textField.getText().substring(0,
								textField.getText().length() - 1));
					}
				}
			}
		});
	}

}
