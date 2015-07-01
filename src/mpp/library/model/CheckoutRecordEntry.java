package mpp.library.model;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {

	private static final long serialVersionUID = 8928346059682226255L;

	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private Copy copy;

	public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate,
			Copy copy) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.copy = copy;
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
}
