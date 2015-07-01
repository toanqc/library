package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mpp.library.model.Address;
import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;
import mpp.library.util.LibraryConstant;
import mpp.library.view.FormValidation;

public class MemberController {

	@FXML
	Button btnHome;
	@FXML
	Label lblStatus;
	@FXML
	TextField txtMemberId;
	@FXML
	TextField txtFirstName;
	@FXML
	TextField txtLastName;
	@FXML
	TextField txtCity;
	@FXML
	TextField txtStreet;
	@FXML
	TextField txtState;
	@FXML
	TextField txtZip;
	@FXML
	TextField txtPhone;

	private MemberDAO memberDAO;

	@FXML
	public void initialize() {
		initializeTextLimiter();
		initializeStyleClass();
	}

	private void initializeTextLimiter() {
		FormValidation.addLengthLimiter(txtFirstName, 20);
		FormValidation.addLengthLimiter(txtLastName, 20);
		FormValidation.addLengthLimiter(txtStreet, 50);
		FormValidation.addLengthLimiter(txtCity, 20);
		FormValidation.addLengthLimiter(txtState, 20);
		FormValidation.addLengthLimiter(txtZip, 5);
		FormValidation.addLengthLimiter(txtPhone, 10);
	}

	private void initializeStyleClass() {
		txtFirstName.getStyleClass().add(LibraryConstant.STYLE_ERROR);
		txtLastName.getStyleClass().add(LibraryConstant.STYLE_ERROR);
		txtStreet.getStyleClass().add(LibraryConstant.STYLE_ERROR);
		txtCity.getStyleClass().add(LibraryConstant.STYLE_ERROR);
		txtState.getStyleClass().add(LibraryConstant.STYLE_ERROR);
		txtZip.getStyleClass().add(LibraryConstant.STYLE_ERROR);
		txtPhone.getStyleClass().add(LibraryConstant.STYLE_ERROR);
	}

	@FXML
	public void returnHome() {
	}

	@FXML
	public void saveMember() {
		validation();
		Address address = new Address(txtStreet.getText(), txtCity.getText(),
				txtState.getText(), Integer.parseInt(txtZip.getText()));
		LibraryMember member = new LibraryMember(txtFirstName.getText(),
				txtLastName.getText(), txtPhone.getText(), address);
		memberDAO.save(member);
	}

	private boolean validation() {
		return false;
	}

	@FXML
	public void handleCancel() {
	}

	@FXML
	public boolean onFirstNameChanged() {
		return FormValidation.isEmpty(txtFirstName);
	}

	@FXML
	public boolean onLastNameChanged() {
		return FormValidation.isEmpty(txtLastName);
	}

	@FXML
	public boolean onCityChanged() {
		return FormValidation.isEmpty(txtCity);
	}

	@FXML
	public boolean onStreetChanged() {
		return FormValidation.isEmpty(txtStreet);
	}

	@FXML
	public boolean onStateChanged() {
		return FormValidation.isEmpty(txtState);
	}

	@FXML
	public boolean onZipChanged() {
		return FormValidation.isEmpty(txtZip);
	}

	@FXML
	public boolean onPhoneChanged() {
		return FormValidation.isEmpty(txtPhone);
	}
}
