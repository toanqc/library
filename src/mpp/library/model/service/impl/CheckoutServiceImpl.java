package mpp.library.model.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import mpp.library.model.Book;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Publication;
import mpp.library.model.dao.db.impl.CheckoutDAODBFacade;
import mpp.library.model.dao.db.impl.CheckoutRecordEntryDAODBFacade;
import mpp.library.model.service.CheckoutService;
import mpp.library.model.service.CopyService;
import mpp.library.model.service.MemberService;

public class CheckoutServiceImpl implements CheckoutService {

	private CheckoutRecordEntryDAODBFacade chkoutRecordEntryDAOFacade;
	private CopyService copyService;
	private MemberService memberService;
	private CheckoutDAODBFacade checkoutDAODBFacade;

	public CheckoutServiceImpl() {
		// TODO Auto-generated constructor stub
		chkoutRecordEntryDAOFacade = new CheckoutRecordEntryDAODBFacade();
		memberService = new MemberServiceImpl();
		copyService = new CopyServiceImpl();
		checkoutDAODBFacade = new CheckoutDAODBFacade();
	}

	@Override
	public void checkout(String memberId, Publication pub) throws Exception {
		// TODO Auto-generated method stub
		if (pub instanceof Book) {
			// check if memberID exist
			LibraryMember member = memberService.get(memberId);
			if (member == null) {
				throw new IllegalArgumentException("Member ID not found");

			} else {
				// check if ISBN exist and copy is available
				Copy copy = checkoutDAODBFacade.getAvailableCopy(pub);
				if (copy != null) {
					LocalDate chkoutDate = LocalDate.now();
					LocalDate dueDate = chkoutDate.plus(copy.getPublication().getMaxCheckoutLength(), ChronoUnit.DAYS);
					CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(member.getMemberId(), copy, chkoutDate,
							dueDate);
					copy.setAvailable(false);
					chkoutRecordEntryDAOFacade.save(ckRecordEntry);
					copyService.updateCopy(copy);

				} else {
					throw new IllegalArgumentException("The copy of the book is not available");
				}

			}
		}
	}

}
