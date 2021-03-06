package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Address;
import mpp.library.model.Author;
import mpp.library.model.dao.AddressDAO;
import mpp.library.model.dao.AuthorDAO;
import mpp.library.model.dao.db.PublicationDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;

public class AuthorDAODBImpl implements AuthorDAO {

	private static final String INSERT_AUTHOR = "INSERT INTO AUTHOR(ADDRESSID, TELEPHONE, FIRSTNAME, LASTNAME, BIO) "
			+ "VALUES (?, ?, ?, ?, ?)";
	private static final String SELECT_AUTHORS_BY_PUB_ID = "SELECT AU.ID,AU.ADDRESSID,AU.TELEPHONE, AU.FIRSTNAME, AU.LASTNAME, AU.BIO "
			+ "FROM AUTHOR AS AU JOIN PUBLICATIONAUTHOR AS PA ON AU.ID=PA.AUTHORID AND PA.PUBID=?";

	public static final String SELECT_STATEMENT = "SELECT a.id as id, a.firstname, a.lastname, a.telephone, "
			+ "a.bio, addr.id as addrid, addr.street, addr.city, addr.state, addr.zip "
			+ "FROM Author a INNER JOIN Address addr ON a.addressId = addr.Id";

	private ConnectionManager cm;
	private AddressDAO addressDAO;

	public AuthorDAODBImpl() {
		cm = ConnectionManager.getInstance();
		addressDAO = new AddressDAODBImpl();
	}

	@Override
	public Author save(Author t) {

		Connection conn = cm.getConnection();

		try {
			// Saving address
			Address address = addressDAO.save(t.getAddress());

			// Saving Author
			PreparedStatement statement = conn.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, address.getId());
			statement.setString(2, t.getPhone());
			statement.setString(3, t.getFirstName());
			statement.setString(4, t.getLastName());
			statement.setString(5, t.getBio());

			System.out.println("Executing query: " + INSERT_AUTHOR);
			statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				t.setId(resultSet.getInt(1));
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return t;
	}

	@Override
	public Author get(int id) {

		Author author = null;
		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildSelectAuthorById(conn, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				author = createAuthor(resultSet);
				// Get Books
				PublicationDAO publicationDAO = new PublicationDAODBImpl();
				author.setBooks(publicationDAO.getBooksByAuthorId(author.getId()));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return author;
	}

	@Override
	public List<Author> getAuthorsByPubId(int pubId) {
		Connection conn = cm.getConnection();
		List<Author> authors = new ArrayList<>();
		Author author = null;
		try {
			PreparedStatement statement = conn.prepareStatement(SELECT_AUTHORS_BY_PUB_ID);
			statement.setInt(1, pubId);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();

			while (resultSet.next()) {
				Address address = addressDAO.get(resultSet.getInt("addressID"));

				author = new Author();
				author.setAddress(address);
				author.setBio(resultSet.getString("bio"));
				author.setFirstName(resultSet.getString("firstName"));
				author.setId(resultSet.getInt("id"));
				author.setLastName(resultSet.getString("lastName"));
				author.setPhone(resultSet.getString("telephone"));

				// Get Books
				PublicationDAO publicationDAO = new PublicationDAODBImpl();
				author.setBooks(publicationDAO.getBooksByAuthorId(author.getId()));
				authors.add(author);
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return authors;
	}

	@Override
	public List<Author> getList() {
		List<Author> authorList = new ArrayList<Author>();

		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildSelectAuthorById(conn, -1);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Author author = createAuthor(rs);
				authorList.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return authorList;
	}

	private Author createAuthor(ResultSet rs) throws SQLException {
		Address address = new Address(rs.getString("street"), rs.getString("city"), rs.getString("state"),
				rs.getInt("zip"));
		address.setId(rs.getInt("addrid"));
		Author author = new Author(rs.getString("firstname"), rs.getString("lastname"), rs.getString("telephone"),
				rs.getString("bio"), address);
		author.setId(rs.getInt("id"));
		return author;
	}

	private PreparedStatement buildSelectAuthorById(Connection conn, int id) throws SQLException {
		if (id != -1) {
			String sql = SELECT_STATEMENT + " WHERE a.id=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			return statement;
		}

		return conn.prepareStatement(SELECT_STATEMENT);
	}

	@Override
	public boolean update(Author author) {
		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildUpdateAuthor(conn, author);
			int authorResult = statement.executeUpdate();

			AddressDAO addressDAO = new AddressDAODBImpl();
			boolean addressResult = addressDAO.update(author.getAddress());

			return ((authorResult == 1) && addressResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private PreparedStatement buildUpdateAuthor(Connection conn, Author author) throws SQLException {
		String sql = "UPDATE Author SET firstname=?, lastname=?, telephone=?, bio=? WHERE id=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, author.getFirstName());
		statement.setString(2, author.getLastName());
		statement.setString(3, author.getPhone());
		statement.setString(4, author.getBio());
		statement.setInt(5, author.getId());

		return statement;
	}

	@Override
	public int geneateAuthorId() {
		int maxId = -1;

		try (Connection conn = cm.getConnection()) {
			maxId = getMaxId(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (maxId < 0) {
			return 1001;
		}

		maxId++;
		return maxId;
	}

	private int getMaxId(Connection conn) throws SQLException {
		String sql = "SELECT max(id) FROM Author";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs.next()) {
			return rs.getInt(1);
		}

		return -1;
	}
}
