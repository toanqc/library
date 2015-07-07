package mpp.library.model.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationType;
import mpp.library.model.dao.CheckoutDAO;

/**
 * 
 * @author bpham4
 * @Date 7/1/2015
 *
 */
public class CheckoutDAOFacade extends AbstractSerializationDAO<LibraryMember>implements CheckoutDAO {

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	public static final String CHECKOUT_RECORD_ENTRY = "CheckoutRecord";

	private CheckoutRecordEntryDAOFacade checkoutRecordEntryDAO = new CheckoutRecordEntryDAOFacade();
	
	@Override
	public void save(LibraryMember member) {
		this.writeObject(SerializationFile.MEMBER.getValue(), member);
		// TODO Auto-generated method stub
	}

	@Override
	public Publication getPublication(Publication pub) {
		// TODO Auto-generated method stub
		if (pub instanceof Book) {
			String ISBN = ((Book) pub).getISBN();
			BookDAOImpl bookDAO = new BookDAOImpl();
			Book book = bookDAO.get(ISBN);
			return book;
		} else if (pub instanceof Periodical) {
			String title = ((Periodical) pub).getTitle();
			String issueNo = ((Periodical) pub).getIssueNumber();
			PeriodicalDAOImpl PeriodicalDAO = new PeriodicalDAOImpl();
			Periodical periodical = PeriodicalDAO.get(issueNo, title);
			return periodical;
		}
		return null;
	}

	@Override
	public LibraryMember get(String memberId) {
		// TODO Auto-generated method stub
		List<LibraryMember> memberList = this.getObjectList(SerializationFile.MEMBER.getValue());
		for (LibraryMember libraryMember : memberList) {
			if (memberId.equals(String.valueOf(libraryMember.getMemberId()))) {
				return libraryMember;
			}
		}

		return null;
	}



	@Override
	public List<MemberCheckoutRecord> printCheckoutRecord(String memberId) {
		// TODO Auto-generated method stub
		List<MemberCheckoutRecord> listCheckoutRecord = new ArrayList<MemberCheckoutRecord>();
		LibraryMember member = get(memberId);
		if (member != null) {
			CheckoutRecord chkOutRecord = member.getCheckoutRecord();
			if (chkOutRecord != null) {
				List<CheckoutRecordEntry> listChkoutRecordEntries = checkoutRecordEntryDAO.getObjectList(SerializationFile.CHECKOUT_RECORD_ENTRY.getValue());
				for (int i = 0; i < listChkoutRecordEntries.size(); i++) {
					CheckoutRecordEntry entry = listChkoutRecordEntries.get(i);
					Copy copy = entry.getCopy();
					LocalDate chkoutDate = entry.getCheckoutDate();
					LocalDate dueDate = entry.getDueDate();
					Publication pub = copy.getPublication();
					String isbnOrIssueNo = "";
					String publicationType = "";
					String title = pub.getTitle();
					if (pub instanceof Book) {
						isbnOrIssueNo = ((Book) pub).getISBN();
						publicationType = PublicationType.BOOK.getValue();
					} else if (pub instanceof Periodical) {
						isbnOrIssueNo = ((Periodical) pub).getIssueNumber();
						publicationType = PublicationType.PERIODICAL.getValue();
					}
					MemberCheckoutRecord memberChkoutRecord = new MemberCheckoutRecord(isbnOrIssueNo, title,
							publicationType, chkoutDate, dueDate);
					listCheckoutRecord.add(memberChkoutRecord);
				}
			}
			else {
				throw new IllegalArgumentException("Member has no checkout record");
			}
		}
		else {
			throw new IllegalArgumentException("Member Id not found");
		}
		
		return listCheckoutRecord;
	}

}
