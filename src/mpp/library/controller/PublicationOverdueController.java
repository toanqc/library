package mpp.library.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mpp.library.model.Book;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.LibraryMember;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationOverdueRecord;
import mpp.library.model.PublicationType;
import mpp.library.model.dao.db.impl.CheckoutRecordEntryDAODBFacade;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.impl.MemberServiceImpl;
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

	private MemberService memberService;
	private CheckoutRecordEntryDAODBFacade recordEntryDAODBFacade;

	private ScreenController myController;

	public PublicationOverdueController() {
		memberService = new MemberServiceImpl();
		recordEntryDAODBFacade = new CheckoutRecordEntryDAODBFacade();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<LibraryMember> libraryMembers = memberService.getList();
		List<PublicationOverdueRecord> publicationOverdueRecords = new ArrayList<>();
		PublicationOverdueRecord publicationOverdueRecord = null;

		for (LibraryMember libraryMember : libraryMembers) {
			List<CheckoutRecordEntry> checkoutRecordEntries = recordEntryDAODBFacade
					.getCheckoutEnteriesOfMemeber(libraryMember.getId());

			for (CheckoutRecordEntry crEntry : checkoutRecordEntries) {

				if (crEntry.getDueDate().compareTo(LocalDate.now()) < 0) {
					publicationOverdueRecord = new PublicationOverdueRecord();
					Publication publication = crEntry.getCopy().getPublication();

					if (publication instanceof Book) {
						publicationOverdueRecord.setIssueNo(((Book) publication).getISBN());
						publicationOverdueRecord.setType(PublicationType.BOOK.getValue());
					} else {
						publicationOverdueRecord.setIssueNo(((Periodical) publication).getIssueNumber());
						publicationOverdueRecord.setType(PublicationType.PERIODICAL.getValue());
					}

					publicationOverdueRecord.setChkoutDate(crEntry.getCheckoutDate());
					publicationOverdueRecord.setDueDate(crEntry.getDueDate());
					publicationOverdueRecord
							.setMember(libraryMember.getFirstName() + " " + libraryMember.getLastName());
				}
				publicationOverdueRecords.add(publicationOverdueRecord);
			}

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
		myController.setSize(Screen.HOME.getWidth(), Screen.HOME.getHeight());
	}

}
