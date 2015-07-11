package mpp.library.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.service.impl.PrintCheckoutServiceImpl;
import mpp.library.util.FXUtil;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;
import javafx.scene.image.ImageView;

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
	@FXML private Button btnCheckout;
	@FXML private Button btnSearch;
	@FXML private Button btnHome;

	List<MemberCheckoutRecord> listCheckoutRecord;
	private PrintCheckoutServiceImpl printCheckoutService; 

	ScreenController myController;
	private boolean fromLibraryList = false;
	@FXML ImageView iconSearch;
	@FXML ImageView iconPrint;
	@FXML Label lblStatus;

	@FXML
	protected void printCheckoutRecord(MouseEvent event) {
		try {
			String memID = txtMemberID.getText().trim();
			listCheckoutRecord = printCheckoutService.search(memID);
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
		} catch (Exception e) {
			lblMessage.setText(e.getMessage());
			lblMessage.setVisible(true);
		}
	}

	@FXML
	protected void search(MouseEvent event) {
		if (validateData()) {
			lblMessage.setText("");
			lblMessage.setVisible(false);
			try {
				search(txtMemberID.getText().trim());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				FXUtil.showErrorMessage(lblMessage, e.getMessage());
			}
		}
	}
	
	@FXML
	protected void gotoMainScreen(MouseEvent event) {
		if (!fromLibraryList) {
			myController.setScreen(Screen.HOME);
			myController.setSize(Screen.HOME.getWidth(), Screen.HOME.getHeight());
			repaint();
		}
		// return to the Member List screen
		else {
			myController.setScreen(Screen.MEMBER_LIST);
			myController.setSize(Screen.MEMBER_LIST.getWidth(), Screen.MEMBER_LIST.getHeight());
			repaint();
		}
	}
	
	@FXML
	protected void onEnter(ActionEvent event) {
		if (validateData()) {
			lblMessage.setText("");
			lblMessage.setVisible(false);
			try {
				search(txtMemberID.getText().trim());
			} catch (Exception e) {
				FXUtil.showErrorMessage(lblMessage, e.getMessage());
			}
		}
	}
	
	private void search(String memberId) throws Exception {
		// display in table view
		ObservableList<MemberCheckoutRecord> listData = FXCollections.observableArrayList(printCheckoutService.search(memberId));
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
		bindProperties();
		printCheckoutService =  new PrintCheckoutServiceImpl();
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}

	@Override
	public void repaint() {
		fromLibraryList = false;
		btnCheckout.setVisible(true);
		iconPrint.setVisible(true);
		btnSearch.setVisible(true);
		iconPrint.setVisible(true);
		btnHome.setText("Home");
		txtMemberID.setText("");
		txtMemberID.setEditable(true);
		txtMemberID.getStyleClass().remove("disable");
	}
	
	private boolean validateData() {
		if (FormValidation.isEmpty(txtMemberID)) {
			FXUtil.showErrorMessage(lblMessage, "Member ID must not be non-empty");
			return false;
		}
		else if (!FormValidation.isNumber(txtMemberID)) {
			FXUtil.showErrorMessage(lblMessage, "Member ID must be numeric");
			return false;
		}
		return true;
	}
	
	/**
	 * This method will be called from the screen library member list
	 * @param memberId
	 */
	public void loadCheckoutRecordForMember(String memberId, boolean fromLibraryList) {
		this.fromLibraryList = fromLibraryList;
		if (fromLibraryList) {
			txtMemberID.setText(memberId);
			txtMemberID.setEditable(false);
			txtMemberID.getStyleClass().add("disable");
			try {
				search(memberId);
				paintScreenForLibraryList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void paintScreenForLibraryList() {
		btnCheckout.setVisible(false);
		iconPrint.setVisible(false);
		btnSearch.setVisible(false);
		iconSearch.setVisible(false);
		btnHome.setText("Back");
	}
}
