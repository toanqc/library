/**
 * 
 */
package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
	 * @see mpp.library.model.dao.BaseDAO#save(java.lang.Object)
	 */
	@Override
	public void save(LibraryMember member) {
		// TODO Auto-generated method stub
		try {
			Connection conn = ConnectionManager.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO LIBRARYMEMBER(memberid, firstname, lastname, phone, addressid) VALUES ("
					+ member.getMemberId() + ", " + member.getFirstName() + ", " + member.getLastName() + ", "
					+ member.getPhone() + ", " + member.getAddress().getId() + ")";
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
	@Override
	public Publication getPublication(Publication pub) {
		try {
			// TODO Auto-generated method stub
			Connection conn = ConnectionManager.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT p.id PUBID, p.pubtype, p.title, p.isbn_issueno, p.maxcheckoutlength, c.copyNo, c.isavailable FROM PUBLICATION "
					+ " INNER JOIN COPY c WHERE c.pubid = p.id AND c.isavailable = true AND p.pubtype = ";
			if (pub instanceof Book) {
				sql += PublicationType.BOOK + " AND isbn_issueno = " + ((Book) pub).getISBN();
			} else {
				sql += PublicationType.PERIODICAL + " AND isbn_issueno = " + ((Periodical) pub).getIssueNumber();
			}
			// Perform SELECT
			ResultSet rs = stmt.executeQuery(sql);
			int currentPubId = 0;
			Publication result = null;
			List<Publication> listPub = new ArrayList<Publication>();
			List<Copy> listCopies = null;
			while (rs.next()) {
				int id = rs.getInt("PUBID");
				String pubtype = rs.getString("pubtype").trim();
				String title = rs.getString("title").trim();
				String isbn_issueno = rs.getString("isbn_issueno").trim();
				int maxChkout = rs.getInt("maxcheckoutlength");
				int copyNo = rs.getInt("COPYNO");
				boolean isAvailable = rs.getBoolean("ISAVAILABLE");
				if (currentPubId != id) {
					currentPubId = id;
					result = pubtype.equals(PublicationType.BOOK) ? new Book(id, isbn_issueno, title, maxChkout) : new Periodical(id, isbn_issueno, title, maxChkout);
					listCopies = new ArrayList<Copy>();
					listPub.add(result);
					if (listPub.size() > 1) {
						return listPub.get(0);
					}
				}
				Copy copy = new Copy(result, copyNo, isAvailable);
				listCopies.add(copy);
			}
			// close Statement object; do not re-use
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

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

}
