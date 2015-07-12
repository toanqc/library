package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Publication;

/**
 * 
 * @author 984588
 *
 */

public interface CheckoutDAO extends BaseDAO<LibraryMember> {

	List<MemberCheckoutRecord> printCheckoutRecord(int memberId);
	
	Publication getPublication(Publication pub);
}
