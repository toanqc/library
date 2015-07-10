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
import mpp.library.model.dao.impl.AbstractSerializationDAO;

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
	public LibraryMember get(String memberId) {
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

			String sql = "SELECT p.ISBN_ISSUENO, p.TITLE, p.PUBTYPE, r.CHECKOUTDATE, r.DUEDATE FROM PUBLICATION p "
					+ "INNER JOIN COPY c ON c.PUBID = p.ID "
					+ "INNER JOIN CHECKOUTRECORDENTRY r ON c.ID = r.COPYID AND r.MEMBERID = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, memberId);
			// Perform SELECT
			ResultSet rs = stmt.executeQuery();
			List<MemberCheckoutRecord> listChkRecord = new ArrayList<MemberCheckoutRecord>();
			while (rs.next()) {
				String isbn_issueNo = rs.getString("ISBN_ISSUENO");
				String title = rs.getString("TITLE");
				String pubType = rs.getString("PUBTYPE");
				LocalDate chkoutDate = rs.getDate("CHECKOUTDATE").toLocalDate();
				LocalDate dueDate = rs.getDate("DUEDATE").toLocalDate();
				MemberCheckoutRecord record = new MemberCheckoutRecord(isbn_issueNo, title, pubType, chkoutDate, dueDate);
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

			String sql = "SELECT p.ID as PUBID, p.pubtype, p.title, p.isbn_issueno, p.maxcheckoutlength, c.id as COPYID, c.COPYNO, c.ISAVAILABLE "
					+ "FROM COPY c INNER JOIN PUBLICATION p ON p.ID = c.PUBID and p.PUBTYPE = ? "
					+ "and c.ISAVAILABLE = true and p.title = ? and p.ISBN_ISSUENO = ?";
			String type = pub instanceof Book ? PublicationType.BOOK.getValue()
					: PublicationType.PERIODICAL.getValue();
			String iSBN_IssueNo = pub instanceof Book ? ((Book) pub).getISBN()
					: ((Periodical) pub).getIssueNumber();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pub.getId());
			stmt.setString(2, type);
			stmt.setString(3, iSBN_IssueNo);

			// Perform SELECT
			ResultSet rs = stmt.executeQuery();
			Publication publication = null;
			while (rs.next()) {
				int id = rs.getInt("PUBID");
				String pubtype = rs.getString("pubtype").trim();
				String title = rs.getString("title").trim();
				String isbn_issueno = rs.getString("isbn_issueno").trim();
				int maxChkout = rs.getInt("maxcheckoutlength");
				int copyId = rs.getInt("COPYID");
				int copyNo = rs.getInt("COPYNO");
				boolean isAvailable = rs.getBoolean("ISAVAILABLE");
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
	public void save(LibraryMember member) {
		// TODO Auto-generated method stub
		
	}

}
