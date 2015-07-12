package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationType;
import mpp.library.model.dao.CopyDAO;
import mpp.library.model.dao.db.PublicationDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;

/**
 * @author Anil
 *
 */
public class CopyDAODBImpl implements CopyDAO {

	private ConnectionManager cm;

	private static final String INSERT_PUBLICATION_COPY = "INSERT INTO PUBCOPY (PUBID,COPYNUMBER,STATUS) values(?,?,?)";
	private static final String UPDATE_PUBLICATION_COPY = "UPDATE PUBCOPY SET PUBID=?,COPYNUMBER=?,STATUS=? WHERE ID =?";
	private static final String SELECT_PUBLICATION_COPY = "SELECT * FROM PUBCOPY WHERE ID=?";
	private static final String SELECT_PUBLICATION_COPIES_BY_PUBID = "SELECT * FROM PUBCOPY WHERE PUBID=?";

	public CopyDAODBImpl() {
		cm = ConnectionManager.getInstance();
	}

	@Override
	public Copy save(Copy copy) {
		Connection conn = cm.getConnection();

		// Insert Copy
		try {

			PreparedStatement statement = conn.prepareStatement(INSERT_PUBLICATION_COPY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, copy.getPublication().getId());
			statement.setInt(2, copy.getCopyNumber());
			statement.setBoolean(3, copy.getAvailable());
			statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();
			System.out.println("Executed query: " + INSERT_PUBLICATION_COPY);
			if (resultSet.next()) {
				copy.setId(resultSet.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		cm.closeConnection(conn);
		return copy;
	}

	@Override
	public Copy update(Copy copy) {
		Connection conn = cm.getConnection();

		try {

			PreparedStatement statement = conn.prepareStatement(UPDATE_PUBLICATION_COPY);
			statement.setInt(1, copy.getPublication().getId());
			statement.setInt(2, copy.getCopyNumber());
			statement.setBoolean(3, copy.getAvailable());
			statement.setInt(4, copy.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		cm.closeConnection(conn);
		return copy;
	}

	@Override
	public Copy get(int id) {
		Connection conn = cm.getConnection();
		Copy copy = null;

		try {

			PreparedStatement statement = conn.prepareStatement(SELECT_PUBLICATION_COPY);
			statement.setInt(1, id);
			statement.executeQuery();

			ResultSet resultSet = statement.getResultSet();
			if (resultSet.next()) {
				PublicationDAO publicationDAO = new PublicationDAODBImpl();
				copy = new Copy(publicationDAO.get(resultSet.getInt("pubId")), resultSet.getInt("copyNumber"),
						resultSet.getBoolean("status"));
				copy.setId(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		cm.closeConnection(conn);
		return copy;
	}

	@Override
	public List<Copy> getByPubId(int pubId) {
		Connection conn = cm.getConnection();
		List<Copy> copies = new ArrayList<>();
		Copy copy = null;

		try {

			PreparedStatement statement = conn.prepareStatement(SELECT_PUBLICATION_COPIES_BY_PUBID);
			statement.setInt(1, pubId);
			statement.executeQuery();

			ResultSet resultSet = statement.getResultSet();
			PublicationDAO publicationDAO = new PublicationDAODBImpl();
			while (resultSet.next()) {
				copy = new Copy(publicationDAO.get(resultSet.getInt("pubId")), resultSet.getInt("copyNumber"),
						resultSet.getBoolean("status"));
				copy.setId(resultSet.getInt("id"));
				copies.add(copy);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		cm.closeConnection(conn);
		return copies;
	}
	
	@Override
	public Copy getAvailableCopy(Publication pub) {
		try {
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

}
