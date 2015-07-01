package mpp.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CheckoutRecord implements Serializable {

	private static final long serialVersionUID = -2464967253755304313L;

	private List<CheckoutRecordEntry> checkoutRecordEntries;
	
	private LibraryMember libraryMember;

	public CheckoutRecord(LibraryMember member) {
		this.libraryMember = member;
		this.checkoutRecordEntries = new ArrayList();
	}

	public void addCheckoutEntry(CheckoutRecordEntry checkoutEntry) {
		this.checkoutRecordEntries.add(checkoutEntry);
	}

	public boolean removeCheckoutEntry(CheckoutRecordEntry checkoutEntry) {
		return this.checkoutRecordEntries.remove(checkoutEntry);
	}

	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return checkoutRecordEntries;
	}

	public void setCheckoutRecordEntries(ObservableList<CheckoutRecordEntry> checkoutRecordEntries) {
		this.checkoutRecordEntries = checkoutRecordEntries;
	}
	
	public LibraryMember getLibraryMember() {
		return this.libraryMember;
	}
}
