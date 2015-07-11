package mpp.library.model.service;

import mpp.library.model.Publication;

public interface CheckoutService {

	void checkout(int memberID, Publication pub) throws Exception;
	
}
