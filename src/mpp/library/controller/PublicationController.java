package mpp.library.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import mpp.library.model.Author;
import mpp.library.model.Book;
import mpp.library.model.Periodical;
import mpp.library.model.service.AuthorService;
import mpp.library.model.service.BookService;
import mpp.library.model.service.PeriodicalService;
import mpp.library.model.service.impl.AuthorServiceImpl;
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
public class PublicationController implements ControlledScreen, Initializable {

	private BookService bookService;
	private PeriodicalService periodicalSerivce;
	private AuthorService authorService;

	@FXML
	GridPane periodicalGridPane;

	@FXML
	GridPane bookGridPane;

	@FXML
	ToggleGroup publicationGroup;

	@FXML
	TextField bookISBNNumber;

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
	Label lblStatus;

	@FXML
	TextField bookCopiesNum;

	@FXML
	TextField periodicalCopiesNum;

	@FXML
	ListView<Author> authorPublicationListView;

	ScreenController myController;

	public PublicationController() {
		bookService = new BookServiceImpl();
		periodicalSerivce = new PeriodicalServiceImpl();
		authorService = new AuthorServiceImpl();
	}

	@FXML
	protected void showAddBookScreen(ActionEvent event) {
		periodicalGridPane.setVisible(false);
		FXUtil.clearTextFields(periodicalGridPane);
		bookGridPane.setVisible(true);

	}

	@FXML
	protected void showAddPeriodicalScreen(ActionEvent event) {
		periodicalGridPane.setVisible(true);
		FXUtil.clearTextFields(bookGridPane);
		bookGridPane.setVisible(false);
	}

	@FXML
	protected void saveBook(MouseEvent event) {
		System.out.println("Saving Book");

		if (!validateBook()) {
			return;
		}

		Book book = bookService.getBook(bookISBNNumber.getText());
		if (book != null) {
			FXUtil.showErrorMessage(lblStatus, "Book with provided ISBN already exists.");
			return;
		}

		book = new Book(bookISBNNumber.getText());
		book.setTitle(bookTitle.getText());
		book.setMaxCheckoutLength(7);

		// Set Authors
		List<Author> authorList = authorPublicationListView.getSelectionModel().getSelectedItems();
		book.setAuthorList(authorList);

		bookService.saveBook(book, Integer.parseInt(bookCopiesNum.getText()));
		postSaveBook();
	}

	private void postSaveBook() {
		FXUtil.showSuccessMessage(lblStatus, "Book successfully added to system.");
		FXUtil.clearTextFields(bookGridPane);
	}

	@FXML
	protected void savePeriodical(MouseEvent event) {
		System.out.println("Saving Periodical...");

		if (!validatePeriodical()) {
			return;
		}

		Periodical periodical = periodicalSerivce.getPeriodical(periodicalIssueNumber.getText().trim(),
				periodicalTitle.getText().trim());
		if (periodical != null) {
			FXUtil.showErrorMessage(lblStatus, "Periodical with provided title and issue number already exists.");
			return;
		}

		periodical = new Periodical(periodicalTitle.getText(), periodicalIssueNumber.getText());
		periodical.setMaxCheckoutLength(Integer.valueOf(periodicalMaxCheckoutCount.getText()));

		periodicalSerivce.savePeriodical(periodical, Integer.parseInt(periodicalCopiesNum.getText()));
		postSavePeriodical();
	}

	private void postSavePeriodical() {
		FXUtil.showSuccessMessage(lblStatus, "Periodical successfully added to system.");
		FXUtil.clearTextFields(periodicalGridPane);
	}

	@FXML
	protected void cancelBook(MouseEvent event) {
		System.out.println("Cancel Book");
		FXUtil.clearTextFields(bookGridPane);
	}

	@FXML
	protected void cancelPeriodical(MouseEvent event) {
		System.out.println("Cancel Periodical");
		FXUtil.clearTextFields(periodicalGridPane);
	}

	@FXML
	protected void renderAddCopyPublication(ActionEvent event) {
		System.out.println("Render Add Copy Publication Scene");

		myController.loadScreen(Screen.COPY_PUBLICATION, Screen.COPY_PUBLICATION.getValue());
		myController.setScreen(Screen.COPY_PUBLICATION);

		FXUtil.clearTextFields(periodicalGridPane);
		FXUtil.clearTextFields(bookGridPane);
	}

	private void initalizeNumericLimiter() {
		FormValidation.addNumbericLimiter(bookMaxCheckoutCount);
		FormValidation.addNumbericLimiter(periodicalMaxCheckoutCount);
		FormValidation.addNumbericLimiter(bookCopiesNum);
		FormValidation.addNumbericLimiter(periodicalCopiesNum);
	}

	private void initializeTextLimiter() {
		FormValidation.addLengthLimiter(bookISBNNumber, 13);
		FormValidation.addLengthLimiter(bookTitle, 50);
		FormValidation.addLengthLimiter(bookMaxCheckoutCount, 2);
		FormValidation.addLengthLimiter(periodicalIssueNumber, 10);
		FormValidation.addLengthLimiter(periodicalTitle, 50);
		FormValidation.addLengthLimiter(periodicalMaxCheckoutCount, 2);
		FormValidation.addLengthLimiter(bookCopiesNum, 2);
		FormValidation.addLengthLimiter(periodicalCopiesNum, 2);
	}

	private boolean validateBook() {
		if (FormValidation.isEmpty(bookISBNNumber) || FormValidation.isEmpty(bookTitle)
				|| FormValidation.isEmpty(bookMaxCheckoutCount) || FormValidation.isEmpty(bookCopiesNum)) {
			FXUtil.showErrorMessage(lblStatus, "Please complete the fields");
			return false;
		}

		if (FormValidation.isEnteredNumberGreaterThan(bookMaxCheckoutCount, 21)) {
			FXUtil.showErrorMessage(lblStatus, "Books cannot be checked out for more than " + 21 + " days.");
			bookMaxCheckoutCount.requestFocus();
			return false;
		}

		if (!FormValidation.isEnteredNumberGreaterThan(bookCopiesNum, 0)) {
			FXUtil.showErrorMessage(lblStatus, "Enter valid number of copies");
			bookCopiesNum.requestFocus();
			return false;
		}

		if (FormValidation.isNumberAndExactLength(bookISBNNumber, 14)) {
			FXUtil.showErrorMessage(lblStatus, "Book ISBN should be length of 13 digits");
			bookISBNNumber.requestFocus();
			return false;
		}

		if (authorPublicationListView.getSelectionModel().getSelectedItems().isEmpty()) {
			FXUtil.showErrorMessage(lblStatus, "Please select at least one author names.");
			bookISBNNumber.requestFocus();
			return false;
		}

		return true;
	}

	private boolean validatePeriodical() {
		if (FormValidation.isEmpty(periodicalIssueNumber) || FormValidation.isEmpty(periodicalTitle)
				|| FormValidation.isEmpty(periodicalMaxCheckoutCount) || FormValidation.isEmpty(periodicalCopiesNum)) {
			FXUtil.showErrorMessage(lblStatus, "Please complete the fields");
			return false;
		}

		if (!FormValidation.isEnteredNumberGreaterThan(periodicalCopiesNum, 0)) {
			FXUtil.showErrorMessage(lblStatus, "Enter valid number of copies");
			bookCopiesNum.requestFocus();
			return false;
		}

		if (FormValidation.isEnteredNumberGreaterThan(periodicalMaxCheckoutCount, 7)) {
			FXUtil.showErrorMessage(lblStatus, "Periodicals cannot be checkedout more than " + 7 + " days.");
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
		FXUtil.clearTextFields(bookGridPane);
		FXUtil.clearTextFields(periodicalGridPane);
		myController.setScreen(Screen.HOME);
	}

	@FXML
	public void renderAddPublication() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeTextLimiter();
		initalizeNumericLimiter();

		ObservableList<Author> authors = FXCollections.observableArrayList(authorService.getList());
		authorPublicationListView.setItems(authors);
		authorPublicationListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		authorPublicationListView.setCellFactory(new Callback<ListView<Author>, ListCell<Author>>() {

			@Override
			public ListCell<Author> call(ListView<Author> p) {

				ListCell<Author> cell = new ListCell<Author>() {

					@Override
					protected void updateItem(Author t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getFirstName() + " " + t.getLastName());
						}
					}

				};

				return cell;
			}
		});
	}

}
