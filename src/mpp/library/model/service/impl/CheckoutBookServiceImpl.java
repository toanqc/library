package mpp.library.model.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Publication;
import mpp.library.model.dao.impl.CheckoutDAOFacade;
import mpp.library.model.dao.impl.CheckoutRecordDAOFacade;
import mpp.library.model.dao.impl.CheckoutRecordEntryDAOFacade;
import mpp.library.model.service.CheckoutService;

public class CheckoutBookServiceImpl implements CheckoutService {
	
	private CheckoutDAOFacade checkoutDAO;
	private CheckoutRecordDAOFacade chkoutRecordDAOFacade;
	private CheckoutRecordEntryDAOFacade chkoutRecordEntryDAOFacade;
	private CopyServiceImpl copyService;

	public CheckoutBookServiceImpl() {
		// TODO Auto-generated constructor stub
		checkoutDAO = new CheckoutDAOFacade();
		chkoutRecordDAOFacade = new CheckoutRecordDAOFacade();
		chkoutRecordEntryDAOFacade = new CheckoutRecordEntryDAOFacade();
		copyService = new CopyServiceImpl();
	}

	@Override
	public void checkout(String memberId, Publication pub) throws Exception {
		// TODO Auto-generated method stub
		if (pub instanceof Book) {
			// check if memberID exist
			checkoutDAO = new CheckoutDAOFacade();
			LibraryMember member = checkoutDAO.get(memberId);
			if (member == null) {
				throw new IllegalArgumentException("Member ID not found");

			} else {
				// check if ISBN exist and copy is available
				Publication publication = checkoutDAO.getPublication(pub);
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
							copyService.updateCopy(copy);
							
						} else {
							throw new IllegalArgumentException("The copy of the book is not available");
						}
					}

				} else {
					throw new IllegalArgumentException("The book is not available");
				}
			}
		}
	}

}
