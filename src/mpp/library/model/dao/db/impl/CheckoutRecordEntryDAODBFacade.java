package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.logging.Logger;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.dao.CheckoutRecordEntryDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;
import mpp.library.model.dao.impl.AbstractSerializationDAO;

public class CheckoutRecordEntryDAODBFacade extends
		AbstractSerializationDAO<CheckoutRecordEntry> implements
		CheckoutRecordEntryDAO {
	
	private static final Logger LOG = Logger.getLogger(CheckoutRecordEntryDAODBFacade.class.getName());

	@Override
	public CheckoutRecordEntry save(CheckoutRecordEntry checkoutRecordEntry) {
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
			int key = -1;
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getInt(1);
			}
			checkoutRecordEntry.setId(key);
			// close Statement object; do not re-use
			stmt.close();
			conn.close();
		} catch (Exception e) {
			LOG.warning(e.getMessage());
		}
		return checkoutRecordEntry;
	}

	@Override
	public CheckoutRecordEntry get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(CheckoutRecordEntry checkoutRecordEntry) {
		// TODO Auto-generated method stub
		return false;
	}

}
