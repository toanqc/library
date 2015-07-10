package mpp.library.model.service.impl;

import java.util.List;

import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.dao.impl.CheckoutDAOFacade;
import mpp.library.model.service.PrintCheckoutService;

public class PrintCheckoutServiceImpl implements PrintCheckoutService {
	
	private CheckoutDAOFacade checkoutDAO;

	public PrintCheckoutServiceImpl() {
		// TODO Auto-generated constructor stub
		checkoutDAO = new CheckoutDAOFacade();
	}

	@Override
	public List<MemberCheckoutRecord> search(int memberId) throws Exception {
		// TODO Auto-generated method stub
		return checkoutDAO.printCheckoutRecord(memberId);
	}

}
