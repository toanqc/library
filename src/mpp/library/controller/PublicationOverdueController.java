package mpp.library.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mpp.library.model.Author;
import mpp.library.model.PublicationOverdueRecord;
import mpp.library.model.service.OverdueCalculator;
import mpp.library.model.service.impl.PublicationOverdueImpl;
import mpp.library.util.LambdaLibrary;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * @author Anil
 * 
 *
 */
public class PublicationOverdueController implements Initializable, ControlledScreen {

	@FXML
	private TableView<PublicationOverdueRecord> tableView;

	@FXML
	private TextField txtMemberID;

	@FXML
	private TableColumn<PublicationOverdueRecord, String> isbnColumn;

	@FXML
	private TableColumn<PublicationOverdueRecord, String> memberColumn;

	@FXML
	private TableColumn<PublicationOverdueRecord, String> typeColumn;

	@FXML
	private TableColumn<PublicationOverdueRecord, String> checkoutDateColumn;

	@FXML
	private TableColumn<PublicationOverdueRecord, String> duedateColumn;

	@FXML
	ListView<Author> authorCopyPublicationListView;

	@FXML
	private TextField publicationSearchField;

	private ScreenController myController;
	private OverdueCalculator<PublicationOverdueRecord> publicationOverdueCalculator;
	private static List<PublicationOverdueRecord> publicationOverdueRecords;

	@FXML
	TextField txtSearch;

	public PublicationOverdueController() {
		publicationOverdueCalculator = new PublicationOverdueImpl();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FormValidation.addLengthLimiter(publicationSearchField, 30);
		publicationOverdueRecords = publicationOverdueCalculator.getOverdueRecords();
		setPublicationOverdueListView(publicationOverdueRecords);
	}

	private void setPublicationOverdueListView(List<PublicationOverdueRecord> publicationOverdueRecList) {
		ObservableList<PublicationOverdueRecord> listData = FXCollections
				.observableArrayList(publicationOverdueRecList);
		tableView.setItems(listData);

		isbnColumn.setCellValueFactory(cellData -> cellData.getValue().issueNoProperty());
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().typePropery());
		checkoutDateColumn.setCellValueFactory(cellData -> cellData.getValue().chkoutDatePropery());
		duedateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDatePropery());
		memberColumn.setCellValueFactory(cellData -> cellData.getValue().memberPropery());
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
		publicationSearchField.setText("");
		setPublicationOverdueListView(publicationOverdueRecords);
		myController.setScreen(Screen.HOME);
	}

	@FXML
	public void searchPublicationOverdue(ActionEvent actionEvent) {
		List<PublicationOverdueRecord> filteredPublicationOverdueRecords = new ArrayList<>();

		if (publicationSearchField.getText().trim().isEmpty()) {
			setPublicationOverdueListView(publicationOverdueRecords);
			return;
		}

		if (!publicationOverdueRecords.isEmpty()) {
			filteredPublicationOverdueRecords = LambdaLibrary.FILTER_PUBLICATION_OVERDUE_RECORD
					.apply(publicationOverdueRecords, publicationSearchField.getText().trim());
		}
		setPublicationOverdueListView(filteredPublicationOverdueRecords);
	}
}
