package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Address;
import mpp.library.model.LibraryMember;
import mpp.library.model.dao.AddressDAO;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;

/**
 * @author Toan Quach
 */
public class MemberDAODBImpl implements MemberDAO {

	public static final String SELECT_STATEMENT = "SELECT m.id,m.memberid, m.firstname, m.lastname, m.telephone, "
			+ "a.street, a.city, a.state, a.zip " + "FROM LibraryMember m INNER JOIN Address a ON m.addressId = a.Id";

	private ConnectionManager cm;

	public MemberDAODBImpl() {
		cm = ConnectionManager.getInstance();
	}

	@Override
	public LibraryMember save(LibraryMember member) {
		Address address = saveAddressInfo(member);
		return saveMemberInfo(member, address.getId());
	}

	private Address saveAddressInfo(LibraryMember member) {
		AddressDAO addressDAO = new AddressDAODBImpl();
		return addressDAO.save(member.getAddress());
	}

	private LibraryMember saveMemberInfo(LibraryMember member, int addressId) {
		LibraryMember memberResult = member;
		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildInsertMember(conn, member);
			statement.setInt(2, addressId);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				memberResult.setId(rs.getInt(1));
				return memberResult;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private PreparedStatement buildInsertMember(Connection conn, LibraryMember member) throws SQLException {
		String sql = "INSERT INTO LibraryMember(memberid, addressid, firstname, lastname, telephone) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, member.getMemberId());
		// the second argument will be set with the generated address id
		statement.setString(3, member.getFirstName());
		statement.setString(4, member.getLastName());
		statement.setString(5, member.getPhone());

		return statement;
	}

	@Override
	public LibraryMember get(int id) {
		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildSelectMemberById(conn, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				LibraryMember member = buildLibraryMember(rs);
				return member;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private LibraryMember buildLibraryMember(ResultSet rs) throws SQLException {
		Address address = new Address(rs.getString("street"), rs.getString("city"), rs.getString("state"),
				rs.getInt("zip"));
		LibraryMember member = new LibraryMember(rs.getString("memberid"), rs.getString("firstname"),
				rs.getString("lastname"), rs.getString("telephone"), address);
		member.setId(rs.getInt("id"));
		return member;
	}

	private PreparedStatement buildSelectMemberById(Connection conn, int id) throws SQLException {
		if (id != -1) {
			String sql = SELECT_STATEMENT + " WHERE id=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			return statement;
		}

		return conn.prepareStatement(SELECT_STATEMENT);
	}

	@Override
	public List<LibraryMember> getList() {
		List<LibraryMember> memberList = new ArrayList<LibraryMember>();

		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildSelectMemberById(conn, -1);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				LibraryMember member = buildLibraryMember(rs);
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return memberList;
	}

	@Override
	public boolean update(LibraryMember member) {
		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildUpdateMember(conn, member);
			int memberResult = statement.executeUpdate();

			AddressDAO addressDAO = new AddressDAODBImpl();
			boolean addressResult = addressDAO.update(member.getAddress());

			return ((memberResult == 1) && addressResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private PreparedStatement buildUpdateMember(Connection conn, LibraryMember member) throws SQLException {
		String sql = "UPDATE LibraryMember SET firstname=?, lastname=?, telephone=? WHERE memberid=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, member.getFirstName());
		statement.setString(2, member.getLastName());
		statement.setString(3, member.getPhone());
		statement.setString(4, member.getMemberId());

		return statement;
	}

	@Override
	public LibraryMember getByMemberId(String memberId) {
		try (Connection conn = cm.getConnection()) {
			PreparedStatement statement = buildSelectMemberByMemberId(conn, memberId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				LibraryMember member = buildLibraryMember(rs);
				return member;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private PreparedStatement buildSelectMemberByMemberId(Connection conn, String memberId) throws SQLException {
		String sql = SELECT_STATEMENT + " WHERE memberid=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, memberId);

		return statement;
	}

	@Override
	public String geneateMemberId() {
		int maxId = -1;

		try (Connection conn = cm.getConnection()) {
			maxId = getMaxId(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (maxId < 0) {
			return "1001";
		}

		maxId++;
		return String.valueOf(maxId);
	}

	private int getMaxId(Connection conn) throws SQLException {
		String sql = "SELECT max(id) FROM LibraryMember";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs.next()) {
			return rs.getInt(1);
		}

		return -1;
	}
}
