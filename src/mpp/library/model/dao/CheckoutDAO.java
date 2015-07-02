package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Publication;

/**
 * 
 * @author 984588
 *
 */

public interface CheckoutDAO extends  BaseDAO<LibraryMember>{

	public Publication getPublication(Publication pub);
	
	LibraryMember get(String memberId) ;

	public List<MemberCheckoutRecord> printCheckoutRecord(String memberId);
}
