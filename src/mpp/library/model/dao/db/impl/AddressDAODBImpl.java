package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mpp.library.model.Address;
import mpp.library.model.dao.AddressDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;

/**
 * @author Toan Quach
 */
public class AddressDAODBImpl implements AddressDAO {

	private ConnectionManager cm;

	public AddressDAODBImpl() {
		cm = ConnectionManager.getInstance();
	}

	@Override
	public Address save(Address address) {
		Address addrResult = address;

		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildInsertAddressSql(conn, address);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				addrResult.setId(rs.getInt(1));
				return addrResult;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private PreparedStatement buildInsertAddressSql(Connection conn, Address address) throws SQLException {
		String sql = "INSERT INTO Address(street, city, state, zip) VALUES(?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, address.getStreet());
		statement.setString(2, address.getCity());
		statement.setString(3, address.getState());
		statement.setInt(4, address.getZip());

		return statement;
	}

	@Override
	public Address get(int id) {
		String sql = "SELECT street,city,state,zip,id FROM ADDRESS WHERE id =?";
		Address address = null;
		try (Connection conn = cm.getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			preparedStatement.executeQuery();
			ResultSet result = preparedStatement.getResultSet();
			if (result.next()) {
				address = new Address(result.getString("street").trim(), result.getString("city").trim(),
						result.getString("state").trim(), result.getInt("zip"));
				address.setId(result.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public boolean update(Address address) {
		try (Connection conn = cm.getConnection()) {
			int result = builderUpdateAddressSql(conn, address).executeUpdate();
			return result != 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private PreparedStatement builderUpdateAddressSql(Connection conn, Address address) throws SQLException {
		String sql = "UPDATE Address SET street=?, city=?, state=?, zip=? WHERE id=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, address.getStreet());
		statement.setString(2, address.getCity());
		statement.setString(3, address.getState());
		statement.setInt(4, address.getZip());
		statement.setInt(5, address.getId());

		return statement;
	}
}
