package br.com.lucious.loterica.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DbUtil {

	public static void initDB(Connection conn) throws SQLException {
		if (conn != null) {
			for (Map.Entry<String, String> script : Tables.getScripts().entrySet()) {
				if (!tableExists(script.getKey(), conn)) {
					System.out.println("CRIANDO TABELA " + script.getKey());
					conn.createStatement().execute(script.getValue());
					System.out.println("TABELA " + script.getKey() + " CRIADA!");
				}
			}
		}

	}

	public static boolean tableExists(String tableName, Connection conn) throws SQLException {
		if (conn != null) {
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);

			return rs.next();
		}
		return false;
	}

}
