package mpp.library.controller;

import java.util.Iterator;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import mpp.library.model.Author;
import mpp.library.model.Book;
import mpp.library.model.Periodical;
import mpp.library.model.service.BookService;
import mpp.library.model.service.PeriodicalService;
import mpp.library.model.service.impl.BookServiceImpl;
import mpp.library.model.service.impl.PeriodicalServiceImpl;
import mpp.library.util.FXUtil;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * @author Anil
 *
 */
public class PublicationCopyController implements ControlledScreen {

	private FXUtil fxUtil;

	private BookService bookService;

	private PeriodicalService periodicalService;

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
	Text messageBoxBook;
	
	@FXML
	Text messageBoxPeriodical;

	ScreenController myController;
	
	private FadeTransition fadeOutAddBookMessage;
	private FadeTransition fadeOutAddPeriodicalMessage;

	public PublicationCopyController() {
		fxUtil = new FXUtil();
		bookService = new BookServiceImpl();
		periodicalService = new PeriodicalServiceImpl();
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

		myController.loadScreen(Screen.PUBLICATION, Screen.PUBLICATION.getValue());
		myController.setScreen(Screen.PUBLICATION);
		myController.setSize(Screen.PUBLICATION.getWidth(), Screen.PUBLICATION.getHeight());
		
		fxUtil.clearTextFields(periodicalCopyGridPane);
		fxUtil.clearTextFields(bookCopyGridPane);
	}

	@FXML
	protected void searchBook(ActionEvent event) {
		System.out.println("Searching Book with ISBN: " + bookCopyISBNNumber.getText());
		String isbnNumber = bookCopyISBNNumber.getText().trim();
		Book book = null;

		if (isbnNumber.length() > 0) {
			book = bookService.getBook(isbnNumber);
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
			periodical = periodicalService.getPeriodical(issueNumber, title);
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

		Book book = bookService.getBook(bookCopyISBNNumber.getText().trim());
		if (book != null) {
			bookService.addCopy(book, Integer.valueOf(bookCopyNumber.getText().trim()));
		}

		postSaveBook();
	}
	
	private void postSaveBook() {
		messageBoxBook.setText("Book Copy successfully added to system");
		messageBoxBook.setVisible(true);
		fxUtil.clearTextFields(bookCopyGridPane);
		fadeOutAddBookMessage.play();
	}

	@FXML
	protected void addPeriodicalCopy(ActionEvent event) {
		System.out.println("Adding Periodical Copy");

		if (!validatePeriodicalCopy()) {
			return;
		}

		Periodical periodical = periodicalService.getPeriodical(periodicalCopyIssueNumber.getText().trim(),
				periodicalCopyTitle.getText().trim());
		if (periodical != null) {
			periodicalService.addCopy(periodical, Integer.valueOf(periodicalCopyNumber.getText().trim()));
		}

		postSavePeriodical();
	}
	
	private void postSavePeriodical() {
		messageBoxPeriodical.setText("Periodical successfully added to system.");
		messageBoxPeriodical.setVisible(true);
		fxUtil.clearTextFields(periodicalCopyGridPane);
		fadeOutAddPeriodicalMessage.play();
	}

	public void initialize() {
		initializeTextLimiter();
		initalizeNumericLimiter();
		fadeOutAddBookMessage = fxUtil.createFadeOutEffect(messageBoxBook, 5000);
		fadeOutAddPeriodicalMessage = fxUtil.createFadeOutEffect(messageBoxPeriodical, 5000);
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

		if (FormValidation.isEnteredNumberGreaterThan(bookCopyMaxCheckoutCount, 21)) {
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

		if (FormValidation.isEnteredNumberGreaterThan(periodicalCopyMaxCheckoutCount, 7)) {
			ValidationDialog.showWarning("Periodicals cannot be checked out for more than " + 7 + " days.");
			periodicalCopyMaxCheckoutCount.requestFocus();
			return false;
		}

		return true;
	}

	@Override
	public void setScreenParent(ScreenController screenParent) {
		myController = screenParent;
	}

	@Override
	public void repaint() {

	}

	@FXML
	public void returnHome() {
		myController.setScreen(Screen.HOME);
		myController.setSize(Screen.HOME.getWidth(), Screen.HOME.getHeight());
	}
	
	@FXML
	protected void cancelCopyBook(ActionEvent event) {
		System.out.println("Cancel Book Copy");
		fxUtil.clearTextFields(bookCopyGridPane);
	}

	@FXML
	protected void cancelCopyPeriodical(ActionEvent event) {
		System.out.println("Cancel Periodical Copy");
		fxUtil.clearTextFields(periodicalCopyGridPane);
	}

}
