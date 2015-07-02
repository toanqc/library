package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Publication;

public interface CheckoutDAO extends  BaseDAO<LibraryMember>{

	public Publication copyIsAvailable(Publication pub);
	
	LibraryMember get(String memberId) ;
	
	
	public void saveCheckoutRecordEntry(String name, CheckoutRecordEntry ckRecordEntry);
	
	public void saveCheckoutRecord(CheckoutRecord ckRecord);
	
	public List<MemberCheckoutRecord> printCheckoutRecord(String memberId);
}
