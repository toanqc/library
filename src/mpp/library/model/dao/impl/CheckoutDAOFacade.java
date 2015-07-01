package mpp.library.model.dao.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.CheckoutRecord;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.dao.CheckoutDAO;

/**
 * 
 * @author bpham4
 *
 */
public class CheckoutDAOFacade implements CheckoutDAO {

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	public static final String CHECKOUT_RECORD_ENTRY = "CheckoutRecord";

	@Override
	public void save(LibraryMember member) {
		// TODO Auto-generated method stub

	}

	@Override
	public Copy copyIsAvailable(Publication pub) {
		// TODO Auto-generated method stub
		List<Copy> listCopies = null;
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
			if (book != null) {
				listCopies = book.getCopies();
			}
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
			if (periodical != null) {
				listCopies = periodical.getCopies();
			}
		}
		
		if (listCopies == null || (listCopies != null && listCopies.isEmpty())) {
			return null;
		} else {
			Copy copy = listCopies.get(listCopies.size() - 1);
			return copy;
		}
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

	/**
	 * 
	 * @param name
	 * @param ckRecordEntry
	 */
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
	
	/**
	 * 
	 * @param name
	 * @param ckRecord
	 */
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
}
