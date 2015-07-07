package mpp.library.model.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.dao.impl.CheckoutDAOFacade;
import mpp.library.model.dao.impl.CheckoutRecordDAOFacade;
import mpp.library.model.dao.impl.CheckoutRecordEntryDAOFacade;
import mpp.library.model.service.CheckoutService;

public class CheckoutPeriodicalServiceImpl implements CheckoutService {

	private CheckoutDAOFacade checkoutDAO;
	private CheckoutRecordDAOFacade chkoutRecordDAOFacade;
	private CheckoutRecordEntryDAOFacade chkoutRecordEntryDAOFacade;
	private BookServiceImpl bookService;

	public CheckoutPeriodicalServiceImpl() {
		// TODO Auto-generated constructor stub
		checkoutDAO = new CheckoutDAOFacade();
		chkoutRecordDAOFacade = new CheckoutRecordDAOFacade();
		chkoutRecordEntryDAOFacade = new CheckoutRecordEntryDAOFacade();
		bookService = new BookServiceImpl();
	}

	@Override
	public void checkout(String memberId, Publication periodical) throws Exception {
		// TODO Auto-generated method stub
		if (periodical instanceof Periodical) {
			// check if memberID exist
			LibraryMember member = checkoutDAO.get(memberId);
			if (member == null) {
				throw new IllegalArgumentException("Member ID not found");
			} else {
				// check if ISBN exist and copy is available
				Publication publication = checkoutDAO.getPublication(periodical);
				if (publication != null) {
					List<Copy> listCopies = publication.getCopies();
					if (listCopies != null) {
						Copy copy = null;
						for (int i = 0; i < listCopies.size(); i++) {
							if (listCopies.get(i).getAvailable()) {
								copy = listCopies.get(i);
								i = listCopies.size();
							}
						}
						if (copy != null) {
							CheckoutRecord currentRecord = member.getCheckoutRecord();
							LocalDate chkoutDate = LocalDate.now();
							LocalDate dueDate = chkoutDate.plus(publication.getMaxCheckoutLength(), ChronoUnit.DAYS);
							CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(chkoutDate, dueDate, copy);
							copy.setAvailable(false);
							currentRecord.addCheckoutEntry(ckRecordEntry);
							chkoutRecordDAOFacade.update(currentRecord);
							chkoutRecordEntryDAOFacade.update(ckRecordEntry);
							bookService.updateBookCopy((Book)publication, copy);
						} else {
							throw new IllegalArgumentException("The copy of the periodical is not available");
						}
					}
				} else {
					throw new IllegalArgumentException("The copy is not available");
				}
			}
		}
	}

}
