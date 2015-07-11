package mpp.library.model;

/**
 * 
 * @author Toan Quach
 *
 */
public class LibraryMember extends Person {

	private static final long serialVersionUID = 1373745594447872262L;

	private int id;
	private String memberId;
	private CheckoutRecord checkoutRecord;

	public LibraryMember(String memberId, String firstName, String lastName, String phone, Address address) {
		super(firstName, lastName, phone, address);
		this.memberId = memberId;
		this.checkoutRecord = new CheckoutRecord(this);
	}

	public String getMemberId() {
		return memberId.trim();
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}

	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
