//package mpp.library.model.service.impl;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//import mpp.library.model.CheckoutRecord;
//import mpp.library.model.CheckoutRecordEntry;
//import mpp.library.model.Copy;
//import mpp.library.model.LibraryMember;
//import mpp.library.model.Periodical;
//import mpp.library.model.Publication;
//import mpp.library.model.dao.impl.CheckoutDAOFacade;
//import mpp.library.model.dao.impl.CheckoutRecordDAOFacade;
//import mpp.library.model.dao.impl.CheckoutRecordEntryDAOFacade;
//import mpp.library.model.service.CheckoutService;
//import mpp.library.model.service.CopyService;
//import mpp.library.model.service.MemberService;
//import mpp.library.model.service.PeriodicalService;
//
//public class CheckoutPeriodicalServiceImpl implements CheckoutService {
//
//	private CheckoutDAOFacade checkoutDAO;
//	private CheckoutRecordDAOFacade chkoutRecordDAOFacade;
//	private CheckoutRecordEntryDAOFacade chkoutRecordEntryDAOFacade;
//	private PeriodicalService periodicalService;
//	private CopyService copyService;
//	private MemberService memberService;
//
//	public CheckoutPeriodicalServiceImpl() {
//		// TODO Auto-generated constructor stub
//		checkoutDAO = new CheckoutDAOFacade();
//		chkoutRecordDAOFacade = new CheckoutRecordDAOFacade();
//		chkoutRecordEntryDAOFacade = new CheckoutRecordEntryDAOFacade();
//		periodicalService = new PeriodicalServiceImpl();
//		memberService = new MemberServiceImpl();
//		copyService = new CopyServiceImpl();
//		
//	}
//
//	@Override
//	public void checkout(int memberId, Publication periodical) throws Exception {
//		// TODO Auto-generated method stub
//		if (periodical instanceof Periodical) {
//			// check if memberID exist
//			LibraryMember member = checkoutDAO.get(memberId);
//			if (member == null) {
//				throw new IllegalArgumentException("Member ID not found");
//			} else {
//				// check if ISBN exist and copy is available
//				Publication publication = checkoutDAO.getPublication(periodical);
//				if (publication != null) {
//					List<Copy> listCopies = publication.getCopies();
//					if (listCopies != null) {
//						Copy copy = listCopies.stream().filter(s -> s.getAvailable()).findFirst().get();
//						if (copy != null) {
//							CheckoutRecord currentRecord = member.getCheckoutRecord();
//							LocalDate chkoutDate = LocalDate.now();
//							LocalDate dueDate = chkoutDate.plus(publication.getMaxCheckoutLength(), ChronoUnit.DAYS);
//							CheckoutRecordEntry ckRecordEntry = new CheckoutRecordEntry(chkoutDate, dueDate, copy);
//							copy.setAvailable(false);
//							currentRecord.addCheckoutEntry(ckRecordEntry);
//							chkoutRecordDAOFacade.update(currentRecord);
//							chkoutRecordEntryDAOFacade.update(ckRecordEntry);
//							periodicalService.updatePeriodicalCopy((Periodical)publication, copy);
//							memberService.updateMember(member);
//							copyService.updateCopy(copy);
//						} else {
//							throw new IllegalArgumentException("The copy of the periodical is not available");
//						}
//					}
//				} else {
//					throw new IllegalArgumentException("The copy is not available");
//				}
//			}
//		}
//	}

//}
