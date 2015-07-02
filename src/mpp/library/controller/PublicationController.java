package mpp.library.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mpp.library.model.Author;
import mpp.library.model.Book;
import mpp.library.model.Copy;
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
public class PublicationController {

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
	Text messageBox;

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

		Book book = new Book(bookISBNNumber.getText());
		book.setTitle(bookTitle.getText());
		book.setMaxCheckoutLength(7);

		List<String> authorList = new ArrayList<String>(Arrays.asList(bookAuthor.getText().split(" , ")));

		List<Author> authors = new ArrayList<>();
		Author author = null;
		for (String string : authorList) {
			author = new Author();
			String[] authorNames = string.split("\\s+");
			author.setFirstName(authorNames[0]);
			author.setLastName(authorNames[1]);
			authors.add(author);
		}

		Copy copy = new Copy(book, 1);
		copy.setAvailable(true);
		List<Copy> copies = new ArrayList<>();
		copies.add(copy);
		book.setCopies(copies);

		book.setAuthorList(authors);
		bookDao.save(book);
		messageBox.setText("Book successfully saved");
		messageBox.setVisible(true);
	}

	@FXML
	protected void savePeriodical(MouseEvent event) {
		System.out.println("Saving Periodical...");

		if (!validatePeriodical()) {
			return;
		}

		Periodical periodical = new Periodical(periodicalTitle.getText(), periodicalTitle.getText());
		periodical.setMaxCheckoutLength(Integer.valueOf(periodicalMaxCheckoutCount.getText()));

		Copy copy = new Copy(periodical, 1);
		copy.setAvailable(true);
		List<Copy> copies = new ArrayList<>();
		copies.add(copy);
		periodical.setCopies(copies);

		periodicalDao.save(periodical);
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
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/mpp/library/view/publication/addCopyPublication.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		primaryStage.setScene(new Scene(root, LibraryConstant.WINDOW_HEIGHT, LibraryConstant.WINDOW_WIDTH));
		primaryStage.show();
	}

	public void initialize() {
		initializeTextLimiter();
		initalizeNumericLimiter();
	}

	private void initalizeNumericLimiter() {
		FormValidation.addNumbericLimiter(bookMaxCheckoutCount);
		FormValidation.addNumbericLimiter(periodicalMaxCheckoutCount);
	}

	private void initializeTextLimiter() {
		FormValidation.addLengthLimiter(bookISBNNumber, 20);
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

		if (!FormValidation.isEnteredNumberGreaterThan(bookMaxCheckoutCount, 21)) {
			ValidationDialog.showWarning("Books cannot be checkedout more than " + 21 + " days.");
			bookMaxCheckoutCount.requestFocus();
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

		if (!FormValidation.isEnteredNumberGreaterThan(periodicalMaxCheckoutCount, 7)) {
			ValidationDialog.showWarning("Periodicals cannot be checkedout more than " + 7 + " days.");
			periodicalMaxCheckoutCount.requestFocus();
			return false;
		}

		return true;
	}

}
