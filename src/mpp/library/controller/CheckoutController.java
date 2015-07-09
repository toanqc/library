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
import mpp.library.model.service.impl.CheckoutBookServiceImpl;
import mpp.library.model.service.impl.CheckoutPeriodicalServiceImpl;
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

	private CheckoutBookServiceImpl chkoutBookService = new CheckoutBookServiceImpl();
	private CheckoutPeriodicalServiceImpl chkoutPeriodicalService = new CheckoutPeriodicalServiceImpl();

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
				lblMessage.setText("Check out successfully");
				lblMessage.setVisible(true);
			} catch (Exception e) {
				lblMessage.setText(e.getMessage());
				lblMessage.setVisible(true);
			}

		} else if (rdPeriodical.isSelected() && validateData()) {
			lblMessage.setVisible(false);
			lblMessage.setText("");
			String memberId = txtMemberID1.getText().trim();
			String title = txtTitle.getText().trim();
			String issueNo = txtIssueNumber.getText().trim();
			try {
				chkoutPeriodicalService.checkout(memberId, new Periodical(title, issueNo));
				lblMessage.setText("Check out successfully");
				lblMessage.setVisible(true);
			} catch (Exception e) {
				lblMessage.setText(e.getMessage());
				lblMessage.setVisible(true);
			}
		}
	}

	@FXML
	protected void handleCancel(MouseEvent event) {
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
				lblMessage.setText("Member ID must not non-empty");
				lblMessage.setVisible(true);
				return false;
			} else if (!FormValidation.isNumber(txtMemberID)) {
				lblMessage.setText("Member ID is not numeric. Try again");
				lblMessage.setVisible(true);
				return false;
			}
		}
		else {
			if (FormValidation.isEmpty(txtMemberID1)) {
				lblMessage.setText("Member ID must not non-empty");
				lblMessage.setVisible(true);
				return false;
			} else if (!FormValidation.isNumber(txtMemberID1)) {
				lblMessage.setText("Member ID is not numeric. Try again");
				lblMessage.setVisible(true);
				return false;
			}
		}
		if (rdBook.isSelected()) {
			if (!txtISBN.getText().trim().matches("^[0-9]{13}$")) {
				lblMessage.setText("ISBN must be 13 digits long");
				lblMessage.setVisible(true);
				return false;
			}
		}
		if (rdPeriodical.isSelected()) {
			if ((FormValidation.isEmpty(txtTitle) || FormValidation.isEmpty(txtIssueNumber))) {
				lblMessage.setText("Title and Issue Number must not non-empty");
				lblMessage.setVisible(true);
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
