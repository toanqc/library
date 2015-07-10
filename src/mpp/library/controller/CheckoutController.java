package mpp.library.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import mpp.library.model.Book;
import mpp.library.model.Periodical;
import mpp.library.model.service.impl.CheckoutServiceImpl;
import mpp.library.util.FXUtil;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * 
 * @author bpham4
 *
 */
public class CheckoutController implements ControlledScreen, Initializable {

	@FXML
	private GridPane mainGridPane;
	@FXML
	private GridPane bookGridPane;
	@FXML
	private GridPane periodicalGridPane;
	@FXML
	private RadioButton rdBook;
	@FXML
	private RadioButton rdPeriodical;

	@FXML
	private TextField txtMemberID;

	@FXML
	private TextField txtMemberID1;

	@FXML
	private TextField txtISBN;
	@FXML
	private TextField txtIssueNumber;
	@FXML
	private TextField txtTitle;
	@FXML
	private Label lblMessage;
	@FXML
	private Button btnCheckout;

	private CheckoutServiceImpl chkoutBookService = new CheckoutServiceImpl();

	public static final int ISBN_MAX_LENTH = 13;

	private ScreenController myController;

	@FXML
	protected void gotoMainScreen(MouseEvent event) {
		myController.setScreen(Screen.HOME);
		myController.setSize(Screen.HOME.getWidth(), Screen.HOME.getHeight());
	}

	@FXML
	protected void proceedCheckout(MouseEvent event) {
		if (rdBook.isSelected() && validateData()) {
			lblMessage.setVisible(false);
			lblMessage.setText("");
			String memberId = txtMemberID.getText().trim();
			String ISBN = txtISBN.getText().trim();
			try {
				chkoutBookService.checkout(memberId, new Book(ISBN));
				FXUtil.showSuccessMessage(lblMessage, "Check out successfully");
			} catch (Exception e) {
				FXUtil.showErrorMessage(lblMessage, e.getMessage());
			}

		} else if (rdPeriodical.isSelected() && validateData()) {
			lblMessage.setVisible(false);
			lblMessage.setText("");
			String memberId = txtMemberID1.getText().trim();
			String title = txtTitle.getText().trim();
			String issueNo = txtIssueNumber.getText().trim();
			try {
				chkoutBookService.checkout(memberId, new Periodical(title, issueNo));
				FXUtil.showSuccessMessage(lblMessage, "Check out successfully");
			} catch (Exception e) {
				FXUtil.showErrorMessage(lblMessage, e.getMessage());
			}
		}
	}

	@FXML
	protected void handleClear(MouseEvent event) {
		lblMessage.setVisible(false);
		clear();
	}

	@FXML
	protected void checkoutBook(MouseEvent event) {
		if (rdBook.isSelected()) {
			clear();
			rdPeriodical.setSelected(false);
			bookGridPane.setVisible(true);
			periodicalGridPane.setVisible(false);
		}

	}

	@FXML
	protected void checkoutPeriodical(MouseEvent event) {
		if (rdPeriodical.isSelected()) {
			clear();
			rdBook.setSelected(false);
			bookGridPane.setVisible(false);
			periodicalGridPane.setVisible(true);
		}
	}

	private void clear() {
		txtMemberID.clear();
		txtMemberID1.clear();
		txtISBN.clear();
		txtIssueNumber.clear();
		txtTitle.clear();
		lblMessage.setText("");
		lblMessage.setVisible(false);
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub

	}

	private boolean validateData() {
		if (rdBook.isSelected()) {
			if (FormValidation.isEmpty(txtMemberID)) {
				FXUtil.showErrorMessage(lblMessage, "Member ID must not non-empty");
				return false;
			} else if (!FormValidation.isNumber(txtMemberID)) {
				FXUtil.showErrorMessage(lblMessage, "Member ID is not numeric. Try again!");
				return false;
			}
		}
		else {
			if (FormValidation.isEmpty(txtMemberID1)) {
				FXUtil.showErrorMessage(lblMessage, "Member ID must not non-empty");
				return false;
			} else if (!FormValidation.isNumber(txtMemberID1)) {
				FXUtil.showErrorMessage(lblMessage, "Member ID is not numeric. Try again!");
				return false;
			}
		}
		if (rdBook.isSelected()) {
			if (!txtISBN.getText().trim().matches("^[0-9]{13}$")) {
				FXUtil.showErrorMessage(lblMessage, "ISBN must be 13 digits long");
				return false;
			}
		}
		if (rdPeriodical.isSelected()) {
			if ((FormValidation.isEmpty(txtTitle) || FormValidation.isEmpty(txtIssueNumber))) {
				FXUtil.showErrorMessage(lblMessage, "Title and Issue Number must not non-empty");
				return false;
			}
		}
		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		FormValidation.addLengthLimiter(txtISBN, ISBN_MAX_LENTH);

	}

}
