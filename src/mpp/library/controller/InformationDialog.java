package mpp.library.controller;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * 
 * @author Toan Quach
 *
 */
@SuppressWarnings("deprecation")
public class InformationDialog {

	public static void showInformation(String title, String message) {
		Dialogs.create().title(title).message(message)
				.actions(Dialog.ACTION_OK).showWarning();
	}
}
