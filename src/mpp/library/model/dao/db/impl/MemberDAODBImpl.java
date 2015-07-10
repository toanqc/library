package mpp.library.model.dao.db.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Address;
import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.db.connection.ConnectionManager;

public class MemberDAODBImpl implements MemberDAO {

	private static final String UPDATE_STATEMENT = "UPDATE FROM ";
	public static final String INSERT_STATEMENT = "INSERT INTO ";
	public static final String SELECT_STATEMENT = "SELECT * FROM ";

	private ConnectionManager cm;

	public MemberDAODBImpl() {
		cm = ConnectionManager.getInstance();
	}

	@Override
	public void save(LibraryMember member) {
		Connection conn = cm.getConnection();

		String addressSql = buildSelectMemberSql(member);
		int addressId = cm.executeSave(conn, addressSql);
		String memberSql = buildInsertMemberSql(member, addressId);
		cm.executeSave(conn, memberSql);

		cm.closeConnection(conn);
	}

	private String buildInsertMemberSql(LibraryMember member, int addressId) {
		StringBuilder sql = new StringBuilder();
		sql.append(INSERT_STATEMENT);
		sql.append(EntityType.MEMBER.getValue());
		sql.append(" VALUES(");
		sql.append("'").append(member.getFirstName()).append("'").append(" ,");
		sql.append("'").append(member.getLastName()).append("'").append(" ,");
		sql.append(addressId).append(" ,");
		sql.append("'").append(member.getPhone()).append("'");
		sql.append(")");

		return sql.toString();
	}

	private String buildSelectMemberSql(LibraryMember member) {
		StringBuilder sql = new StringBuilder();
		sql.append(INSERT_STATEMENT);
		sql.append(EntityType.ADDRESS.getValue());
		sql.append(" VALUES(");
		sql.append("'").append(member.getAddress().getStreet()).append("'").append(" ,");
		sql.append("'").append(member.getAddress().getCity()).append("'").append(" ,");
		sql.append("'").append(member.getAddress().getState()).append("'").append(" ,");
		sql.append("'").append(member.getAddress().getZip()).append("'");
		sql.append(")");

		return sql.toString();
	}

	@Override
	public LibraryMember get(String id) {
		try {
			Connection conn = cm.getConnection();
			String memberSql = buildSelectMemberSql(id);
			ResultSet rs = cm.executeQuery(conn, memberSql);

			if (rs.next()) {
				LibraryMember member = buildLibraryMember(rs);
				return member;
			}
			cm.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private LibraryMember buildLibraryMember(ResultSet rs) throws SQLException {
		Address address = new Address(rs.getString("street"), rs.getString("city"), rs.getString("state"),
				rs.getInt("zip"));
		LibraryMember member = new LibraryMember(rs.getInt("memberId"), rs.getString("firstName"),
				rs.getString("lastNameName"), rs.getString("phone"), address);
		return member;
	}

	private String buildSelectMemberSql(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT m.memberId, m.firstName, m.lastName, m.phone, a.street, a.city, a.state, a.zip FROM LibraryMember m, Address a");
		sql.append(" WHERE AND m.addressId = a.addressId");
		if (id != null && !"".equals(id)) {
			sql.append(" memberId=");
			sql.append(id);
		}

		return sql.toString();
	}

	@Override
	public List<LibraryMember> getList() {
		List<LibraryMember> memberList = new ArrayList<LibraryMember>();
		try {
			Connection conn = cm.getConnection();
			String memberSql = buildSelectMemberSql("");
			ResultSet rs = cm.executeQuery(conn, memberSql);
			while (rs.next()) {
				LibraryMember member = buildLibraryMember(rs);
				memberList.add(member);
			}
			cm.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return memberList;
	}

	@Override
	public boolean update(LibraryMember member) {
		Connection conn = cm.getConnection();
		String memberUpdateSql = buildUpdateMemberSql(member);
		boolean memberResult = cm.executeUpdate(conn, memberUpdateSql);

		String addressUpdateSql = builderUpdateAddressSql(member.getAddress());
		boolean addressResult = cm.executeUpdate(conn, addressUpdateSql);

		cm.closeConnection(conn);
		if (memberResult && addressResult) {
			return true;
		}

		return false;
	}

	private String builderUpdateAddressSql(Address address) {
		StringBuilder sql = new StringBuilder();
		sql.append(UPDATE_STATEMENT);
		sql.append(EntityType.MEMBER.getValue());
		sql.append(" SET ");
		sql.append("street=").append("'").append(address.getStreet()).append("' ,");
		sql.append("city=").append("'").append(address.getCity()).append("' ,");
		sql.append("state=").append("'").append(address.getState()).append("' ,");
		sql.append("zip=").append("'").append(address.getZip()).append("' ,");

		return sql.toString();
	}

	private String buildUpdateMemberSql(LibraryMember member) {
		StringBuilder sql = new StringBuilder();
		sql.append(UPDATE_STATEMENT);
		sql.append(EntityType.MEMBER.getValue());
		sql.append(" SET ");
		sql.append("firstName=").append("'").append(member.getFirstName()).append("' ,");
		sql.append("lastName=").append("'").append(member.getLastName()).append("' ,");
		sql.append("phone=").append("'").append(member.getPhone()).append("' ,");
		sql.append(" WHERE memberId=").append(member.getMemberId());

		return sql.toString();
	}
}
