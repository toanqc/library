package mpp.library.model.service;

import java.util.List;

import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Publication;

public interface CheckoutService {

	void checkout(String memberID, Publication pub) throws Exception;

	List<CheckoutRecordEntry> getCheckRecordEntryByMemberId(int memberId);

	List<CheckoutRecordEntry> getOverdueCheckoutRecordEntryByMemberId(int memberId);
	
}
