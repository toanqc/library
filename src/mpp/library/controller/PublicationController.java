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

public class PublicationController {

	BookDAO bookDao;

	PeriodicalDAO periodicalDao;

	FXUtil fxUtil;

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
			root = FXMLLoader.load(getClass().getResource("/mpp/library/view/addCopyPublication.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}
