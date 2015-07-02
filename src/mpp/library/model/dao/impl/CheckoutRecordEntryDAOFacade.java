package mpp.library.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.dao.CheckoutRecordDAO;

public class CheckoutRecordEntryDAOFacade extends
		AbstractSerializationDAO<CheckoutRecordEntry> implements CheckoutRecordDAO<CheckoutRecordEntry> {

	public void saveCheckoutRecordEntry(
			List<CheckoutRecordEntry> listChkoutRecordEntry) {
		this.writeObjectList(
				SerializationFile.CHECKOUT_RECORD_ENTRY.getValue(),
				listChkoutRecordEntry);
	}

	@Override
	public CheckoutRecordEntry get(String copyNumber) {
		List<CheckoutRecordEntry> entryList = this
				.getObjectList(SerializationFile.CHECKOUT_RECORD_ENTRY
						.getValue());
		for (CheckoutRecordEntry entry : entryList) {
			if (String.valueOf(entry.getCopy().getCopyNumber()).equals(copyNumber)) {
				return entry;
			}
		}

		return null;
	}

	@Override
	public boolean update(CheckoutRecordEntry record) {
		List<CheckoutRecordEntry> recordList = this
				.getObjectList(SerializationFile.CHECKOUT_RECORD_ENTRY
						.getValue());
		if (recordList != null) {
			boolean existingRecord = false;
			for (int i = 0; i < recordList.size(); i++) {
				CheckoutRecordEntry cre = recordList.get(i);
				if (record.getCopy().getCopyNumber() == cre.getCopy()
						.getCopyNumber()) {
					existingRecord = true;
					recordList.set(i, record);
					this.writeObjectList(
							SerializationFile.CHECKOUT_RECORD_ENTRY.getValue(),
							recordList);
					return true;
				}
			}
			if (!existingRecord) {
				// record does not exist, add new record into the file
				recordList.add(record);
				this.writeObjectList(
						SerializationFile.CHECKOUT_RECORD_ENTRY.getValue(),
						recordList);
				return true;
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
