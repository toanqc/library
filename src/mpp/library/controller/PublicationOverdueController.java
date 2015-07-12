package mpp.library.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mpp.library.model.Author;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.LibraryMember;
import mpp.library.model.PublicationOverdueRecord;
import mpp.library.model.service.CheckoutService;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.impl.CheckoutServiceImpl;
import mpp.library.model.service.impl.MemberServiceImpl;
import mpp.library.util.LambdaLibrary;
import mpp.library.view.ControlledScreen;
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

	private MemberService memberService;
	private CheckoutService checkoutService;
	private ScreenController myController;

	public PublicationOverdueController() {
		memberService = new MemberServiceImpl();
		checkoutService = new CheckoutServiceImpl();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<LibraryMember> libraryMembers = memberService.getList();
		List<PublicationOverdueRecord> publicationOverdueRecords = new ArrayList<>();

		for (LibraryMember libraryMember : libraryMembers) {
			List<CheckoutRecordEntry> checkoutRecordEntries = checkoutService
					.getOverdueCheckoutRecordEntryByMemberId(libraryMember.getId());

			publicationOverdueRecords = LambdaLibrary.publicationOverdueRecordLambda.apply(checkoutRecordEntries,
					libraryMember);
		}

		ObservableList<PublicationOverdueRecord> listData = FXCollections
				.observableArrayList(publicationOverdueRecords);
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
		myController.setScreen(Screen.HOME);
	}

}
