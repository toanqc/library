package mpp.library.model.dao;

import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Publication;

public interface CheckoutDAO extends  BaseDAO<LibraryMember>{

	public Copy copyIsAvailable(Publication pub);
	
	LibraryMember get(String memberId) ;
	
	
	
	
	
}
