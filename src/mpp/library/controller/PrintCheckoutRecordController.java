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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.dao.impl.CheckoutDAOFacade;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * 
 * @author bpham4
 *
 */
public class PrintCheckoutRecordController implements Initializable, ControlledScreen {
	
	@FXML private TableView<MemberCheckoutRecord> tableView;
	@FXML private TextField txtMemberID;
	@FXML private TableColumn<MemberCheckoutRecord, String> ISBNTC;
	@FXML private TableColumn<MemberCheckoutRecord, String> titleTC;
	@FXML private TableColumn<MemberCheckoutRecord, String> typeTC;
	@FXML private TableColumn<MemberCheckoutRecord, String> chkoutDateTC;
	@FXML private TableColumn<MemberCheckoutRecord, String> dueDateTC;
	@FXML private Label lblMessage;
	
	private CheckoutDAOFacade checkoutDAO = new CheckoutDAOFacade();
	List<MemberCheckoutRecord> listCheckoutRecord;

	ScreenController myController;

	@FXML
	protected void printCheckoutRecord(MouseEvent event) {
		if (listCheckoutRecord != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("ISBN/IssueNo\t");
			sb.append("Title\t");
			sb.append("Type\t\t");
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
		if (validateData()) {
			lblMessage.setText("");
			lblMessage.setVisible(false);
			search();
		}
	}
	
	@FXML
	protected void gotoMainScreen(MouseEvent event) {
		myController.setScreen(Screen.HOME);
		myController.setSize(Screen.HOME.getWidth(), Screen.HOME.getHeight());
	}
	
	@FXML
	protected void onEnter(ActionEvent event) {
		if (validateData()) {
			lblMessage.setText("");
			lblMessage.setVisible(false);
			search();
		}
	}
	
	private void search() {
		String memberId = txtMemberID.getText().trim();
		listCheckoutRecord = checkoutDAO.printCheckoutRecord(memberId);
		// display in table view
		ObservableList<MemberCheckoutRecord> listData = FXCollections.observableArrayList(listCheckoutRecord);
		tableView.setItems(listData);
	}
	
	private void bindProperties() {
		ISBNTC.setCellValueFactory(cellData -> cellData.getValue().issueNoProperty());
		titleTC.setCellValueFactory(cellData -> cellData.getValue().titlePropery());
		typeTC.setCellValueFactory(cellData -> cellData.getValue().typePropery());
		chkoutDateTC.setCellValueFactory(cellData -> cellData.getValue().chkoutDatePropery());
		dueDateTC.setCellValueFactory(cellData -> cellData.getValue().dueDatePropery());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		bindProperties();
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean validateData() {
		if (FormValidation.isEmpty(txtMemberID)) {
			lblMessage.setText("Member ID must not be non-empty");
			lblMessage.setVisible(true);
			return false;
		}
		else if (!FormValidation.isNumber(txtMemberID)) {
			lblMessage.setText("Member ID must be numeric");
			lblMessage.setVisible(true);
			return false;
		}
		return true;
	}
}
