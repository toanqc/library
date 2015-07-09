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
import mpp.library.model.LibraryMember;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.impl.MemberServiceImpl;
import mpp.library.util.FXUtil;
import mpp.library.view.ControlledScreen;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

public class MemberListController implements ControlledScreen {

	@FXML
	Button btnHome;

	@FXML
	Button btnAdd;

	@FXML
	TextField txtSearch;

	@FXML
	Button btnSearch;

	@FXML
	TableView<LibraryMember> memberTable;

	@FXML
	TableColumn<LibraryMember, Integer> memberId;

	@FXML
	TableColumn<LibraryMember, String> firstName;

	@FXML
	TableColumn<LibraryMember, String> lastName;

	@FXML
	TableColumn<LibraryMember, String> street;

	@FXML
	TableColumn<LibraryMember, String> city;

	@FXML
	TableColumn<LibraryMember, String> state;

	@FXML
	TableColumn<LibraryMember, Integer> zip;

	@FXML
	TableColumn<LibraryMember, String> phone;

	@FXML
	TableColumn<LibraryMember, String> checkoutRecord;

	private ObservableList<LibraryMember> libraryMemberList;

	private MemberService memberService;

	public MemberListController() {
		memberService = new MemberServiceImpl();
	}

	@FXML
	private void initialize() {
		handleSelectedRow();
		bindProperties();
		buildData();
	}

	private void handleSelectedRow() {
		memberId.setCellFactory(new Callback<TableColumn<LibraryMember, Integer>, TableCell<LibraryMember, Integer>>() {
			@SuppressWarnings("unchecked")
			@Override
			public TableCell<LibraryMember, Integer> call(TableColumn<LibraryMember, Integer> param) {
				TableCell<LibraryMember, Integer> cell = new TableCell<LibraryMember, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						setText((item == null || empty) ? null : item.toString());
						setGraphic(null);
					}
				};
				cell.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2) {
						LibraryMember member = (LibraryMember) memberTable.getItems()
								.get(((TableCell<LibraryMember, Integer>) event.getSource()).getIndex());
						openDetailMemberStage(member.getMemberId());
					}
				});

				return cell;
			}
		});

		checkoutRecord
				.setCellFactory(new Callback<TableColumn<LibraryMember, String>, TableCell<LibraryMember, String>>() {
					@SuppressWarnings("unchecked")
					@Override
					public TableCell<LibraryMember, String> call(TableColumn<LibraryMember, String> param) {

						TableCell<LibraryMember, String> cell = new TableCell<LibraryMember, String>() {
							@Override
							protected void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								setText((item == null || empty) ? null : item.toString());
								setGraphic(null);
							}
						};

						cell.setOnMouseClicked(event -> {
							if (event.getClickCount() == 2) {
								LibraryMember member = (LibraryMember) memberTable.getItems()
										.get(((TableCell<LibraryMember, String>) event.getSource()).getIndex());
								myController.loadScreen(Screen.PRINT_CHECKOUT_RECORD,
										Screen.PRINT_CHECKOUT_RECORD.getValue());
								PrintCheckoutRecordController printCheckoutRecordController = (PrintCheckoutRecordController) ControlledScreen.controllerList
										.get(Screen.PRINT_CHECKOUT_RECORD);
								printCheckoutRecordController
										.loadCheckoutRecordForMember(String.valueOf(member.getMemberId()), true);
								myController.setScreen(Screen.PRINT_CHECKOUT_RECORD);
								myController.setSize(Screen.PRINT_CHECKOUT_RECORD.getWidth(),
										Screen.PRINT_CHECKOUT_RECORD.getHeight());
							}
						});

						return cell;
					}
				});
	}

	private void openDetailMemberStage(int memberId) {
		myController.loadScreen(Screen.MEMBER, Screen.MEMBER.getValue());
		MemberController memberController = (MemberController) ControlledScreen.controllerList.get(Screen.MEMBER);
		memberController.setFunctionType(FunctionType.UPDATE);
		memberController.setMemberId(memberId);
		myController.setScreen(Screen.MEMBER);
		myController.setSize(Screen.MEMBER.getWidth(), Screen.MEMBER.getHeight());
		memberController.repaint();
	}

	private void bindProperties() {
		memberId.setCellValueFactory(cell -> new SimpleObjectProperty<Integer>(cell.getValue().getMemberId()));
		firstName.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getFirstName()));
		lastName.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getLastName()));
		street.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getAddress().getStreet()));
		city.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getAddress().getCity()));
		zip.setCellValueFactory(cell -> new SimpleObjectProperty<Integer>(cell.getValue().getAddress().getZip()));
		state.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getAddress().getState()));
		phone.setCellValueFactory(cell -> new SimpleObjectProperty<String>(cell.getValue().getPhone()));
		checkoutRecord.setCellValueFactory(cell -> new ReadOnlyStringWrapper("..."));
	}

	/**
	 * Repaint the layout
	 */
	public void repaint() {
		lblStatus.setText("");
		txtSearch.clear();
		buildData();
	}

	private void buildData() {
		libraryMemberList = FXCollections.observableArrayList();
		List<LibraryMember> memberList = memberService.getList();

		if (memberList != null && !memberList.isEmpty()) {
			libraryMemberList.addAll(memberList);
		}
		memberTable.setItems(libraryMemberList);
	}

	@FXML
	public void returnHome() {
		myController.setScreen(Screen.HOME);
		myController.setSize(Screen.HOME.getWidth(), Screen.HOME.getHeight());
	}

	@FXML
	public void addMember() {
		myController.loadScreen(Screen.MEMBER, Screen.MEMBER.getValue());
		MemberController memberController = (MemberController) ControlledScreen.controllerList.get(Screen.MEMBER);
		memberController.setFunctionType(FunctionType.ADD);
		myController.setScreen(Screen.MEMBER);
		myController.setSize(Screen.MEMBER.getWidth(), Screen.MEMBER.getHeight());
		memberController.repaint();
	}

	@FXML
	public void searchMember() {
		String searchText = txtSearch.getText();
		if ("".equals(searchText.trim())) {
			buildData();
		} else {
			libraryMemberList.clear();
			LibraryMember libraryMember = memberService.get(searchText);
			if (libraryMember == null) {
				FXUtil.showErrorMessage(lblStatus, "No record found with member id: " + txtSearch.getText());
				txtSearch.requestFocus();
			} else {
				lblStatus.setText("");
				libraryMemberList.add(libraryMember);
			}
			memberTable.setItems(libraryMemberList);
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