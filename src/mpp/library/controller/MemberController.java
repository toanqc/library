package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mpp.library.model.Address;
import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.impl.MemberDAOImpl;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.ScreenController;
import mpp.library.view.Screen;

public class MemberController implements ControlledScreen {

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

	private FunctionType functionType;

	private int memberId;

	private MemberDAO memberDAO;

	@FXML
	public void initialize() {
		initializeTextLimiter();
		initalizeNumericLimiter();
	}

	private void initalizeNumericLimiter() {
		FormValidation.addNumbericLimiter(txtZip);
		FormValidation.addNumbericLimiter(txtPhone);
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

	public void transferData(FunctionType functionType, int memberId) {
		this.functionType = functionType;
		this.memberId = memberId;

		if (FunctionType.UPDATE.equals(functionType)) {
			loadData();
		} else if (FunctionType.ADD.equals(functionType)) {
			setGenerateMemberId();
		}
	}

	private void setGenerateMemberId() {
		memberDAO = new MemberDAOImpl();
		memberId = memberDAO.generateId();
		txtMemberId.setText(String.valueOf(memberId));
	}

	private void loadData() {
		memberDAO = new MemberDAOImpl();
		LibraryMember libraryMember = memberDAO.get(String.valueOf(memberId));
		bindData(libraryMember);
	}

	private void bindData(LibraryMember libraryMember) {
		txtMemberId.setText(String.valueOf(libraryMember.getMemberId()));
		txtFirstName.setText(libraryMember.getFirstName());
		txtLastName.setText(libraryMember.getLastName());
		txtStreet.setText(libraryMember.getAddress().getStreet());
		txtCity.setText(libraryMember.getAddress().getCity());
		txtState.setText(libraryMember.getAddress().getState());
		txtZip.setText(String.valueOf(libraryMember.getAddress().getZip()));
		txtPhone.setText(libraryMember.getPhone());
	}

	@FXML
	public void returnHome() {
	}

	@FXML
	public void saveMember() {
		if (!validation()) {
			return;
		}
		Address address = new Address(txtStreet.getText(), txtCity.getText(),
				txtState.getText(), Integer.parseInt(txtZip.getText()));
		LibraryMember member = new LibraryMember(memberId,
				txtFirstName.getText(), txtLastName.getText(),
				txtPhone.getText(), address);

		if (FunctionType.ADD.equals(functionType)) {
			memberDAO.save(member);
			InformationDialog.showInformation(
					"Member Manamgement",
					"Library member was created, your id is: "
							+ member.getMemberId());
		} else if (FunctionType.UPDATE.equals(functionType)) {
			memberDAO.update(member);
			InformationDialog.showInformation("Member Manamgement",
					"Member id " + member.getMemberId()
							+ " was updated successfully");
		}
	}

	public FunctionType getFunctionType() {
		return functionType;
	}

	public void setFunctionType(FunctionType functionType) {
		this.functionType = functionType;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	private boolean validation() {
		if (FormValidation.isEmpty(txtFirstName)
				|| FormValidation.isEmpty(txtLastName)
				|| FormValidation.isEmpty(txtStreet)
				|| FormValidation.isEmpty(txtCity)
				|| FormValidation.isEmpty(txtState)
				|| FormValidation.isEmpty(txtZip)
				|| FormValidation.isEmpty(txtPhone)) {
			ValidationDialog.showWarning("All fields are mandatory!");
			return false;
		}

		if (!FormValidation.isNumberAndExactLength(txtZip, 5)) {
			ValidationDialog
					.showWarning("Zip code must be numeric with exactly 5 digits!");
			txtZip.requestFocus();
			return false;
		}

		if (!FormValidation.isNumberAndExactLength(txtPhone, 10)) {
			ValidationDialog
					.showWarning("Phone number must be numeric with exactly 10 digits!");
			txtZip.requestFocus();
			return false;
		}

		return true;
	}

	@FXML
	public void handleCancel() {
		myController.setScreen(Screen.MEMBER_LIST);
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

	ScreenController myController;

	@Override
	public void setScreenParent(ScreenController screenParent) {
		myController = screenParent;
	}
}
