package mpp.library.controller;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

@SuppressWarnings("deprecation")
public class ValidationDialog {

	public static void showWarning(String message) {
		Dialogs.create()
                .title("Validation Error!")
                .message(message)
                .actions(Dialog.ACTION_OK).showWarning();
	}
}
