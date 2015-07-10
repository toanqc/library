/**
 * 
 */
package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class CheckoutDAODBFacade extends
		AbstractSerializationDAO<LibraryMember> implements CheckoutDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see mpp.library.model.dao.BaseDAO#save(java.lang.Object)
	 */
	@Override
	public void save(LibraryMember member) {
		// TODO Auto-generated method stub
		try {
			Connection conn = ConnectionManager.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO LIBRARYMEMBER(memberid, firstname, lastname, phone, addressid) VALUES ("
					+ member.getMemberId()
					+ ", "
					+ member.getFirstName()
					+ ", "
					+ member.getLastName()
					+ ", "
					+ member.getPhone()
					+ ", " + member.getAddress().getId() + ")";
			stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public List<MemberCheckoutRecord> printCheckoutRecord(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Copy getAvailableCopy(Publication pub) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			Connection conn = ConnectionManager.getInstance().getConnection();

			String sql = "SELECT p.id PUBID, p.pubtype, p.title, p.isbn_issueno, p.maxcheckoutlength, c.copyNo, c.isavailable FROM COPY c "
					+ "INNER JOIN PUBLICATION p WHERE c.pubid = p.id AND c.isavailable = true and p.id = ? AND p.pubtype = ? AND"
					+ " isbn_issueno = ? ";
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
				int copyNo = rs.getInt("COPYNO");
				boolean isAvailable = rs.getBoolean("ISAVAILABLE");
				publication = pubtype.equals(PublicationType.BOOK) ? new Book(id,
						isbn_issueno, title, maxChkout) : new Periodical(id,
						isbn_issueno, title, maxChkout);

				Copy copy = new Copy(publication, copyNo, isAvailable);
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

}
