/**
 * 
 */
package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import mpp.library.model.Book;
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
			String sql = "SELECT * FROM PUBLICATION WHERE pubtype = ";
			if (pub instanceof Book) {
				sql += PublicationType.BOOK + " AND isbn_issueno = " + ((Book) pub).getISBN();
			} else {
				sql += PublicationType.PERIODICAL + " AND isbn_issueno = " + ((Periodical) pub).getIssueNumber();
			}
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
