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

	private static final String INSERT_AUTHOR = "INSERT INTO AUTHOR(ADDRESSID,TELEPHONE,FIRSTNAME,LASTNAME,BIO) VALUES(?,?,?,?,?)";
	private static final String SELECT_AUTHOR = "SELECT AU.CREDENTIALS,AU.ADDRESSID,AU.TELEPHONE, AU.FIRSTNAME, AU.LASTNAME, AU.BIO, AD.STREET, AD.CITY, AD.STATE, AD.ZIP FROM AUTHOR AS AU JOIN ADDRESS AS AD ON AU.ADDRESSID=AS.ID";
	private static final String SELECT_AUTHORS_BY_PUB_ID = "SELECT AU.ID,AU.ADDRESSID,AU.TELEPHONE, AU.FIRSTNAME, AU.LASTNAME, AU.BIO FROM AUTHOR AS AU JOIN PUBLICATIONAUTHOR AS PA ON AU.ID=PA.AUTHORID AND PA.PUBID=?";

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

		Connection conn = cm.getConnection();

		Author author = null;
		try {
			PreparedStatement statement = conn.prepareStatement(SELECT_AUTHOR);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();

			Address address = new Address(resultSet.getString("street").trim(), resultSet.getString("city").trim(),
					resultSet.getString("state").trim(), resultSet.getInt("zip"));
			address.setId(resultSet.getInt("id"));

			author = new Author();
			author.setAddress(address);
			author.setAddressId(address.getId());
			author.setBio(resultSet.getString("bio").trim());
			author.setCredentials(resultSet.getString("credentials").trim());
			author.setFirstName(resultSet.getString("firstName").trim());
			author.setId(id);
			author.setLastName(resultSet.getString("lastName").trim());
			author.setPhone(resultSet.getString("phone").trim());

			// Get Books
			PublicationDAO publicationDAO = new PublicationDAODBImpl();
			author.setBooks(publicationDAO.getBooksByAuthorId(author.getID()));
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
				author.setAddressId(address.getId());
				author.setBio(resultSet.getString("bio"));
				author.setFirstName(resultSet.getString("firstName"));
				author.setId(resultSet.getInt("id"));
				author.setLastName(resultSet.getString("lastName"));
				author.setPhone(resultSet.getString("telephone"));

				// Get Books
				PublicationDAO publicationDAO = new PublicationDAODBImpl();
				author.setBooks(publicationDAO.getBooksByAuthorId(author.getID()));
				authors.add(author);
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return authors;
	}

}
