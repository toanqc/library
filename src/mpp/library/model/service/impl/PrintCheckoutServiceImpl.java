package mpp.library.model.service.impl;

import java.util.List;

import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.dao.db.impl.CheckoutDAODBFacade;
import mpp.library.model.service.PrintCheckoutService;

public class PrintCheckoutServiceImpl implements PrintCheckoutService {
	
	private CheckoutDAODBFacade checkoutDAO;

	public PrintCheckoutServiceImpl() {
		// TODO Auto-generated constructor stub
		checkoutDAO = new CheckoutDAODBFacade();
	}

	@Override
	public List<MemberCheckoutRecord> search(int memberId) throws Exception {
		// TODO Auto-generated method stub
		return checkoutDAO.printCheckoutRecord(memberId);
	}

}
