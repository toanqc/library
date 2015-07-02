package mpp.library.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import mpp.library.model.Book;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.dao.impl.CheckoutDAOFacade;
import mpp.library.model.dao.impl.CheckoutRecordDAOFacade;
import mpp.library.model.dao.impl.CheckoutRecordEntryDAOFacade;
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
	private TextField txtISBN;
	@FXML
	private TextField txtIssueNumber;
	@FXML
	private TextField txtTitle;
	@FXML
	private Label lblMessage;
	@FXML
	private Button btnCheckout;

	private CheckoutDAOFacade checkoutDAO = new CheckoutDAOFacade();

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
			checkoutBook();
		} else if (rdPeriodical.isSelected() && validateData()) {
			lblMessage.setVisible(false);
			lblMessage.setText("");
			checkoutPeriodical();
		}
	}

	private void checkoutBook() {
		String memberId = txtMemberID.getText().trim();
		String ISBN = txtISBN.getText().trim();
		// check if memberID exist
		checkoutDAO = new CheckoutDAOFacade();
		LibraryMember member = checkoutDAO.get(memberId);
		if (member == null) {
			lblMessage.setText("Member ID not found");
			lblMessage.setVisible(true);
		} else {
			// check if ISBN exist and copy is available
			Publication book = new Book(ISBN);
			Publication publication = checkoutDAO.copyIsAvailable(book);
			if (publication != null) {
				List<Copy> listCopies = publication.getCopies();
				if (listCopies != null) {
					Copy copy = null;
					for (int i = 0; i < listCopies.size(); i++) {
						if (listCopies.get(i).getAvailable()) {
							copy = listCopies.get(i);
							i = listCopies.size();
						}
					}
					if (copy != null) {
						CheckoutRecordDAOFacade chkoutRecordDAOFacade = new CheckoutRecordDAOFacade();
						CheckoutRecordEntryDAOFacade chkoutRecordEntryDAOFacade = new CheckoutRecordEntryDAOFacade();
						// read the file CheckoutRecord and then append the new
						// record into the file
						CheckoutRecord currentRecord = chkoutRecordDAOFacade
								.getCheckoutRecord(memberId);
						LocalDate chkoutDate = LocalDate.now();
						LocalDate dueDate = chkoutDate.plus(
								publication.getMaxCheckoutLength(),
								ChronoUnit.DAYS);
						CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(
								chkoutDate, dueDate, copy);
						copy.setAvailable(false);
						currentRecord.addCheckoutEntry(ckRecordEntry);
						chkoutRecordDAOFacade.update(currentRecord);
						chkoutRecordEntryDAOFacade.update(ckRecordEntry);
					} else {
						lblMessage
								.setText("The copy of the book is not available");
						lblMessage.setVisible(true);
					}
				}

			} else {
				lblMessage.setText("The book is not available");
				lblMessage.setVisible(true);
			}
		}
	}

	private void checkoutPeriodical() {
		String memberId = txtMemberID.getText().trim();
		String title = txtTitle.getText().trim();
		String issueNo = txtIssueNumber.getText().trim();

		// check if memberID exist
		CheckoutDAOFacade checkoutDAO = new CheckoutDAOFacade();
		LibraryMember member = checkoutDAO.get(memberId);
		if (member == null) {
			lblMessage.setText("Member ID not found");
			lblMessage.setVisible(true);
		} else {
			// check if ISBN exist and copy is available
			Publication periodical = new Periodical(title, issueNo);
			Publication publication = checkoutDAO.copyIsAvailable(periodical);
			if (publication != null) {
				List<Copy> listCopies = publication.getCopies();
				if (listCopies != null) {
					Copy copy = null;
					for (int i = 0; i < listCopies.size(); i++) {
						if (listCopies.get(i).getAvailable()) {
							copy = listCopies.get(i);
							i = listCopies.size();
						}
					}
					if (copy != null) {
						CheckoutRecordDAOFacade chkoutRecordDAOFacade = new CheckoutRecordDAOFacade();
						CheckoutRecordEntryDAOFacade chkoutRecordEntryDAOFacade = new CheckoutRecordEntryDAOFacade();
						// read the file CheckoutRecord and then append the new
						// record into the file
						CheckoutRecord currentRecord = chkoutRecordDAOFacade
								.getCheckoutRecord(memberId);

						LocalDate chkoutDate = LocalDate.now();
						LocalDate dueDate = chkoutDate.plus(
								publication.getMaxCheckoutLength(),
								ChronoUnit.DAYS);
						CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(
								chkoutDate, dueDate, copy);
						copy.setAvailable(false);
						currentRecord.addCheckoutEntry(ckRecordEntry);
						chkoutRecordDAOFacade.update(currentRecord);
						chkoutRecordEntryDAOFacade.update(ckRecordEntry);
					} else {
						lblMessage
								.setText("The copy of the periodical is not available");
						lblMessage.setVisible(true);
					}
				}
			} else {
				lblMessage.setText("The copy is not available");
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
			mainGridPane.getChildren().remove(periodicalGridPane);
			mainGridPane.add(bookGridPane, 0, 2, 2, 1);
		}

	}

	@FXML
	protected void checkoutPeriodical(MouseEvent event) {
		if (rdPeriodical.isSelected()) {
			clear();
			rdBook.setSelected(false);
			bookGridPane.setVisible(false);
			periodicalGridPane.setVisible(true);
			mainGridPane.getChildren().remove(bookGridPane);
			mainGridPane.add(periodicalGridPane, 0, 2, 2, 1);
		}
	}

	private void clear() {
		txtMemberID.clear();
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
		if (FormValidation.isEmpty(txtMemberID)) {
			lblMessage.setText("Member ID must not non-empty");
			lblMessage.setVisible(true);
			return false;
		} else if (!FormValidation.isNumber(txtMemberID)) {
			lblMessage.setText("Member ID is not numeric. Try again");
			lblMessage.setVisible(true);
			return false;
		}
		if (rdBook.isSelected()) {
			if (!txtISBN.getText().trim().matches("^[0-9]{13}$")) {
				lblMessage.setText("ISBN must be 13 digits long");
				lblMessage.setVisible(true);
				return false;
			}
		}
		if (rdPeriodical.isSelected()) {
			if ((FormValidation.isEmpty(txtTitle) || FormValidation
					.isEmpty(txtIssueNumber))) {
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

	public static final int ISBN_MAX_LENTH = 13;

}
