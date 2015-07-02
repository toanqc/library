package mpp.library.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mpp.library.model.Author;
import mpp.library.model.Book;
import mpp.library.model.Periodical;
import mpp.library.model.dao.BookDAO;
import mpp.library.model.dao.PeriodicalDAO;
import mpp.library.model.dao.impl.BookDAOImpl;
import mpp.library.model.dao.impl.PeriodicalDAOImpl;
import mpp.library.util.FXUtil;
import mpp.library.util.LibraryConstant;
import mpp.library.view.FormValidation;

/**
 * @author Anil
 *
 */
public class PublicationCopyController {

	private FXUtil fxUtil;

	private BookDAO bookDao;

	private PeriodicalDAO periodicalDao;

	@FXML
	GridPane periodicalCopyGridPane;

	@FXML
	GridPane bookCopyGridPane;

	@FXML
	ToggleGroup publicationCopyGroup;

	@FXML
	TextField bookCopyISBNNumber;

	@FXML
	TextField bookCopyAuthor;

	@FXML
	TextField bookCopyTitle;

	@FXML
	TextField bookCopyNumber;

	@FXML
	TextField bookCopyMaxCheckoutCount;

	@FXML
	TextField periodicalCopyIssueNumber;

	@FXML
	TextField periodicalCopyTitle;

	@FXML
	TextField periodicalCopyMaxCheckoutCount;

	@FXML
	TextField periodicalCopyNumber;

	@FXML
	Text messageBox;

	public PublicationCopyController() {
		fxUtil = new FXUtil();
		bookDao = new BookDAOImpl();
		periodicalDao = new PeriodicalDAOImpl();
	}

	@FXML
	protected void showAddBookScreen(ActionEvent event) {
		periodicalCopyGridPane.setVisible(false);
		fxUtil.clearTextFields(periodicalCopyGridPane);
		bookCopyGridPane.setVisible(true);

	}

	@FXML
	protected void showAddPeriodicalScreen(ActionEvent event) {
		periodicalCopyGridPane.setVisible(true);
		fxUtil.clearTextFields(bookCopyGridPane);
		bookCopyGridPane.setVisible(false);
	}

	@FXML
	protected void renderAddPublication(ActionEvent event) {
		System.out.println("Render Add Publication Scene");
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/mpp/library/view/publication/addPublication.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		primaryStage.setScene(new Scene(root, LibraryConstant.WINDOW_HEIGHT, LibraryConstant.WINDOW_WIDTH));
		primaryStage.show();
	}

	@FXML
	protected void searchBook(ActionEvent event) {
		System.out.println("Searching Book with ISBN: " + bookCopyISBNNumber.getText());
		String isbnNumber = bookCopyISBNNumber.getText().trim();
		Book book = null;

		if (isbnNumber.length() > 0) {
			book = bookDao.get(isbnNumber);
			if (book != null) {
				bookCopyMaxCheckoutCount.setText(String.valueOf(book.getMaxCheckoutLength()));
				bookCopyTitle.setText(book.getTitle());

				List<Author> authors = book.getAuthorList();
				StringBuilder builder = new StringBuilder();

				Iterator<Author> authorIterator = authors.iterator();

				while (authorIterator.hasNext()) {
					Author auth = authorIterator.next();
					builder.append(auth.getFirstName());
					builder.append(" ");
					builder.append(auth.getLastName());
					if (authorIterator.hasNext()) {
						builder.append(", ");
					}
				}

				bookCopyAuthor.setText(String.join(",", builder.toString()));
				bookCopyNumber.setText(String.valueOf(book.getCopies().size() + 1));
				bookCopyNumber.setEditable(false);
			}
		}
	}

	@FXML
	protected void searchPeriodical(ActionEvent event) {
		System.out.println("Searching Periodical..");
		String issueNumber = periodicalCopyIssueNumber.getText().trim();
		String title = periodicalCopyTitle.getText().trim();
		Periodical periodical = null;

		if (title.length() > 0) {
			periodical = periodicalDao.get(issueNumber, title);
			if (periodical != null) {
				System.out.println("Got it");
				periodicalCopyMaxCheckoutCount.setText(String.valueOf(periodical.getMaxCheckoutLength()));
				periodicalCopyTitle.setText(periodical.getTitle());
				periodicalCopyNumber.setText(String.valueOf(periodical.getCopies().size() + 1));
				periodicalCopyNumber.setEditable(false);
			}
		}
	}

	@FXML
	protected void addBookCopy(ActionEvent event) {
		System.out.println("Adding Book Copy");

		if (!validateBookCopy()) {
			return;
		}

		Book book = bookDao.get(bookCopyISBNNumber.getText().trim());
		if (book != null) {
			bookDao.addCopy(book, Integer.valueOf(bookCopyNumber.getText().trim()));
		}

		messageBox.setText("Book Copy successfully added");
		messageBox.setVisible(true);

	}

	@FXML
	protected void addPeriodicalCopy(ActionEvent event) {
		System.out.println("Adding Periodical Copy");

		if (!validatePeriodicalCopy()) {
			return;
		}

		Periodical periodical = periodicalDao.get(periodicalCopyIssueNumber.getText().trim(),
				periodicalCopyTitle.getText().trim());
		if (periodical != null) {
			periodicalDao.addCopy(periodical, Integer.valueOf(periodicalCopyNumber.getText().trim()));
		}

		messageBox.setText("Pediodical Copy successfully added");
		messageBox.setVisible(true);
	}

	public void initialize() {
		initializeTextLimiter();
		initalizeNumericLimiter();
	}

	private void initalizeNumericLimiter() {
		FormValidation.addNumbericLimiter(bookCopyMaxCheckoutCount);
		FormValidation.addNumbericLimiter(periodicalCopyMaxCheckoutCount);
		FormValidation.addNumbericLimiter(bookCopyNumber);
		FormValidation.addNumbericLimiter(periodicalCopyNumber);
	}

	private void initializeTextLimiter() {
		FormValidation.addLengthLimiter(bookCopyISBNNumber, 20);
		FormValidation.addLengthLimiter(bookCopyAuthor, 100);
		FormValidation.addLengthLimiter(bookCopyTitle, 50);
		FormValidation.addLengthLimiter(periodicalCopyIssueNumber, 21);
		FormValidation.addLengthLimiter(periodicalCopyTitle, 20);
	}

	private boolean validateBookCopy() {
		if (FormValidation.isEmpty(bookCopyMaxCheckoutCount) || FormValidation.isEmpty(bookCopyNumber)
				|| FormValidation.isEmpty(bookCopyISBNNumber) || FormValidation.isEmpty(bookCopyAuthor)
				|| FormValidation.isEmpty(bookCopyTitle)) {
			ValidationDialog.showWarning("All fields are mandatory!");
			return false;
		}

		if (!FormValidation.isEnteredNumberGreaterThan(bookCopyMaxCheckoutCount, 21)) {
			ValidationDialog.showWarning("Books cannot be checked out for more than " + 21 + " days.");
			bookCopyMaxCheckoutCount.requestFocus();
			return false;
		}

		return true;
	}

	private boolean validatePeriodicalCopy() {
		if (FormValidation.isEmpty(periodicalCopyMaxCheckoutCount) || FormValidation.isEmpty(periodicalCopyNumber)
				|| FormValidation.isEmpty(periodicalCopyIssueNumber) || FormValidation.isEmpty(periodicalCopyTitle)) {
			ValidationDialog.showWarning("All fields are mandatory!");
			return false;
		}

		if (!FormValidation.isEnteredNumberGreaterThan(periodicalCopyMaxCheckoutCount, 7)) {
			ValidationDialog.showWarning("Periodicals cannot be checked out for more than " + 7 + " days.");
			periodicalCopyMaxCheckoutCount.requestFocus();
			return false;
		}

		return true;
	}

}
