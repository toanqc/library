package mpp.library.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemberCheckoutRecord {

	private final SimpleStringProperty issueNo = new SimpleStringProperty("");
	private final SimpleStringProperty title = new SimpleStringProperty("");
	private final SimpleStringProperty type = new SimpleStringProperty("");
	private final SimpleStringProperty chkoutDate = new SimpleStringProperty("");
	private final SimpleStringProperty dueDate = new SimpleStringProperty("");

	public MemberCheckoutRecord(String i, String t, String type, LocalDate outDate, LocalDate dueDate) {
		// TODO Auto-generated constructor stub
		setIssueNo(i);
		setTitle(t);
		setType(type);
		setChkoutDate(outDate);
		setDueDate(dueDate);
	}

	public void setIssueNo(String i) {
		issueNo.set(i);
	}

	public String getIssueno() {
		return this.issueNo.get();
	}

	public void setTitle(String t) {
		this.title.set(t);
	}

	public String getTitle() {
		return this.title.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public String getType() {
		return this.type.get();
	}

	public void setChkoutDate(LocalDate outDate) {
		this.chkoutDate.set(outDate.toString());
	}

	public String getChkoutDate() {
		return this.chkoutDate.get();
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate.set(dueDate.toString());
	}

	public String getDueDate() {
		return this.dueDate.get();
	}
	
	public StringProperty issueNoProperty() {
		return this.issueNo;
	}
	
	public StringProperty titlePropery() {
		return this.title;
	}
	
	public StringProperty typePropery() {
		return this.type;
	}
	
	public StringProperty chkoutDatePropery() {
		return this.chkoutDate;
	}
	
	public StringProperty dueDatePropery() {
		return this.dueDate;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.issueNo + "\t");
		sb.append(this.title + "\t");
		sb.append(this.getType());
		if (this.getType() == PublicationType.BOOK.getValue()) {
			sb.append("      \t");
		} else {
			sb.append("\t");
		}
		sb.append(this.getChkoutDate() + "\t");
		sb.append(this.dueDate + "\t");

		return sb.toString();
	}

}
