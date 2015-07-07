package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mpp.library.model.Address;
import mpp.library.model.LibraryMember;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.impl.MemberServiceImpl;
import mpp.library.view.ControlledScreen;
import mpp.library.view.FormValidation;
import mpp.library.view.Screen;
import mpp.library.view.ScreenController;

/**
 * Controller for library member screen
 * 
 * @author Toan Quach
 *
 */
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

	private MemberService memberService;

	public MemberController() {
		memberService = new MemberServiceImpl();
	}

	@FXML
	private void initialize() {
		initializeTextLimiter();
		initalizeNumericLimiter();
		builData();
	}

	public void repaint() {
		clearTextField();
		builData();
	}

	private void builData() {
		if (FunctionType.UPDATE.equals(functionType)) {
			loadData();
		} else if (FunctionType.ADD.equals(functionType)) {
			setGenerateMemberId();
		}
	}

	private void clearTextField() {
		txtFirstName.clear();
		txtLastName.clear();
		txtStreet.clear();
		txtCity.clear();
		txtState.clear();
		txtZip.clear();
		txtPhone.clear();
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

	private void setGenerateMemberId() {
		memberId = memberService.generateId();
		txtMemberId.setText(String.valueOf(memberId));
	}

	private void loadData() {
		LibraryMember libraryMember = memberService.get(String.valueOf(memberId));
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
		myController.setScreen(Screen.HOME);
		myController.setSize(Screen.HOME.getWidth(), Screen.HOME.getHeight());
	}

	@FXML
	public void saveMember() {
		if (!validation()) {
			return;
		}
		Address address = new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(),
				Integer.parseInt(txtZip.getText()));
		LibraryMember member = new LibraryMember(memberId, txtFirstName.getText(), txtLastName.getText(),
				txtPhone.getText(), address);

		if (FunctionType.ADD.equals(functionType)) {
			memberService.saveMember(member);
			InformationDialog.showInformation("Member Manamgement",
					"Library member was created, your id is: " + member.getMemberId());
		} else if (FunctionType.UPDATE.equals(functionType)) {
			memberService.updateMember(member);
			InformationDialog.showInformation("Member Manamgement",
					"Member id " + member.getMemberId() + " was updated successfully");
		}
		MemberListController memberListController = (MemberListController) ControlledScreen.controllerList
				.get(Screen.MEMBER_LIST);
		myController.setScreen(Screen.MEMBER_LIST);
		myController.setSize(Screen.MEMBER_LIST.getWidth(), Screen.MEMBER_LIST.getHeight());
		memberListController.repaint();
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
		if (FormValidation.isEmpty(txtFirstName) || FormValidation.isEmpty(txtLastName)
				|| FormValidation.isEmpty(txtStreet) || FormValidation.isEmpty(txtCity)
				|| FormValidation.isEmpty(txtState) || FormValidation.isEmpty(txtZip)
				|| FormValidation.isEmpty(txtPhone)) {
			ValidationDialog.showWarning("All fields are mandatory!");
			return false;
		}

		if (!FormValidation.isNumberAndExactLength(txtZip, 5)) {
			ValidationDialog.showWarning("Zip code must be numeric with exactly 5 digits!");
			txtZip.requestFocus();
			return false;
		}

		if (!FormValidation.isNumberAndExactLength(txtPhone, 10)) {
			ValidationDialog.showWarning("Phone number must be numeric with exactly 10 digits!");
			txtZip.requestFocus();
			return false;
		}

		return true;
	}

	@FXML
	public void handleCancel() {
		myController.setScreen(Screen.MEMBER_LIST);
		myController.setSize(Screen.MEMBER_LIST.getWidth(), Screen.MEMBER_LIST.getHeight());
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
