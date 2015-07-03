package mpp.library.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import mpp.library.model.Author;
import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.dao.BookDAO;
import mpp.library.model.dao.PeriodicalDAO;
import mpp.library.model.dao.impl.BookDAOImpl;
import mpp.library.model.dao.impl.PeriodicalDAOImpl;
import mpp.library.util.FXUtil;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * @author Anil
 *
 */
public class PublicationController implements ControlledScreen {

	private BookDAO bookDao;

	private PeriodicalDAO periodicalDao;

	private FXUtil fxUtil;

	@FXML
	GridPane periodicalGridPane;

	@FXML
	GridPane bookGridPane;

	@FXML
	ToggleGroup publicationGroup;

	@FXML
	TextField bookISBNNumber;

	@FXML
	TextField bookAuthor;

	@FXML
	TextField bookTitle;

	@FXML
	TextField bookMaxCheckoutCount;

	@FXML
	TextField periodicalIssueNumber;

	@FXML
	TextField periodicalTitle;

	@FXML
	TextField periodicalMaxCheckoutCount;

	@FXML
	Text messageBoxBook;

	@FXML
	Text messageBoxPeriodical;

	ScreenController myController;

	private FadeTransition fadeOutAddBookMessage;
	private FadeTransition fadeOutAddPeriodicalMessage;

	public PublicationController() {
		bookDao = new BookDAOImpl();
		periodicalDao = new PeriodicalDAOImpl();
		fxUtil = new FXUtil();
	}

	@FXML
	protected void showAddBookScreen(ActionEvent event) {
		periodicalGridPane.setVisible(false);
		fxUtil.clearTextFields(periodicalGridPane);
		bookGridPane.setVisible(true);

	}

	@FXML
	protected void showAddPeriodicalScreen(ActionEvent event) {
		periodicalGridPane.setVisible(true);
		fxUtil.clearTextFields(bookGridPane);
		bookGridPane.setVisible(false);
	}

	@FXML
	protected void saveBook(MouseEvent event) {
		System.out.println("Saving Book");

		if (!validateBook()) {
			return;
		}

		Book book = bookDao.get(bookISBNNumber.getText());
		if (book != null) {
			fxUtil.createDialogAndRequestFocus("The entered book with provided ISBN already exists in the system.",
					bookISBNNumber);
			return;
		}

		book = new Book(bookISBNNumber.getText());
		book.setTitle(bookTitle.getText());
		book.setMaxCheckoutLength(7);

		List<String> authorList = new ArrayList<String>(Arrays.asList(bookAuthor.getText().split(" , ")));

		List<Author> authors = new ArrayList<>();
		Author author = null;
		for (String string : authorList) {
			author = new Author();
			String[] authorNames = string.split("\\s+");
			author.setFirstName(authorNames[0]);
			String lastName = authorNames.length > 0 ? authorNames[authorNames.length - 1] : "";
			author.setLastName(lastName);
			authors.add(author);
		}

		Copy copy = new Copy(book, 1);
		copy.setAvailable(true);
		List<Copy> copies = new ArrayList<>();
		copies.add(copy);
		book.setCopies(copies);

		book.setAuthorList(authors);
		bookDao.save(book);
		postSaveBook();
	}

	private void postSaveBook() {
		messageBoxBook.setText("Book successfully added to system.");
		messageBoxBook.setVisible(true);
		fxUtil.clearTextFields(bookGridPane);
		fadeOutAddBookMessage.play();
	}

	@FXML
	protected void savePeriodical(MouseEvent event) {
		System.out.println("Saving Periodical...");

		if (!validatePeriodical()) {
			return;
		}

		Periodical periodical = periodicalDao.get(periodicalTitle.getText(), periodicalIssueNumber.getText());
		if (periodical != null) {
			fxUtil.createDialogAndRequestFocus(
					"The entered periodical with provided title and issue number already exists in the system.",
					bookISBNNumber);
			return;
		}

		periodical = new Periodical(periodicalTitle.getText(), periodicalIssueNumber.getText());
		periodical.setMaxCheckoutLength(Integer.valueOf(periodicalMaxCheckoutCount.getText()));

		Copy copy = new Copy(periodical, 1);
		copy.setAvailable(true);
		List<Copy> copies = new ArrayList<>();
		copies.add(copy);
		periodical.setCopies(copies);

		periodicalDao.save(periodical);
		postSavePeriodical();
	}

	private void postSavePeriodical() {
		messageBoxPeriodical.setText("Periodical successfully added to system.");
		messageBoxPeriodical.setVisible(true);
		fxUtil.clearTextFields(bookGridPane);
		fadeOutAddPeriodicalMessage.play();
	}

	@FXML
	protected void cancelBook(MouseEvent event) {
		System.out.println("Cancel Book");
		fxUtil.clearTextFields(bookGridPane);
	}

	@FXML
	protected void cancelPeriodical(MouseEvent event) {
		System.out.println("Cancel Periodical");
		fxUtil.clearTextFields(periodicalGridPane);
	}

	@FXML
	protected void renderAddCopyPublication(ActionEvent event) {
		System.out.println("Render Add Copy Publication Scene");

		myController.loadScreen(Screen.COPY_PUBLICATION, Screen.COPY_PUBLICATION.getValue());
		myController.setScreen(Screen.COPY_PUBLICATION);
		myController.setSize(Screen.COPY_PUBLICATION.getWidth(), Screen.COPY_PUBLICATION.getHeight());

		fxUtil.clearTextFields(periodicalGridPane);
		fxUtil.clearTextFields(bookGridPane);
	}

	@FXML
	public void initialize() {
		initializeTextLimiter();
		initalizeNumericLimiter();
		fadeOutAddBookMessage = fxUtil.createFadeOutEffect(messageBoxBook, 5000);
		fadeOutAddPeriodicalMessage = fxUtil.createFadeOutEffect(messageBoxPeriodical, 5000);
	}

	private void initalizeNumericLimiter() {
		FormValidation.addNumbericLimiter(bookMaxCheckoutCount);
		FormValidation.addNumbericLimiter(periodicalMaxCheckoutCount);
	}

	private void initializeTextLimiter() {
		FormValidation.addLengthLimiter(bookISBNNumber, 13);
		FormValidation.addLengthLimiter(bookAuthor, 100);
		FormValidation.addLengthLimiter(bookTitle, 50);
		FormValidation.addLengthLimiter(bookMaxCheckoutCount, 21);
		FormValidation.addLengthLimiter(periodicalIssueNumber, 20);
		FormValidation.addLengthLimiter(periodicalTitle, 5);
		FormValidation.addLengthLimiter(periodicalMaxCheckoutCount, 7);
	}

	private boolean validateBook() {
		if (FormValidation.isEmpty(bookISBNNumber) || FormValidation.isEmpty(bookAuthor)
				|| FormValidation.isEmpty(bookTitle) || FormValidation.isEmpty(bookMaxCheckoutCount)) {
			ValidationDialog.showWarning("All fields are mandatory!");
			return false;
		}

		if (FormValidation.isEnteredNumberGreaterThan(bookMaxCheckoutCount, 21)) {
			ValidationDialog.showWarning("Books cannot be checked out for more than " + 21 + " days.");
			bookMaxCheckoutCount.requestFocus();
			return false;
		}

		if (FormValidation.isNumberAndExactLength(bookISBNNumber, 14)) {
			ValidationDialog.showWarning("Book ISBN should be length of 13 digits");
			bookISBNNumber.requestFocus();
			return false;
		}

		return true;
	}

	private boolean validatePeriodical() {
		if (FormValidation.isEmpty(periodicalIssueNumber) || FormValidation.isEmpty(periodicalTitle)
				|| FormValidation.isEmpty(periodicalMaxCheckoutCount)) {
			ValidationDialog.showWarning("All fields are mandatory!");
			return false;
		}

		if (FormValidation.isEnteredNumberGreaterThan(periodicalMaxCheckoutCount, 7)) {
			ValidationDialog.showWarning("Periodicals cannot be checkedout more than " + 7 + " days.");
			periodicalMaxCheckoutCount.requestFocus();
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

}
