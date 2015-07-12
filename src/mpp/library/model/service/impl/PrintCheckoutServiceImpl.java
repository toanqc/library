package mpp.library.model.service.impl;

import java.util.List;

import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.dao.db.impl.CheckoutDAODBFacade;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.PrintCheckoutService;

public class PrintCheckoutServiceImpl implements PrintCheckoutService {

	private CheckoutDAODBFacade checkoutDAO;
	private MemberService memberService;

	public PrintCheckoutServiceImpl() {
		checkoutDAO = new CheckoutDAODBFacade();
		memberService = new MemberServiceImpl();
	}

	@Override
	public List<MemberCheckoutRecord> search(String memberId) throws Exception {
		LibraryMember member = memberService.getByMemberId(memberId);
		if (member == null) {
			throw new IllegalArgumentException("Member ID not found");
		}
		return checkoutDAO.printCheckoutRecord(member.getId());
	}
}
