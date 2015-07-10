package mpp.library.model.dao;

import mpp.library.model.CheckoutRecordEntry;

public interface CheckoutRecordEntryDAO extends BaseDAO<CheckoutRecordEntry> {

	boolean update(CheckoutRecordEntry checkoutRecordEntry);
}
