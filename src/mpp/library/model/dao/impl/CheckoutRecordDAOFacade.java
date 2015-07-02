package mpp.library.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.dao.CheckoutRecordDAO;


public class CheckoutRecordDAOFacade extends
		AbstractSerializationDAO<CheckoutRecord> implements CheckoutRecordDAO<CheckoutRecord> {

	public void saveCheckoutRecord(CheckoutRecord ckRecord) {
		this.writeObject(SerializationFile.CHECKOUT_RECORD.getValue(), ckRecord);
	}

	@Override
	public CheckoutRecord get(String memberId) {
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

	@Override
	public boolean update(CheckoutRecord record) {
		List<CheckoutRecord> recordList = this
				.getObjectList(SerializationFile.CHECKOUT_RECORD.getValue());
		if (recordList != null) {
			boolean existingRecord = false;
			for (int i = 0; i < recordList.size(); i++) {
				CheckoutRecord ckr = recordList.get(i);
				if (record.getLibraryMember().getMemberId() == ckr
						.getLibraryMember().getMemberId()) {
					existingRecord = true;
					recordList.set(i, record);
					this.writeObjectList(
							SerializationFile.CHECKOUT_RECORD.getValue(),
							recordList);
					return true;
				}
			}
			if (!existingRecord) {
				// record does not exist, add new record into the file
				recordList.add(record);
				this.writeObjectList(
						SerializationFile.CHECKOUT_RECORD.getValue(),
						recordList);
				return true;
			}
			
		} else {
			recordList = new ArrayList<CheckoutRecord>();
			recordList.add(record);
			this.writeObjectList(
					SerializationFile.CHECKOUT_RECORD.getValue(),
					recordList);
			return true;
		}
		return false;
	}
}
