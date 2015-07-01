package mpp.library.model;

import java.time.LocalDate;

public class MemberCheckoutRecord {

	private String ISBN;

	private String title;

	private String publicationType;

	private LocalDate checkoutDate;

	private LocalDate dueDate;

	public MemberCheckoutRecord(String isbn, String title, String type, LocalDate chkoutDate, LocalDate dueDate) {
		this.ISBN = isbn;
		this.title = title;
		this.publicationType = type;
		this.checkoutDate = chkoutDate;
		this.dueDate = dueDate;
	}
	
	public String getISBNOrIssueNo() {
		return this.ISBN;
	}
		
	public String getTitle() {
		return this.title;
	}
	
	public String getPublicationType() {
		return this.publicationType;
	}
	
	public LocalDate getCheckoutDate() {
		return this.checkoutDate;
	}
	
	public LocalDate getDueDate() {
		return this.dueDate;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.ISBN +"\t");
		sb.append(this.title +"\t");
		sb.append(this.publicationType +"\t");
		sb.append(this.checkoutDate +"\t");
		sb.append(this.dueDate +"\t");
		
		return sb.toString();
	}

}
