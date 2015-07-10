package mpp.library.model.dao.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import mpp.library.util.LibraryConstant;
import mpp.library.util.PropertiesUtil;

public class ConnectionManager {

	private static final String DATABASE = "ij.database";
	private static final String USERNAME = "ij.username";
	private static final String PASSWORD = "ij.password";

	private String databaseUrl;
	private String user;
	private String pass;

	private Connection conn = null;
	private static ConnectionManager instance = null;

	private ConnectionManager() {
		Properties props = PropertiesUtil.getProperties(LibraryConstant.CONFIG_FILE);
		databaseUrl = props.getProperty(DATABASE);
		user = props.getProperty(USERNAME);
		pass = props.getProperty(PASSWORD);
	}

	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(databaseUrl, user, pass);
			System.out.println("Got connection...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Execute an insert statement then return true if the execution is
	 * successfully
	 * 
	 * @param conn
	 *            Connection
	 * @param sql
	 *            the SQL statement
	 * @return true if the execution is successfully
	 */
	public int executeSave(Connection conn, String sql) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = statement.getResultSet();
			if (resultSet.next()) {
				return resultSet.getInt(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public boolean executeUpdate(Connection conn, String sql) {
		try {
			Statement statement = conn.createStatement();
			int result = statement.executeUpdate(sql);
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Execute a select statement then return the result set
	 * 
	 * @param conn
	 *            Connection
	 * @param sql
	 *            the select SQL statement
	 * @return the result set
	 */
	public ResultSet executeQuery(Connection conn, String sql) {
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet != null) {
				return resultSet;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
