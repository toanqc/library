package mpp.library.controller;

import java.util.List;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import mpp.library.model.LibraryMember;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.impl.MemberServiceImpl;
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
						System.out.println("person" + member.getMemberId());
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
								// TODO: need to transfer to print checkout
								// record screen
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
		memberId.setCellValueFactory(new PropertyValueFactory<LibraryMember, Integer>("memberId"));
		firstName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("lastName"));

		street.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getAddress().getStreet());
					}
				});

		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getAddress().getCity());
					}
				});

		zip.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<LibraryMember, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<LibraryMember, Integer> param) {
						return new SimpleObjectProperty<Integer>(param.getValue().getAddress().getZip());
					}
				});

		state.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getAddress().getState());
					}
				});

		phone.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("phone"));

		checkoutRecord.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("..."));
	}

	/**
	 * Repaint the layout
	 */
	public void repaint() {
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
		if ("".equals(txtSearch.getText().trim())) {
			buildData();
		} else {
			libraryMemberList.clear();
			LibraryMember libraryMember = memberService.get(txtSearch.getText());
			if (libraryMember != null) {
				libraryMemberList.add(libraryMember);
			}
			memberTable.setItems(libraryMemberList);
		}
	}

	ScreenController myController;

	@Override
	public void setScreenParent(ScreenController screenParent) {
		myController = screenParent;
	}
}