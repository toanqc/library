package mpp.library.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.dao.impl.CheckoutDAOFacade;

/**
 * 
 * @author bpham4
 *
 */
public class PrintCheckoutRecordController {
	
	@FXML private TableView<MemberCheckoutRecord> tableView;
	private TextField txtMemberID;
	
	private CheckoutDAOFacade checkoutDAO = new CheckoutDAOFacade();
	ObservableList<MemberCheckoutRecord> listCheckoutRecord;

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
		String memberId = txtMemberID.getText().trim();
		listCheckoutRecord = checkoutDAO.printCheckoutRecord(memberId);
		// display in table view
		tableView.setItems(listCheckoutRecord);
	}
	
	@FXML
	protected void gotoMainScreen(MouseEvent event) {
		System.out.println("Goto MainScreen");
	}
}
