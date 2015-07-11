package mpp.library.model.dao.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import mpp.library.util.LibraryConstant;
import mpp.library.util.PropertiesUtil;

/**
 * @author Toan Quach
 */
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
}
