package mpp.library.view;

public enum Screen {
	HOME("home.fxml", 800, 630),
	MEMBER_LIST("member/memberList.fxml", 800, 620),
	MEMBER("member/member.fxml", 530, 400),
	PUBLICATION("publication/addPublication.fxml",  800, 600),
	COPY_PUBLICATION("publication/addCopyPublication.fxml",  800, 600),
	CHECKOUT("checkout/Checkout.fxml", 520, 420),
	PUBLICATION_OVERDUE("publication/publicationOverdue.fxml", 800, 600),
	PRINT_CHECKOUT_RECORD("checkout/PrintCheckoutRecord.fxml", 630, 530);

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

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
