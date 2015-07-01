package mpp.library.model.dao.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class CheckoutDAOFacade extends AbstractSerializationDAO<LibraryMember> implements CheckoutDAO {

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	public static final String CHECKOUT_RECORD_ENTRY = "CheckoutRecord";

	@Override
	public void save(LibraryMember member) {
		// TODO Auto-generated method stub
		ObjectOutputStream out = null;
		String name = member.getFullName();
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(member);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}

	@Override
	public Publication copyIsAvailable(Publication pub) {
		// TODO Auto-generated method stub
		if (pub instanceof Book) {
			String ISBN = ((Book) pub).getISBN();
			ObjectInputStream in = null;
			Book book = null;
			try {
				Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, ISBN);
				in = new ObjectInputStream(Files.newInputStream(path));
				book = (Book) in.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
					}
				}
			}
			return book;
		}
		else if (pub instanceof Periodical) {
			String title = ((Periodical) pub).getTitle();
			String issueNo = ((Periodical) pub).getIssueNumber();
			ObjectInputStream in = null;
			Periodical periodical = null;
			try {
				Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, title + "_" + issueNo);
				in = new ObjectInputStream(Files.newInputStream(path));
				periodical = (Periodical) in.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
					}
				}
			}
			return periodical;
		}
		return null;
	}

	@Override
	public LibraryMember get(String memberId) {
		// TODO Auto-generated method stub
		ObjectInputStream in = null;
		LibraryMember member = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, memberId);
			in = new ObjectInputStream(Files.newInputStream(path));
			member = (LibraryMember) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return member;
	}

	@Override
	public void saveCheckoutRecordEntry(String name, CheckoutRecordEntry ckRecordEntry) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ckRecordEntry);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	@Override
	public void saveCheckoutRecord(CheckoutRecord ckRecord) {
		ObjectOutputStream out = null;
		try {
			String name = CHECKOUT_RECORD_ENTRY + "_" + ckRecord.getLibraryMember().getFullName();
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ckRecord);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}

	@Override
	public List<MemberCheckoutRecord> printCheckoutRecord(String memberId) {
		// TODO Auto-generated method stub
		List<MemberCheckoutRecord> listCheckoutRecord = new ArrayList<MemberCheckoutRecord>();
		LibraryMember member = get(memberId);
		if (member != null) {
			CheckoutRecord chkOutRecord = member.getCheckoutRecord();
			List<CheckoutRecordEntry> listChkoutRecordEntries = chkOutRecord.getCheckoutRecordEntries();
			for (int i = 0; i < listChkoutRecordEntries.size(); i++) {
				CheckoutRecordEntry entry = listChkoutRecordEntries.get(i);
				Copy copy = entry.getCopy();
				LocalDate chkoutDate = entry.getCheckoutDate();
				LocalDate dueDate = entry.getDueDate();
				Publication pub = copy.getPublication();
				String isbnOrIssueNo = "";
				String publicationType = "";
				String title = "Test" ;//pub.getTitle();
				if (pub instanceof Book) {
					isbnOrIssueNo = ((Book) pub).getISBN();
					publicationType = PublicationType.BOOK.getValue();
				}
				else if (pub instanceof Periodical) {
					isbnOrIssueNo = ((Periodical) pub).getIssueNumber();
					publicationType = PublicationType.PERIODICAL.getValue();
				}
				isbnOrIssueNo = "Bao Test";
				MemberCheckoutRecord memberChkoutRecord = new MemberCheckoutRecord(isbnOrIssueNo, title, publicationType, chkoutDate, dueDate);
				listCheckoutRecord.add(memberChkoutRecord);
			}
		}
		return listCheckoutRecord;
	}
	
}
