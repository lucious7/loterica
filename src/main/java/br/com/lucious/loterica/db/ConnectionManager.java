package br.com.lucious.loterica.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private final static String URL = "jdbc:derby:database";
	private static Connection conn = null;
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (conn == null) {
			conn = DriverManager.getConnection(URL + ";create=true");
		}
		return conn;
	}

	public static void closeConnection() throws SQLException {
		if (conn != null) {
			try {
				DriverManager.getConnection(URL + ";shutdown=true");
			} catch (SQLException e) {
				if (e.getErrorCode() != 45000 || !"08006".equals(e.getSQLState())) {
					throw e;
				}
			}
			conn = null;
		}
	}
}
