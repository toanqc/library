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

import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Publication;
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
		try {
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
	public Publication getPublication(Publication pub) {
		return null;
	}

	@Override
	public LibraryMember save(LibraryMember member) {
		return null;
	}

}
