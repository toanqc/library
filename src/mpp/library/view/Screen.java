package mpp.library.view;

public enum Screen {
	HOME("home.fxml", 800, 600),
	MEMBER_LIST("member/memberList.fxml", 800, 600),
	MEMBER("member/member.fxml", 500, 400),
	PUBLICATION("",  0, 0),
	CHECKOUT("checkout/Checkout.fxml", 570, 420),
	PUBLICATION_OVERDUE("", 0, 0),
	PRINT_CHECKOUT_RECORD("checkout/PrintCheckoutRecord.fxml", 650, 530);

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
