package mpp.library.model;

import java.time.LocalDate;

public class MemberCheckoutRecord {

	private String iSBN;

	private String issueNo;

	private String title;

	private String publicationType;

	private LocalDate checkoutDate;

	private LocalDate dueDate;

	public MemberCheckoutRecord(String isbn, String issueNo, String title, String type, LocalDate chkoutDate, LocalDate dueDate) {
		this.iSBN = isbn;
		this.issueNo = issueNo;
		this.title = title;
		this.publicationType = type;
		this.checkoutDate = chkoutDate;
		this.dueDate = dueDate;
	}
	
	public String getISBN() {
		return this.iSBN;
	}
	
	public String getIssueNo() {
		return this.issueNo;
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
		if (!this.iSBN.equals("")) {
			sb.append(this.iSBN +"\t");
		}
		else {
			sb.append(this.issueNo +"\t");
		}
		sb.append(this.title +"\t");
		sb.append(this.publicationType +"\t");
		sb.append(this.checkoutDate +"\t");
		sb.append(this.dueDate +"\t");
		
		return sb.toString();
	}

}
