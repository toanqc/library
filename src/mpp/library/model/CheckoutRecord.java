package mpp.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

public class CheckoutRecord implements Serializable {

	private static final long serialVersionUID = -2464967253755304313L;

	private List<CheckoutRecordEntry> checkoutRecordEntries;

	private LibraryMember libraryMember;

	public CheckoutRecord(LibraryMember member) {
		this.libraryMember = member;
		this.checkoutRecordEntries = new ArrayList<CheckoutRecordEntry>();
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

	public void setCheckoutRecordEntries(
			ObservableList<CheckoutRecordEntry> checkoutRecordEntries) {
		this.checkoutRecordEntries = checkoutRecordEntries;
	}

	public LibraryMember getLibraryMember() {
		return this.libraryMember;
	}

	public List<CheckoutRecordEntry> getOverdueCheckoutRecordEntries() {
		List<CheckoutRecordEntry> crEntries = new ArrayList<>();

		if (checkoutRecordEntries != null && !checkoutRecordEntries.isEmpty()) {

			for (CheckoutRecordEntry checkoutRecordEntry : checkoutRecordEntries) {
				if (checkoutRecordEntry.getDueDate().compareTo(LocalDate.now()) < 1) {
					crEntries.add(checkoutRecordEntry);
				}
			}
		}
		return crEntries;
	}
}
