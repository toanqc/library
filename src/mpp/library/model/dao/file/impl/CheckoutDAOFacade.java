package mpp.library.model.dao.file.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Publication;
import mpp.library.model.dao.CheckoutDAO;

/**
 * 
 * @author bpham4
 * @Date 7/1/2015
 *
 */
public class CheckoutDAOFacade extends AbstractSerializationDAO<LibraryMember>implements CheckoutDAO {

	// private CheckoutRecordEntryDAOFacade checkoutRecordEntryDAO = new CheckoutRecordEntryDAOFacade();
	
	@Override
	public LibraryMember save(LibraryMember member) {
		this.writeObject(SerializationFile.MEMBER.getValue(), member);
		return null;
	}

	@Override
	public Publication getPublication(Publication pub) {
//		if (pub instanceof Book) {
//			String ISBN = ((Book) pub).getISBN();
//			PublicationDAODBImpl bookDAO = new PublicationDAODBImpl();
//			Book book = bookDAO.get(ISBN);
//			return book;
//		} else if (pub instanceof Periodical) {
//			String title = ((Periodical) pub).getTitle();
//			String issueNo = ((Periodical) pub).getIssueNumber();
//			PeriodicalDAOImpl PeriodicalDAO = new PeriodicalDAOImpl();
//			Periodical periodical = PeriodicalDAO.get(issueNo, title);
//			return periodical;
//		}
		return null;
	}

	@Override
	public LibraryMember get(int memberId) {
		List<LibraryMember> memberList = this.getObjectList(SerializationFile.MEMBER.getValue());
		return memberList.stream().filter(s -> memberId == s.getId()).findFirst().get();
	}



	@Override
	public List<MemberCheckoutRecord> printCheckoutRecord(int memberId) {
		List<MemberCheckoutRecord> listCheckoutRecord = new ArrayList<MemberCheckoutRecord>();
//		LibraryMember member = get(memberId);
//		if (member != null) {
//			CheckoutRecord chkOutRecord = member.getCheckoutRecord();
//			if (chkOutRecord != null) {
//				List<CheckoutRecordEntry> listChkoutRecordEntries = checkoutRecordEntryDAO.getObjectList(SerializationFile.CHECKOUT_RECORD_ENTRY.getValue());
//				for (CheckoutRecordEntry entry : listChkoutRecordEntries) {
//					Copy copy = entry.getCopy();
//					LocalDate chkoutDate = entry.getCheckoutDate();
//					LocalDate dueDate = entry.getDueDate();
//					Publication pub = copy.getPublication();
//					String isbnOrIssueNo = "";
//					String publicationType = "";
//					String title = pub.getTitle();
//					if (pub instanceof Book) {
//						isbnOrIssueNo = ((Book) pub).getISBN();
//						publicationType = PublicationType.BOOK.getValue();
//					} else if (pub instanceof Periodical) {
//						isbnOrIssueNo = ((Periodical) pub).getIssueNumber();
//						publicationType = PublicationType.PERIODICAL.getValue();
//					}
//					MemberCheckoutRecord memberChkoutRecord = new MemberCheckoutRecord(isbnOrIssueNo, title,
//							publicationType, chkoutDate, dueDate);
//					listCheckoutRecord.add(memberChkoutRecord);
//				}
//			}
//			else {
//				throw new IllegalArgumentException("Member has no checkout record");
//			}
//		}
//		else {
//			throw new IllegalArgumentException("Member Id not found");
//		}
//		
		return listCheckoutRecord;
	}


}
