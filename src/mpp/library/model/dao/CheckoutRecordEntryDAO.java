package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.CheckoutRecordEntry;

public interface CheckoutRecordEntryDAO extends BaseDAO<CheckoutRecordEntry> {

	boolean update(CheckoutRecordEntry checkoutRecordEntry);

	List<CheckoutRecordEntry> getCheckoutEnteriesOfMemeber(int memId);

	List<CheckoutRecordEntry> getOverdueCheckoutEnteriesOfMemeber(int memId);
}
