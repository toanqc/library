package mpp.library.view;

public enum Screen {
	HOME("home.fxml", 800, 630),
	MEMBER_LIST("member/memberList.fxml", 800, 620),
	MEMBER("member/member.fxml", 530, 400),
	PUBLICATION("",  0, 0),
	CHECKOUT("checkout/Checkout.fxml", 520, 420),
	PUBLICATION_OVERDUE("", 0, 0),
	PRINT_CHECKOUT_RECORD("checkout/PrintCheckoutRecord.fxml", 630, 530),
	COPY_PUBLICATION("", 0, 0);

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
