package mpp.library.controller;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.dao.impl.CheckoutDAOFacade;

/**
 * 
 * @author bpham4
 *
 */
public class PrintCheckoutRecordController {
	
	@FXML private TableView<MemberCheckoutRecord> tableView;
	@FXML private TextField txtMemberID;
	@FXML private TableColumn<MemberCheckoutRecord, String> ISBNTC;
	@FXML private TableColumn<MemberCheckoutRecord, String> titleTC;
	@FXML private TableColumn<MemberCheckoutRecord, String> typeTC;
	@FXML private TableColumn<MemberCheckoutRecord, LocalDate> chkoutDateTC;
	@FXML private TableColumn<MemberCheckoutRecord, LocalDate> dueDateTC;
	
	private CheckoutDAOFacade checkoutDAO = new CheckoutDAOFacade();
	List<MemberCheckoutRecord> listCheckoutRecord;

	@FXML public void initialize() {
		bindProperties();
	}
	
	@FXML
	protected void printCheckoutRecord(MouseEvent event) {
		if (listCheckoutRecord != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("ISBN/IssueNo\t");
			sb.append("Title\t");
			sb.append("Type\t");
			sb.append("Checkout Date\t");
			sb.append("Due Date\t");
			System.out.println(sb.toString());
			for (int i = 0; i < listCheckoutRecord.size(); i++) {
				MemberCheckoutRecord record = listCheckoutRecord.get(i);
				System.out.println(record);
			}
		}
	}

	@FXML
	protected void search(MouseEvent event) {
		System.out.println("Search Member ID");
		search();
	}
	
	@FXML
	protected void gotoMainScreen(MouseEvent event) {
		System.out.println("Goto MainScreen");
	}
	
	@FXML
	protected void onEnter(ActionEvent event) {
		System.out.println("On Enter");
		search();
	}
	
	private void search() {
		String memberId = txtMemberID.getText().trim();
		listCheckoutRecord = checkoutDAO.printCheckoutRecord(memberId);
		// display in table view
		ObservableList<MemberCheckoutRecord> listData = FXCollections.observableArrayList(listCheckoutRecord);
		tableView.setItems(listData);
	}
	
	private void bindProperties() {
		ISBNTC.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
		typeTC.setCellValueFactory(new PropertyValueFactory<>("publicationType"));
		chkoutDateTC.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
		dueDateTC.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		ISBNTC.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
	}
}
