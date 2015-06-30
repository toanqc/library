package mpp.library.model;

public class LibraryMember extends Person {

	private static final long serialVersionUID = 1373745594447872262L;

	private int memberId;
	private CheckoutRecord checkoutRecord;

	public LibraryMember(String firstName, String lastName, String phone, Address address) {
		super(firstName, lastName, phone, address);
		this.memberId = generateId();
		this.checkoutRecord = new CheckoutRecord();
	}

	private int generateId() {
		return 0;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}

}
