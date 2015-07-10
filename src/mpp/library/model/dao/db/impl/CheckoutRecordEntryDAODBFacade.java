package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.dao.CheckoutRecordEntryDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;
import mpp.library.model.dao.impl.AbstractSerializationDAO;

public class CheckoutRecordEntryDAODBFacade extends
		AbstractSerializationDAO<CheckoutRecordEntry> implements
		CheckoutRecordEntryDAO {

	@Override
	public void save(CheckoutRecordEntry checkoutRecordEntry) {
		try {
			// TODO Auto-generated method stub
			Connection conn = ConnectionManager.getInstance().getConnection();

			String sql = "INSERT INTO CHECKOUTRECORDENTRY (memberid, copyid, checkoutdate, duedate) VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkoutRecordEntry.getMemberId());
			stmt.setInt(2, checkoutRecordEntry.getCopy().getCopyNumber());
			LocalDate ckoutDate = checkoutRecordEntry.getCheckoutDate();
			stmt.setDate(3, Date.valueOf(ckoutDate));

			// Perform SELECT
			stmt.executeUpdate();
			// close Statement object; do not re-use
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public CheckoutRecordEntry get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(CheckoutRecordEntry checkoutRecordEntry) {
		// TODO Auto-generated method stub
		return false;
	}

}
