package mpp.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mpp.library.model.Address;
import mpp.library.model.Author;
import mpp.library.model.service.AuthorService;
import mpp.library.model.service.impl.AuthorServiceImpl;
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
public class AuthorController implements ControlledScreen {

	@FXML
	Label lblStatus;
	@FXML
	TextField txtAuthorId;
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
	@FXML
	TextArea txtBio;

	private FunctionType functionType;

	private int authorId;

	private int addressId;

	private AuthorService authorService;

	public AuthorController() {
		authorService = new AuthorServiceImpl();
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
		txtBio.clear();
		txtFirstName.requestFocus();
		lblStatus.setText("");
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
		FormValidation.addLengthLimiter(txtPhone, 14);
	}

	private void setGenerateMemberId() {
		authorId = authorService.generateAuthorId();
		txtAuthorId.setText(String.valueOf(authorId));
	}

	private void loadData() {
		Author author = authorService.get(authorId);
		bindData(author);
	}

	private void bindData(Author author) {
		txtAuthorId.setText(String.valueOf(author.getId()));
		txtFirstName.setText(author.getFirstName());
		txtLastName.setText(author.getLastName());
		txtStreet.setText(author.getAddress().getStreet());
		txtCity.setText(author.getAddress().getCity());
		txtState.setText(author.getAddress().getState());
		txtZip.setText(String.valueOf(author.getAddress().getZip()));
		txtPhone.setText(author.getPhone());
		txtBio.setText(author.getBio());
		addressId = author.getAddress().getId();
	}

	@FXML
	public void returnHome() {
		clearTextField();
		myController.setScreen(Screen.HOME);
		AuthorListController authorListController = (AuthorListController) ControlledScreen.controllerList
				.get(Screen.AUTHOR_LIST);
		authorListController.repaint();
	}

	@FXML
	public void saveAuthor() {
		if (!validation()) {
			return;
		}
		Address address = new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(),
				Integer.parseInt(txtZip.getText()));
		address.setId(addressId);
		Author author = new Author(txtFirstName.getText(), txtLastName.getText(), txtPhone.getText(), txtBio.getText(),
				address);
		author.setId(Integer.parseInt(txtAuthorId.getText()));

		if (FunctionType.ADD.equals(functionType)) {
			authorService.saveAuthor(author);
			FXUtil.showSuccessMessage(lblStatus, "Author was created, the id is: " + author.getId());
			clearTextField();
			txtAuthorId.setText(String.valueOf(authorService.generateAuthorId()));
		} else if (FunctionType.UPDATE.equals(functionType)) {
			authorService.updateAuthor(author);
			FXUtil.showSuccessMessage(lblStatus, "Author id " + author.getId() + " was updated successfully");
		}
	}

	public FunctionType getFunctionType() {
		return functionType;
	}

	public void setFunctionType(FunctionType functionType) {
		this.functionType = functionType;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	private boolean validation() {
		if (FormValidation.isEmpty(txtFirstName) || FormValidation.isEmpty(txtLastName)
				|| FormValidation.isEmpty(txtStreet) || FormValidation.isEmpty(txtZip)
				|| FormValidation.isEmpty(txtBio)) {
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
			FXUtil.showErrorMessage(lblStatus, "Incorrect format of phone number, 10 digits or 000-000-0000!");
			txtPhone.requestFocus();
			return false;
		}

		if (!FormValidation.isEnoughLength(txtBio, 10)) {
			FXUtil.showErrorMessage(lblStatus, "Bio must have at least 10 characters");
			txtBio.requestFocus();
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
		myController.setScreen(Screen.AUTHOR_LIST);
		AuthorListController authorListController = (AuthorListController) ControlledScreen.controllerList
				.get(Screen.AUTHOR_LIST);
		authorListController.repaint();
	}

	ScreenController myController;

	@Override
	public void setScreenParent(ScreenController screenParent) {
		myController = screenParent;
	}
}
