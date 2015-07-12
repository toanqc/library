package mpp.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
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
	Label lblStatus;

	@FXML
	ListView<Author> authorCopyPublicationListView;

	ScreenController myController;

	public PublicationCopyController() {
		bookService = new BookServiceImpl();
		periodicalService = new PeriodicalServiceImpl();
	}

	@FXML
	protected void showAddBookScreen(ActionEvent event) {
		periodicalCopyGridPane.setVisible(false);
		FXUtil.clearTextFields(periodicalCopyGridPane);
		bookCopyGridPane.setVisible(true);

	}

	@FXML
	protected void showAddPeriodicalScreen(ActionEvent event) {
		periodicalCopyGridPane.setVisible(true);
		FXUtil.clearTextFields(bookCopyGridPane);
		bookCopyGridPane.setVisible(false);
	}

	@FXML
	protected void renderAddPublication(ActionEvent event) {
		System.out.println("Render Add Publication Scene");

		myController.loadScreen(Screen.PUBLICATION, Screen.PUBLICATION.getValue());
		myController.setScreen(Screen.PUBLICATION);

		FXUtil.clearTextFields(periodicalCopyGridPane);
		FXUtil.clearTextFields(bookCopyGridPane);
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

				// Set Author
				ObservableList<Author> authors = FXCollections.observableArrayList(book.getAuthorList());
				authorCopyPublicationListView.setItems(authors);
				authorCopyPublicationListView.setMouseTransparent(true);
				authorCopyPublicationListView.setFocusTraversable(false);
				authorCopyPublicationListView.setCellFactory(new Callback<ListView<Author>, ListCell<Author>>() {

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
			}
		}
	}

	@FXML
	protected void addBookCopy(ActionEvent event) {
		System.out.println("Adding Book Copy");

		if (!validateBookCopy()) {
			return;
		}

		bookService.addCopy(bookCopyISBNNumber.getText().trim(), Integer.valueOf(bookCopyNumber.getText().trim()));

		postSaveBook();
	}

	private void postSaveBook() {
		FXUtil.showSuccessMessage(lblStatus, "Book Copy successfully added to system");
		FXUtil.clearTextFields(bookCopyGridPane);
	}

	@FXML
	protected void addPeriodicalCopy(ActionEvent event) {
		System.out.println("Adding Periodical Copy");

		if (!validatePeriodicalCopy()) {
			return;
		}

		periodicalService.addCopy(periodicalCopyIssueNumber.getText().trim(), periodicalCopyTitle.getText().trim(),
				Integer.valueOf(periodicalCopyNumber.getText().trim()));

		postSavePeriodical();
	}

	private void postSavePeriodical() {
		FXUtil.showSuccessMessage(lblStatus, "Periodical successfully added to system.");
		FXUtil.clearTextFields(periodicalCopyGridPane);
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
		FormValidation.addLengthLimiter(bookCopyISBNNumber, 13);
		FormValidation.addLengthLimiter(bookCopyTitle, 50);
		FormValidation.addLengthLimiter(periodicalCopyIssueNumber, 10);
		FormValidation.addLengthLimiter(periodicalCopyTitle, 50);
		FormValidation.addLengthLimiter(bookCopyMaxCheckoutCount, 2);
		FormValidation.addLengthLimiter(periodicalCopyMaxCheckoutCount, 2);
		FormValidation.addLengthLimiter(bookCopyNumber, 2);
		FormValidation.addLengthLimiter(periodicalCopyNumber, 2);
	}

	private boolean validateBookCopy() {
		if (FormValidation.isEmpty(bookCopyMaxCheckoutCount) || FormValidation.isEmpty(bookCopyNumber)
				|| FormValidation.isEmpty(bookCopyISBNNumber) || FormValidation.isEmpty(bookCopyTitle)) {
			FXUtil.showErrorMessage(lblStatus, "Please complete the fields");
			return false;
		}

		if (FormValidation.isEnteredNumberGreaterThan(bookCopyMaxCheckoutCount, 21)) {
			FXUtil.showErrorMessage(lblStatus, "Books cannot be checked out for more than " + 21 + " days.");
			bookCopyMaxCheckoutCount.requestFocus();
			return false;
		}

		if (!FormValidation.isEnteredNumberGreaterThan(bookCopyNumber, 0)) {
			FXUtil.showErrorMessage(lblStatus, "Enter valid number of copies");
			bookCopyNumber.requestFocus();
			return false;
		}

		return true;
	}

	private boolean validatePeriodicalCopy() {
		if (FormValidation.isEmpty(periodicalCopyMaxCheckoutCount) || FormValidation.isEmpty(periodicalCopyNumber)
				|| FormValidation.isEmpty(periodicalCopyIssueNumber) || FormValidation.isEmpty(periodicalCopyTitle)) {
			FXUtil.showErrorMessage(lblStatus, "Please complete the fields");
			return false;
		}

		if (FormValidation.isEnteredNumberGreaterThan(periodicalCopyMaxCheckoutCount, 7)) {
			FXUtil.showErrorMessage(lblStatus, "Periodicals cannot be checked out for more than " + 7 + " days.");
			periodicalCopyMaxCheckoutCount.requestFocus();
			return false;
		}

		if (!FormValidation.isEnteredNumberGreaterThan(periodicalCopyNumber, 0)) {
			FXUtil.showErrorMessage(lblStatus, "Enter valid number of copies");
			periodicalCopyNumber.requestFocus();
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
		FXUtil.clearTextFields(bookCopyGridPane);
		FXUtil.clearTextFields(periodicalCopyGridPane);
		lblStatus.setText("");
		lblStatus.setVisible(false);
		myController.setScreen(Screen.HOME);
	}

	@FXML
	protected void cancelCopyBook(ActionEvent event) {
		System.out.println("Cancel Book Copy");
		FXUtil.clearTextFields(bookCopyGridPane);
	}

	@FXML
	protected void cancelCopyPeriodical(ActionEvent event) {
		System.out.println("Cancel Periodical Copy");
		FXUtil.clearTextFields(periodicalCopyGridPane);
	}

}
