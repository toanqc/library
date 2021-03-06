package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mpp.library.model.Address;
import mpp.library.model.LibraryMember;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.impl.MemberServiceImpl;
import mpp.library.util.FXUtil;
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

	private String memberId;

	private int addressId;

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
		lblStatus.setText("");
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
		txtFirstName.requestFocus();
	}

	private void initalizeNumericLimiter() {
		FormValidation.addNumbericLimiter(txtZip);
	}

	private void initializeTextLimiter() {
		FormValidation.addLengthLimiter(txtFirstName, 20);
		FormValidation.addLengthLimiter(txtLastName, 20);
		FormValidation.addLengthLimiter(txtStreet, 50);
		FormValidation.addLengthLimiter(txtCity, 20);
		FormValidation.addLengthLimiter(txtState, 2);
		FormValidation.addLengthLimiter(txtZip, 5);
		FormValidation.addLengthLimiter(txtPhone, 12);
	}

	private void setGenerateMemberId() {
		memberId = memberService.generateMemberId();
		txtMemberId.setText(memberId);
	}

	private void loadData() {
		LibraryMember libraryMember = memberService.getByMemberId(memberId);
		bindData(libraryMember);
	}

	private void bindData(LibraryMember libraryMember) {
		txtMemberId.setText(libraryMember.getMemberId());
		txtFirstName.setText(libraryMember.getFirstName());
		txtLastName.setText(libraryMember.getLastName());
		txtStreet.setText(libraryMember.getAddress().getStreet());
		txtCity.setText(libraryMember.getAddress().getCity());
		txtState.setText(libraryMember.getAddress().getState());
		txtZip.setText(String.valueOf(libraryMember.getAddress().getZip()));
		txtPhone.setText(libraryMember.getPhone());
		addressId = libraryMember.getAddress().getId();
	}

	@FXML
	public void returnHome() {
		clearTextField();
		lblStatus.setText("");
		myController.setScreen(Screen.HOME);
		MemberListController memberListController = (MemberListController) ControlledScreen.controllerList
				.get(Screen.MEMBER_LIST);
		memberListController.repaint();
	}

	@FXML
	public void saveMember() {
		if (!validation()) {
			return;
		}
		Address address = new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(),
				Integer.parseInt(txtZip.getText()));
		address.setId(addressId);
		LibraryMember member = new LibraryMember(memberId, txtFirstName.getText(), txtLastName.getText(),
				txtPhone.getText(), address);

		if (FunctionType.ADD.equals(functionType)) {
			memberService.saveMember(member);
			FXUtil.showSuccessMessage(lblStatus, "Library member was created, id is: " + member.getMemberId());
			clearTextField();
			txtMemberId.setText(memberService.generateMemberId());
		} else if (FunctionType.UPDATE.equals(functionType)) {
			memberService.updateMember(member);
			FXUtil.showSuccessMessage(lblStatus, "Member id " + member.getMemberId() + " was updated successfully");
		}
	}

	public FunctionType getFunctionType() {
		return functionType;
	}

	public void setFunctionType(FunctionType functionType) {
		this.functionType = functionType;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	private boolean validation() {
		if (FormValidation.isEmpty(txtFirstName) || FormValidation.isEmpty(txtLastName)
				|| FormValidation.isEmpty(txtStreet) || FormValidation.isEmpty(txtZip)) {
			FXUtil.showErrorMessage(lblStatus, "(*) fields are required. Please input!");
			return false;
		}

		if (!FormValidation.isEmpty(txtState) && !FormValidation.isCharacter(txtState, 2)) {
			FXUtil.showErrorMessage(lblStatus, "State must have exaclty two characters in the range A-Z!");
			txtState.requestFocus();
			return false;
		}

		if (!FormValidation.isNumberAndExactLength(txtZip, 5)) {
			FXUtil.showErrorMessage(lblStatus, "Zip code must be numeric with exactly 5 digits!");
			txtZip.requestFocus();
			return false;
		}

		if (!FormValidation.isEmpty(txtPhone) && !FormValidation.isCorrectPhone(txtPhone)) {
			FXUtil.showErrorMessage(lblStatus, "Incorrect phone format, 10 digits or 000-000-0000!");
			txtPhone.requestFocus();
			return false;
		}

		return true;
	}

	@FXML
	public void clearFields() {
		clearTextField();
	}

	@FXML
	public void handleCancel() {
		myController.setScreen(Screen.MEMBER_LIST);
		MemberListController memberListController = (MemberListController) ControlledScreen.controllerList
				.get(Screen.MEMBER_LIST);
		memberListController.repaint();
	}

	ScreenController myController;

	@Override
	public void setScreenParent(ScreenController screenParent) {
		myController = screenParent;
	}
}
