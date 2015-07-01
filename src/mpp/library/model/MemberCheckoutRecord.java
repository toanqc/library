package mpp.library.model;

import java.time.LocalDate;

public class MemberCheckoutRecord {

	private String isbn;

	private String title;

	private String publicationType;

	private LocalDate checkoutDate;

	private LocalDate dueDate;

	public MemberCheckoutRecord(String isbn, String title, String type,
			LocalDate chkoutDate, LocalDate dueDate) {
		this.isbn = isbn;
		this.title = title;
		this.publicationType = type;
		this.checkoutDate = chkoutDate;
		this.dueDate = dueDate;
	}

	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	public String getISBN() {
		return this.isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setPublicationType(String type) {
		this.publicationType = type;
	}

	public String getPublicationType() {
		return this.publicationType;
	}

	public void setCheckoutDate(LocalDate chkoutDate) {
		this.checkoutDate = chkoutDate;
	}

	public LocalDate getCheckoutDate() {
		return this.checkoutDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getDueDate() {
		return this.dueDate;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.isbn + "\t");
		sb.append(this.title + "\t");
		sb.append(this.publicationType);
		if (this.publicationType == PublicationType.BOOK.getValue()) {
			sb.append("      \t");
		} else {
			sb.append("\t");
		}
		sb.append(this.checkoutDate + "\t");
		sb.append(this.dueDate + "\t");

		return sb.toString();
	}

}
