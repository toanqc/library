/**
 * 
 */
package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationType;
import mpp.library.model.dao.CheckoutDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;
import mpp.library.model.dao.file.impl.AbstractSerializationDAO;

/**
 * @author bpham4
 *
 */
public class CheckoutDAODBFacade extends AbstractSerializationDAO<LibraryMember>implements CheckoutDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see mpp.library.model.dao.CheckoutDAO#getPublication(mpp.library.model.
	 * Publication)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see mpp.library.model.dao.CheckoutDAO#get(java.lang.String)
	 */
	@Override
	public LibraryMember get(int memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mpp.library.model.dao.CheckoutDAO#printCheckoutRecord(java.lang.String)
	 */
	@Override
	public List<MemberCheckoutRecord> printCheckoutRecord(int memberId) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			Connection conn = ConnectionManager.getInstance().getConnection();

			String sql = "SELECT p.ISBN_ISSUENUM, p.TITLE, p.PUBTYPE, r.CHECKOUTDATE, r.DUEDATE FROM PUBLICATION p "
					+ "INNER JOIN PUBCOPY c ON c.PUBID = p.ID "
					+ "INNER JOIN CHECKOUTRECORD r ON c.ID = r.COPYID AND r.IDMEM = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, memberId);
			// Perform SELECT
			ResultSet rs = stmt.executeQuery();
			List<MemberCheckoutRecord> listChkRecord = new ArrayList<MemberCheckoutRecord>();
			while (rs.next()) {
				String isbn_issueNo = rs.getString("ISBN_ISSUENUM").trim();
				String title = rs.getString("TITLE").trim();
				String pubType = rs.getString("PUBTYPE").trim();
				LocalDate chkoutDate = rs.getDate("CHECKOUTDATE").toLocalDate();
				LocalDate dueDate = rs.getDate("DUEDATE").toLocalDate();
				MemberCheckoutRecord record = new MemberCheckoutRecord(isbn_issueNo, title, pubType, chkoutDate,
						dueDate);
				listChkRecord.add(record);
			}
			// close Statement object; do not re-use
			stmt.close();
			conn.close();
			return listChkRecord;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Copy getAvailableCopy(Publication pub) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			Connection conn = ConnectionManager.getInstance().getConnection();

			String sql = "SELECT p.ID as PUBID, p.pubtype, p.title, p.isbn_issuenum, p.maxcheckoutlength, c.id as COPYID, c.COPYNUMBER, c.STATUS "
					+ "FROM PUBCOPY c INNER JOIN PUBLICATION p ON p.ID = c.PUBID and p.PUBTYPE = ? "
					+ "and c.STATUS = true and p.ISBN_ISSUENUM = ?";
			
			if(pub instanceof Periodical){
				sql = sql + " and p.title = ?";
			}
			
			String type = pub instanceof Book ? PublicationType.BOOK.getValue().toLowerCase()
					: PublicationType.PERIODICAL.getValue().toLowerCase();
			String iSBN_IssueNo = pub instanceof Book ? ((Book) pub).getISBN()
					: ((Periodical) pub).getIssueNumber();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			stmt.setString(2, iSBN_IssueNo);
			
			if(pub instanceof Periodical){
				stmt.setString(3, pub.getTitle());
			}

			// Perform SELECT
			ResultSet rs = stmt.executeQuery();
			Publication publication = null;
			while (rs.next()) {
				int id = rs.getInt("PUBID");
				String pubtype = rs.getString("pubtype").trim();
				String title = rs.getString("title").trim();
				String isbn_issueno = rs.getString("isbn_issuenum").trim();
				int maxChkout = rs.getInt("maxcheckoutlength");
				int copyId = rs.getInt("COPYID");
				int copyNo = rs.getInt("COPYNUMBER");
				boolean isAvailable = rs.getBoolean("status");
				publication = pubtype.equals(PublicationType.BOOK) ? new Book(id,
						isbn_issueno, title, maxChkout) : new Periodical(id,
						isbn_issueno, title, maxChkout);
				Copy copy = new Copy(copyId, publication, copyNo, isAvailable);
				return copy;
			}
			// close Statement object; do not re-use
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Publication getPublication(Publication pub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibraryMember save(LibraryMember member) {
		// TODO Auto-generated method stub
		return null;
	}

}
