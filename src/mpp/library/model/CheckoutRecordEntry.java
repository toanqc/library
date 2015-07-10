package mpp.library.model;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {

	private static final long serialVersionUID = 8928346059682226255L;

	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private Copy copy;
	private int id;
	private int memberId;

	public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate, Copy copy) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.copy = copy;
	}
	
	public CheckoutRecordEntry(int memberId, Copy copy, LocalDate chkoutDate, LocalDate dueDate) {
		this.memberId = memberId;
		this.copy = copy;
		this.checkoutDate = chkoutDate;
		this.dueDate = dueDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setMemberID(int memberId) {
		this.memberId = memberId;
	}
	
	public int getMemberId() {
		return this.memberId;
	}
}
