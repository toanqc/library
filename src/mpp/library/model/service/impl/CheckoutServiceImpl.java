package mpp.library.model.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Publication;
import mpp.library.model.dao.CheckoutDAO;
import mpp.library.model.dao.CheckoutRecordEntryDAO;
import mpp.library.model.dao.db.connection.DataAccessFactory;
import mpp.library.model.service.CheckoutService;
import mpp.library.model.service.CopyService;
import mpp.library.model.service.MemberService;

public class CheckoutServiceImpl implements CheckoutService {

	private CheckoutRecordEntryDAO chkoutRecordEntryDAOFacade;
	private CopyService copyService;
	private MemberService memberService;
	private CheckoutDAO checkoutDAODBFacade;

	public CheckoutServiceImpl() {
		// TODO Auto-generated constructor stub
		chkoutRecordEntryDAOFacade = (CheckoutRecordEntryDAO) DataAccessFactory.getDAOImpl(CheckoutRecordEntryDAO.class);
		memberService = new MemberServiceImpl();
		copyService = new CopyServiceImpl();
		checkoutDAODBFacade = (CheckoutDAO) DataAccessFactory.getDAOImpl(CheckoutDAO.class);
	}

	@Override
	public void checkout(String memberId, Publication pub) throws Exception {
		// TODO Auto-generated method stub
		// check if memberID exist
		LibraryMember member = memberService.getByMemberId(memberId);
		if (member == null) {
			throw new IllegalArgumentException("Member ID not found");

		} else {
			// check if ISBN exist and copy is available
			Copy copy = checkoutDAODBFacade.getAvailableCopy(pub);
			if (copy != null) {
				LocalDate chkoutDate = LocalDate.now();
				LocalDate dueDate = chkoutDate.plus(copy.getPublication().getMaxCheckoutLength(), ChronoUnit.DAYS);
				CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(member.getId(), copy, chkoutDate, dueDate);
				copy.setAvailable(false);
				chkoutRecordEntryDAOFacade.save(ckRecordEntry);
				copyService.updateCopy(copy);

			} else {
				throw new IllegalArgumentException("The copy of the book is not available");
			}

		}

	}
	
	@Override
	public List<CheckoutRecordEntry> getCheckRecordEntryByMemberId(int memberId){
		return chkoutRecordEntryDAOFacade.getCheckoutEnteriesOfMemeber(memberId);
	}

}
