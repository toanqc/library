package mpp.library.controller;

import java.net.URL;
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
import javafx.scene.input.MouseEvent;
import mpp.library.model.Book;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.LibraryMember;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationOverdueRecord;
import mpp.library.model.PublicationType;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.impl.MemberDAOImpl;

/**
 * @author Anil
 * 
 *
 */
public class PublicationOverdueController implements Initializable {

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

	private MemberDAO memberDao;

	public PublicationOverdueController() {
		memberDao = new MemberDAOImpl();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<LibraryMember> libraryMembers = memberDao.getList();
		List<PublicationOverdueRecord> publicationOverdueRecords = new ArrayList<>();
		PublicationOverdueRecord publicationOverdueRecord = null;

		for (LibraryMember libraryMember : libraryMembers) {

			for (CheckoutRecordEntry crEntry : libraryMember.getCheckoutRecord().getOverdueCheckoutRecordEntries()) {
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
				publicationOverdueRecord.setDueDate(crEntry.getCheckoutDate());
				publicationOverdueRecord.setMember(libraryMember.getFirstName() + " " + libraryMember.getLastName());
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

	@FXML
	protected void gotoMainScreen(MouseEvent event) {
		System.out.println("Goto MainScreen");
	}

}
