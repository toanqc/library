package mpp.library.model.service;

import java.util.List;

import mpp.library.model.MemberCheckoutRecord;

public interface PrintCheckoutService {
	
	List<MemberCheckoutRecord> search(String memberId) throws Exception;

}
