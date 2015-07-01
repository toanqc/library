package mpp.library.controller;

import javafx.scene.control.Label;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import mpp.library.model.Book;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.dao.impl.CheckoutDAOFacade;

/**
 * 
 * @author bpham4
 *
 */
public class CheckoutController {

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

	private CheckoutDAOFacade checkoutDAO = new CheckoutDAOFacade();
	
	@FXML
	protected void gotoMainScreen(MouseEvent event) {
		System.out.println("Goto MainScreen");
	}

	@FXML
	protected void proceedCheckout(MouseEvent event) {
		if (rdBook.isSelected()) {
			checkoutBook();
		} else if (rdPeriodical.isSelected()) {
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
			Copy copy = checkoutDAO.copyIsAvailable(book);
			if (copy != null) {
				CheckoutRecord ckRecord = new CheckoutRecord(member);
				LocalDate chkoutDate = LocalDate.now();
				LocalDate dueDate = chkoutDate.plusDays(book.getMaxCheckoutLength());
				CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(chkoutDate, dueDate, copy);
				ckRecord.addCheckoutEntry(ckRecordEntry);
				checkoutDAO.saveCheckoutRecord(ckRecord);
			} else {
				lblMessage.setText("The copy is not available");
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
			Copy copy = checkoutDAO.copyIsAvailable(periodical);
			if (copy != null) {
				CheckoutRecord ckRecord = new CheckoutRecord(member);
				LocalDate chkoutDate = LocalDate.now();
				LocalDate dueDate = chkoutDate.plusDays(periodical.getMaxCheckoutLength());
				CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(chkoutDate, dueDate, copy);
				ckRecord.addCheckoutEntry(ckRecordEntry);
				checkoutDAO.saveCheckoutRecord(ckRecord);
			} else {
				lblMessage.setText("The copy is not available");
				lblMessage.setVisible(true);
			}
		}
	}

	@FXML
	protected void handleCancel(MouseEvent event) {
		System.out.println("handle Cancel");
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
	}
}
