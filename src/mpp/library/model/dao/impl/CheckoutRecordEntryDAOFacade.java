package mpp.library.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.CheckoutRecordEntry;

public class CheckoutRecordEntryDAOFacade extends
		AbstractSerializationDAO<CheckoutRecordEntry> {

	public void saveCheckoutRecordEntry(
			List<CheckoutRecordEntry> listChkoutRecordEntry) {
		this.writeObjectList(
				SerializationFile.CHECKOUT_RECORD_ENTRY.getValue(),
				listChkoutRecordEntry);
	}

	public CheckoutRecordEntry getCheckoutRecordEntry(int copyNumber) {
		List<CheckoutRecordEntry> entryList = this
				.getObjectList(SerializationFile.CHECKOUT_RECORD_ENTRY
						.getValue());
		for (CheckoutRecordEntry entry : entryList) {
			if (entry.getCopy().getCopyNumber() == copyNumber) {
				return entry;
			}
		}

		return null;
	}

	public boolean update(CheckoutRecordEntry record) {
		List<CheckoutRecordEntry> recordList = this
				.getObjectList(SerializationFile.CHECKOUT_RECORD_ENTRY
						.getValue());
		if (recordList != null) {
			for (int i = 0; i < recordList.size(); i++) {
				CheckoutRecordEntry cre = recordList.get(i);
				if (record.getCopy().getCopyNumber() == cre.getCopy()
						.getCopyNumber()) {
					recordList.set(i, record);
					this.writeObjectList(
							SerializationFile.CHECKOUT_RECORD_ENTRY.getValue(),
							recordList);
					return true;
				}
			}
		}
		else {
			recordList = new ArrayList<CheckoutRecordEntry>();
			recordList.add(record);
			this.writeObjectList(
					SerializationFile.CHECKOUT_RECORD_ENTRY.getValue(),
					recordList);
			return true;
		}
		return false;
	}
}
