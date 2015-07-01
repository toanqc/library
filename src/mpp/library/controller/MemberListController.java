package mpp.library.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.impl.MemberDAOImpl;
import mpp.library.view.Main;

public class MemberListController {

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
	TableColumn<LibraryMember, CheckoutRecord> checkoutRecord;

	private ObservableList<LibraryMember> libraryMemberList;

	private MemberDAO memberDAO = null;

	@FXML
	public void initialize() {
		bindProperties();
		buildData();
		handleSelectedRow();
	}

	private void handleSelectedRow() {
		memberTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					openDetailMemberStage(memberTable.getSelectionModel()
							.getSelectedItem().getMemberId());
				}
			}
		});
	}

	private void openDetailMemberStage(int memberId) {
		Main.showMemberStage(memberId);
	}

	private void bindProperties() {
		memberId.setCellValueFactory(new PropertyValueFactory<LibraryMember, Integer>(
				"memberId"));
		firstName
				.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>(
						"firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>(
				"lastName"));

		street.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<LibraryMember, String> param) {
				return new SimpleObjectProperty<String>(param.getValue()
						.getAddress().getStreet());
			}
		});

		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<LibraryMember, String> param) {
				return new SimpleObjectProperty<String>(param.getValue()
						.getAddress().getCity());
			}
		});

		zip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibraryMember, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(
					CellDataFeatures<LibraryMember, Integer> param) {
				return new SimpleObjectProperty<Integer>(param.getValue()
						.getAddress().getZip());
			}
		});

		state.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<LibraryMember, String> param) {
				return new SimpleObjectProperty<String>(param.getValue()
						.getAddress().getState());
			}
		});

		phone.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>(
				"phone"));

		checkoutRecord
				.setCellValueFactory(new PropertyValueFactory<LibraryMember, CheckoutRecord>(
						"checkoutRecord"));
	}

	private void buildData() {
		memberDAO = new MemberDAOImpl();
		libraryMemberList = FXCollections.observableArrayList();
		libraryMemberList.addAll(memberDAO.getList());
		memberTable.setItems(libraryMemberList);
	}

	@FXML
	public void returnHome() {
	}

	@FXML
	public void addMember() {
		Main.showMemberStage();
	}

	@FXML
	public void searchMember() {
		if ("".equals(txtSearch.getText().trim())) {
			buildData();
		} else {
			libraryMemberList.clear();
			memberDAO = new MemberDAOImpl();
			LibraryMember libraryMember = memberDAO.get(txtSearch.getText());
			if (libraryMember != null) {
				libraryMemberList.add(libraryMember);
			}
			memberTable.setItems(libraryMemberList);
		}
	}

	@FXML
	public void test() {
		System.out.println("test");
	}
}