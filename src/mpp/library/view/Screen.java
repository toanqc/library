package mpp.library.view;

/**
 * Enum of all screen list of the system
 * 
 * @author Toan Quach
 */
public enum Screen {
	HOME("home.fxml", 800, 600),
	MEMBER_LIST("member/memberList.fxml", 800, 600),
	MEMBER("member/member.fxml", 800, 600),
	AUTHOR_LIST("author/authorList.fxml", 800, 600),
	AUTHOR("author/author.fxml", 800, 600),
	PUBLICATION("publication/addPublication.fxml",  800, 600),
	COPY_PUBLICATION("publication/addCopyPublication.fxml",  800, 600),
	CHECKOUT("checkout/checkout.fxml", 800, 600),
	PUBLICATION_OVERDUE("publication/publicationOverdue.fxml", 800, 600),
	PRINT_CHECKOUT_RECORD("checkout/printCheckoutRecord.fxml", 800, 600);

	String fxmlFile;
	int height;
	int width;

	Screen(String fxmlFile, int width, int height) {
		this.fxmlFile = fxmlFile;
		this.width = width;
		this.height = height;
	}

	public String getValue() {
		return this.fxmlFile;
	}
}
