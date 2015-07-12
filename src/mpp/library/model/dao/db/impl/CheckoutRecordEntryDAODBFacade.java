package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mpp.library.model.Book;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationType;
import mpp.library.model.dao.CheckoutRecordEntryDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;
import mpp.library.model.dao.impl.AbstractSerializationDAO;

public class CheckoutRecordEntryDAODBFacade extends AbstractSerializationDAO<CheckoutRecordEntry>
		implements CheckoutRecordEntryDAO {

	private static final Logger LOG = Logger.getLogger(CheckoutRecordEntryDAODBFacade.class.getName());

	private static final String SELECT_CHECKOUT_ENTRIES_BY_MEM_ID = "SELECT PUB.PUBTYPE,PUB.ISBN_ISSUENUM,PUB.TITLE,CR.ID,CR.IDMEM,CR.CHECKOUTDATE,"
			+ "CR.DUEDATE,PC.ID,PC.PUBID,PC.COPYNUMBER,PC.STATUS FROM CHECKOUTRECORD AS CR "
			+ "JOIN PUBCOPY AS PC ON CR.COPYID=PC.ID JOIN PUBLICATION AS PUB ON PC.PUBID=PUB.ID WHERE CR.IDMEM = ?";

	private static final String SELECT_OVERDUE_CHECKOUT_ENTRIES_BY_MEM_ID = "SELECT PUB.PUBTYPE,PUB.ISBN_ISSUENUM,PUB.TITLE,CR.ID,CR.IDMEM,CR.CHECKOUTDATE,"
			+ "CR.DUEDATE,PC.ID,PC.PUBID,PC.COPYNUMBER,PC.STATUS FROM CHECKOUTRECORD AS CR "
			+ "JOIN PUBCOPY AS PC ON CR.COPYID=PC.ID JOIN PUBLICATION AS PUB ON PC.PUBID=PUB.ID WHERE CR.IDMEM = ? AND CR.DUEDATE < CURRENT_DATE";

	@Override
	public CheckoutRecordEntry save(CheckoutRecordEntry checkoutRecordEntry) {
		try {
			// TODO Auto-generated method stub
			Connection conn = ConnectionManager.getInstance().getConnection();

			String sql = "INSERT INTO CHECKOUTRECORD (idmem, copyid, checkoutdate, duedate) VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, checkoutRecordEntry.getMemberId());
			stmt.setInt(2, checkoutRecordEntry.getCopy().getId());
			LocalDate ckoutDate = checkoutRecordEntry.getCheckoutDate();
			stmt.setDate(3, Date.valueOf(ckoutDate));
			stmt.setDate(4, Date.valueOf(checkoutRecordEntry.getDueDate()));
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
			e.printStackTrace();
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

	@Override
	public List<CheckoutRecordEntry> getCheckoutEnteriesOfMemeber(int memId) {
		List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_CHECKOUT_ENTRIES_BY_MEM_ID);
			statement.setInt(1, memId);

			statement.executeQuery();
			System.out.println("Executed Query: " + SELECT_CHECKOUT_ENTRIES_BY_MEM_ID);
			ResultSet resultSet = statement.getResultSet();

			buildCheckoutRecordEntries(checkoutRecordEntries, resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkoutRecordEntries;
	}

	@Override
	public List<CheckoutRecordEntry> getOverdueCheckoutEnteriesOfMemeber(int memId) {
		List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_OVERDUE_CHECKOUT_ENTRIES_BY_MEM_ID);
			statement.setInt(1, memId);

			statement.executeQuery();
			System.out.println("Executed Query: " + SELECT_OVERDUE_CHECKOUT_ENTRIES_BY_MEM_ID);
			ResultSet resultSet = statement.getResultSet();

			buildCheckoutRecordEntries(checkoutRecordEntries, resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkoutRecordEntries;
	}

	private void buildCheckoutRecordEntries(List<CheckoutRecordEntry> checkoutRecordEntries, ResultSet resultSet)
			throws SQLException {
		CheckoutRecordEntry checkoutRecordEntry = null;
		Copy copy = null;

		while (resultSet.next()) {
			Publication publication = null;

			if (resultSet.getString("PUBTYPE").trim().equals(PublicationType.BOOK)) {
				publication = new Book(resultSet.getString("ISBN_ISSUENUM").trim());
			} else {
				publication = new Periodical(resultSet.getString("TITLE").trim(),
						resultSet.getString("ISBN_ISSUENUM").trim());
			}

			copy = new Copy(publication, resultSet.getInt("COPYNUMBER"), resultSet.getBoolean("STATUS"));
			checkoutRecordEntry = new CheckoutRecordEntry(resultSet.getDate("CHECKOUTDATE").toLocalDate(),
					resultSet.getDate("DUEDATE").toLocalDate(), copy);
			checkoutRecordEntries.add(checkoutRecordEntry);
		}
	}

}
