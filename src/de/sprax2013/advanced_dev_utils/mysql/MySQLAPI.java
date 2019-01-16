package de.sprax2013.advanced_dev_utils.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TimeZone;

public class MySQLAPI {
	private static HashMap<String, Connection> connections = new HashMap<>();

	/**
	 * Returns a MySQL-Connection.<br>
	 * The Connection object will be cached!<br>
	 * Param <i>autoReconnect</i> is not set! Please use this API to get a
	 * connection. You'll get a cached version if the connection is still active or
	 * a new one if not
	 */
	public static Connection getConnection(String host, int port, String username, String password, String database) {
		Connection con = connections.get(host.toLowerCase() + port + username.toLowerCase() + database);
		try {
			if (con == null || con.isClosed() || !con.isValid(0)) {
				Class.forName("com.mysql.cj.jdbc.Driver");

				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database
						+ "?useSSL=false&serverTimezone=" + TimeZone.getDefault().getID(), username, password);

				connections.put(host.toLowerCase() + port + username.toLowerCase() + database, con);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return con;
	}

	/**
	 * Closes all cached connections and removes them from the cache
	 */
	public static void deInit() {
		if (!connections.isEmpty()) {
			for (Connection con : connections.values()) {
				try {
					if (con != null && !con.isClosed()) {
						con.close();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

			connections.clear();
		}
	}
}