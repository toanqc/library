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
import mpp.library.model.dao.AuthorDAO;
import mpp.library.model.dao.db.PublicationDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;
import mpp.library.util.PublicationType;

/**
 * @author Anil
 *
 */
public class PublicationDAODBImpl implements PublicationDAO {

	private static final String INSERT_PUBLICATION = "INSERT INTO PUBLICATION (PUBTYPE,TITLE,ISBN_ISSUENUM,MAXCHECKOUTLENGTH) values(?,?,?,?)";
	private static final String GET_PUBLICATION_BY_ID = "SELECT P.ID,P.PUBTYPE,P.TITLE,P.ISBN_ISSUENUM,PC.COPYNUMBER,PC.STATUS,P.MAXCHECKOUTLENGTH from PUBLICATION AS P JOIN PUBCOPY AS PC ON P.id = PC.PUBID AND P.id=?";
	private static final String GET_PUBLICATION_BY_TYPE_TITLE__ISBN_OR_ISSUENO = "SELECT P.ID,P.PUBTYPE,P.TITLE,P.ISBN_ISSUENUM,P.MAXCHECKOUTLENGTH,PC.COPYNUMBER,PC.STATUS FROM PUBLICATION AS P JOIN PUBCOPY AS PC ON P.ID=PC.PUBID AND P.pubtype=? AND P.isbn_issuenum=?";
	private static final String INSERT_PUBLICATION_COPY = "INSERT INTO PUBCOPY (PUBID,COPYNUMBER,STATUS) values(?,?,?)";
	private static final String INSERT_PUBLICATION_AUTHOR = "INSERT INTO PUBLICATIONAUTHOR(AUTHORID,PUBID) values(?,?)";
	private static final String GET_BOOKS_AUTHOR_ID = "SELECT P.ID,P.PUBTYPE,P.TITLE,P.ISBN_ISSUENUM,P.MAXCHECKOUTLENGTH FROM PUBLICATION AS P JOIN PUBLICATIONAUTHOR PA ON P.id=PA.pubId AND PA.authorid=?";

	private ConnectionManager cm;
	private AuthorDAO authorDAO;

	public PublicationDAODBImpl() {
		cm = ConnectionManager.getInstance();
		authorDAO = new AuthorDAODBImpl();
	}

	@Override
	public Publication save(Publication publication) {
		Connection conn = cm.getConnection();

		System.out.println("Saving Publication: " + publication);
		try {
			conn.setAutoCommit(false);

			// Insert Publication
			insertPublication(publication, conn);

			// Insert Copies
			insertPublicationCopies(publication, conn);

			// Insert Book Author
			insertBookAuthor(publication, conn);

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		cm.closeConnection(conn);

		return publication;
	}

	private void insertBookAuthor(Publication publication, Connection conn) throws SQLException {
		PreparedStatement statement;
		if (publication instanceof Book) {
			Book book = (Book) publication;
			int authorId = -1;
			for (int i = 0; i < book.getAuthorList().size(); i++) {
				authorId = authorDAO.save(book.getAuthorList().get(i)).getID();
				book.getAuthorList().get(i).setId(authorId);
				statement = conn.prepareStatement(INSERT_PUBLICATION_AUTHOR, Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, authorId);
				statement.setInt(2, publication.getId());
				statement.executeUpdate();
				System.out.println("Executed query: " + INSERT_PUBLICATION_AUTHOR);
			}
		}
	}

	private void insertPublicationCopies(Publication publication, Connection conn) throws SQLException {
		PreparedStatement statement;
		ResultSet resultSet;
		for (int i = 1; i <= publication.getCopies().size(); i++) {
			statement = conn.prepareStatement(INSERT_PUBLICATION_COPY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, publication.getId());
			statement.setInt(2, i);
			statement.setBoolean(3, publication.getCopies().get(i - 1).getAvailable());
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			System.out.println("Executed query: " + INSERT_PUBLICATION_COPY);
			if (resultSet.next()) {
				publication.getCopies().get(i - 1).setId(resultSet.getInt(1));
				publication.getCopies().get(i - 1).setPublication(publication);
			}
		}
	}

	private void insertPublication(Publication publication, Connection conn) throws SQLException {
		PreparedStatement statement = conn.prepareStatement(INSERT_PUBLICATION, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, publication instanceof Book ? PublicationType.BOOK.toString().toLowerCase()
				: PublicationType.PERIODICAL.toString().toLowerCase());
		statement.setString(2, publication.getTitle());

		statement.setString(3, publication instanceof Book ? ((Book) publication).getISBN()
				: ((Periodical) publication).getIssueNumber());

		statement.setInt(4, publication.getMaxCheckoutLength());
		System.out.println("Executing query: " + INSERT_PUBLICATION);
		statement.executeUpdate();

		ResultSet resultSet = statement.getGeneratedKeys();
		if (resultSet.next()) {
			publication.setId(resultSet.getInt(1));
		}
	}

	@Override
	public Publication get(int id) {
		Connection conn = cm.getConnection();
		Publication publication = null;
		List<Copy> copies = new ArrayList<>();

		try {
			PreparedStatement statement = conn.prepareStatement(GET_PUBLICATION_BY_ID);
			statement.setInt(1, id);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();

			publication = setPublicationCopies(publication, copies, resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		cm.closeConnection(conn);
		return publication;
	}

	private Publication setPublicationCopies(Publication publication, List<Copy> copies, ResultSet resultSet)
			throws SQLException {

		while (resultSet.next()) {
			if (publication == null) {
				if (resultSet.getString("pubType").trim().equals(PublicationType.BOOK.toString().toLowerCase())) {
					publication = new Book(resultSet.getString("isbn_issuenum").trim());
					publication.setTitle(resultSet.getString("title").trim());
				} else {
					publication = new Periodical(resultSet.getString("title").trim(),
							resultSet.getString("isbn_issuenum").trim());
				}
				publication.setMaxCheckoutLength(resultSet.getInt("maxcheckoutlength"));
				publication.setId(resultSet.getInt("id"));
			}

			Copy copy = new Copy(publication, resultSet.getInt("copynumber"), resultSet.getBoolean("status"));
			copies.add(copy);
			publication.setCopies(copies);
		}

		return publication;
	}

	@Override
	public Publication get(PublicationType publicationType, String title, String isbnOrIssuenum) {
		Connection conn = cm.getConnection();
		Publication publication = null;
		List<Copy> copies = new ArrayList<>();

		String query = (title == null) ? GET_PUBLICATION_BY_TYPE_TITLE__ISBN_OR_ISSUENO
				: GET_PUBLICATION_BY_TYPE_TITLE__ISBN_OR_ISSUENO + "AND P.title=?";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, publicationType.toString().toLowerCase());
			statement.setString(2, isbnOrIssuenum);
			if (title != null) {
				statement.setString(3, title);
			}

			statement.executeQuery();
			System.out.println("Executed query: " + query);
			ResultSet resultSet = statement.getResultSet();

			publication = setPublicationCopies(publication, copies, resultSet);

			if (publicationType.equals(PublicationType.BOOK) && publication != null) {
				((Book) publication).setAuthorList(authorDAO.getAuthorsByPubId(publication.getId()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		cm.closeConnection(conn);
		return publication;
	}

	@Override
	public List<Book> getBooksByAuthorId(int authorId) {
		Connection conn = cm.getConnection();
		List<Book> books = new ArrayList<>();

		try {
			PreparedStatement statement = conn.prepareStatement(GET_BOOKS_AUTHOR_ID);
			statement.setInt(1, authorId);

			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();

			while (resultSet.next()) {
				Book book = new Book(resultSet.getInt("id"), resultSet.getString("isbn_issuenum").trim(),
						resultSet.getString("title").trim(), resultSet.getInt("maxCheckoutLength"));
				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

}
