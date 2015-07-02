package mpp.library.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;

public class CheckoutRecordDAOFacade extends
		AbstractSerializationDAO<CheckoutRecord> {

	public void saveCheckoutRecord(CheckoutRecord ckRecord) {
		this.writeObject(SerializationFile.CHECKOUT_RECORD.getValue(), ckRecord);
	}

	public CheckoutRecord getCheckoutRecord(String memberId) {
		List<CheckoutRecord> memberList = this
				.getObjectList(SerializationFile.CHECKOUT_RECORD.getValue());
		for (CheckoutRecord chkRecord : memberList) {
			if (String.valueOf(chkRecord.getLibraryMember().getMemberId())
					.equals(memberId)) {
				return chkRecord;
			}
		}

		return null;
	}

	public boolean update(CheckoutRecord record) {
		List<CheckoutRecord> recordList = this
				.getObjectList(SerializationFile.CHECKOUT_RECORD.getValue());
		if (recordList != null) {
			for (int i = 0; i < recordList.size(); i++) {
				CheckoutRecord lm = recordList.get(i);
				if (record.getLibraryMember().getMemberId() == lm
						.getLibraryMember().getMemberId()) {
					recordList.set(i, record);
					this.writeObjectList(
							SerializationFile.CHECKOUT_RECORD.getValue(),
							recordList);
					return true;
				}
			}
		} else {
			recordList = new ArrayList<CheckoutRecord>();
			recordList.add(record);
			this.writeObjectList(
					SerializationFile.CHECKOUT_RECORD_ENTRY.getValue(),
					recordList);
			return true;
		}
		return false;
	}
}
