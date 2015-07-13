package mpp.library.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Provide some static method to validate the screen
 * 
 * @author Toan Quach
 *
 */
public class FormValidation {

	public static boolean isEmpty(TextField textField) {
		return isEmpty(textField.getText());
	}

	public static boolean isEmpty(TextArea textArea) {
		return isEmpty(textArea.getText());
	}

	private static boolean isEmpty(String text) {
		if (text == null || text.trim().isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean isNumber(TextField textField) {
		if (textField.getText() != null && textField.getText().trim().matches("^[0-9]+")) {
			return true;
		}

		return false;
	}

	public static boolean isNumberAndExactLength(TextField textField, int length) {
		if (textField.getText() != null && textField.getText().trim().matches("^[0-9]{" + length + "}$")) {
			return true;
		}

		return false;
	}

	public static boolean isValidISBN(String isbn) {
		if (isbn.matches(
				"^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")) {
			return true;
		}

		return false;
	}

	public static boolean isCharacter(TextField textField, int length) {
		if (textField.getText().matches("^[A-Z]{" + length + "}$")) {
			return true;
		}

		return false;
	}

	public static void addLengthLimiter(TextField textField, int maxLength) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
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
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				if (newValue.intValue() > oldValue.intValue()) {
					char ch = textField.getText().charAt(oldValue.intValue());
					// Check if the new character is the number or other's
					if (!(ch >= '0' && ch <= '9')) {
						// if it's not number then just setText to previous one
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					}
				}
			}
		});
	}

	public static boolean isEnteredNumberGreaterThan(TextField textField, int number) {
		if (textField.getText().trim().matches("^[0-9]$")
				&& Integer.valueOf(textField.getText().trim()).intValue() > number) {
			return true;
		}
		return false;
	}

	public static boolean isCorrectPhone(TextField textField) {
		if (textField.getText().trim().matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")) {
			return true;
		}
		return false;

	}

	public static boolean isEnoughLength(TextArea textField, int length) {
		if (textField.getText().trim().length() >= length) {
			return true;
		}
		return false;

	}
}
