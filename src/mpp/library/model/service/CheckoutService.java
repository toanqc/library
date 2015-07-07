package mpp.library.model.service;

import mpp.library.model.Publication;

public interface CheckoutService {

	void checkout(String memberID, Publication pub) throws Exception;
	
}
