package mpp.library.controller;

import java.util.List;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import mpp.library.model.Author;
import mpp.library.model.service.AuthorService;
import mpp.library.model.service.impl.AuthorServiceImpl;
import mpp.library.util.FXUtil;
import mpp.library.view.ControlledScreen;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * @author Toan Quach
 */
public class AuthorListController implements ControlledScreen {

	@FXML
	Button btnHome;

	@FXML
	Button btnAdd;

	@FXML
	TextField txtSearch;

	@FXML
	Button btnSearch;

	@FXML
	TableView<Author> authorTable;

	@FXML
	TableColumn<Author, Integer> authorId;

	@FXML
	TableColumn<Author, String> firstName;

	@FXML
	TableColumn<Author, String> lastName;

	@FXML
	TableColumn<Author, String> street;

	@FXML
	TableColumn<Author, String> city;

	@FXML
	TableColumn<Author, String> state;

	@FXML
	TableColumn<Author, Integer> zip;

	@FXML
	TableColumn<Author, String> phone;

	@FXML
	TableColumn<Author, String> bio;

	private ObservableList<Author> libraryAuthorList;

	private AuthorService authorService;

	public AuthorListController() {
		authorService = new AuthorServiceImpl();
	}

	@FXML
	private void initialize() {
		handleSelectedRow();
		bindProperties();
		buildData();
	}

	private void handleSelectedRow() {
		authorId.setCellFactory(new Callback<TableColumn<Author, Integer>, TableCell<Author, Integer>>() {
			@SuppressWarnings("unchecked")
			@Override
			public TableCell<Author, Integer> call(TableColumn<Author, Integer> param) {
				TableCell<Author, Integer> cell = new TableCell<Author, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						setText((item == null || empty) ? null : item.toString());
						setGraphic(null);
					}
				};
				cell.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2) {
						Author author = (Author) authorTable.getItems()
								.get(((TableCell<Author, Integer>) event.getSource()).getIndex());
						openDetailMemberStage(author.getId());
					}
				});

				return cell;
			}
		});

		bio.setCellFactory(new Callback<TableColumn<Author, String>, TableCell<Author, String>>() {
			@SuppressWarnings("unchecked")
			@Override
			public TableCell<Author, String> call(TableColumn<Author, String> param) {

				TableCell<Author, String> cell = new TableCell<Author, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText((item == null || empty) ? null : item.toString());
						setGraphic(null);
					}
				};

				cell.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2) {
						Author author = (Author) authorTable.getItems()
								.get(((TableCell<Author, String>) event.getSource()).getIndex());
						myController.loadScreen(Screen.PRINT_CHECKOUT_RECORD, Screen.PRINT_CHECKOUT_RECORD.getValue());
						PrintCheckoutRecordController printCheckoutRecordController = (PrintCheckoutRecordController) ControlledScreen.controllerList
								.get(Screen.PRINT_CHECKOUT_RECORD);
						printCheckoutRecordController.loadCheckoutRecordForMember(String.valueOf(author.getId()), true);
						myController.setScreen(Screen.PRINT_CHECKOUT_RECORD);
					}
				});

				return cell;
			}
		});
	}

	private void openDetailMemberStage(int authorId) {
		myController.loadScreen(Screen.AUTHOR, Screen.AUTHOR.getValue());
		AuthorController authorController = (AuthorController) ControlledScreen.controllerList.get(Screen.AUTHOR);
		authorController.setFunctionType(FunctionType.UPDATE);
		authorController.setAuthorId(authorId);
		myController.setScreen(Screen.AUTHOR);
		authorController.repaint();
	}

	private void bindProperties() {
		authorId.setCellValueFactory(cell -> new SimpleObjectProperty<Integer>(cell.getValue().getId()));
		firstName.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getFirstName()));
		lastName.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getLastName()));
		street.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getAddress().getStreet()));
		city.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getAddress().getCity()));
		zip.setCellValueFactory(cell -> new SimpleObjectProperty<Integer>(cell.getValue().getAddress().getZip()));
		state.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getAddress().getState()));
		phone.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getPhone()));
		bio.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getBio()));
	}

	/**
	 * Repaint the layout
	 */
	public void repaint() {
		lblStatus.setText("");
		txtSearch.clear();
		// buildData();
	}

	private void buildData() {
		libraryAuthorList = FXCollections.observableArrayList();
		List<Author> authorList = authorService.getList();

		if (authorList != null && !authorList.isEmpty()) {
			libraryAuthorList.addAll(authorList);
		}
		authorTable.setItems(libraryAuthorList);
	}

	@FXML
	public void returnHome() {
		myController.setScreen(Screen.HOME);
	}

	@FXML
	public void addMember() {
		myController.loadScreen(Screen.MEMBER, Screen.MEMBER.getValue());
		AuthorController memberController = (AuthorController) ControlledScreen.controllerList.get(Screen.MEMBER);
		memberController.setFunctionType(FunctionType.ADD);
		myController.setScreen(Screen.MEMBER);
		memberController.repaint();
	}

	@FXML
	public void searchMember() {
		String authorId = txtSearch.getText();
		if ("".equals(authorId.trim())) {
			buildData();
		} else {
			libraryAuthorList.clear();
			Author author = authorService.get(Integer.parseInt(authorId));
			if (author == null) {
				FXUtil.showErrorMessage(lblStatus, "No record found with author id: " + txtSearch.getText());
				txtSearch.requestFocus();
			} else {
				lblStatus.setText("");
				libraryAuthorList.add(author);
			}
			authorTable.setItems(libraryAuthorList);
		}
	}

	ScreenController myController;

	@FXML
	Label lblStatus;

	@Override
	public void setScreenParent(ScreenController screenParent) {
		myController = screenParent;
	}
}