package mpp.library.model;

public class LibraryMember extends Person {

	private static final long serialVersionUID = 1373745594447872262L;

	private int memberId;
	private CheckoutRecord checkoutRecord;

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
