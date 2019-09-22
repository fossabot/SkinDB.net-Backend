package de.sprax2013.skindb.backend.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import de.sprax2013.skindb.backend.Main;
import de.sprax2013.skindb.backend.constructors.Pending;
import de.sprax2013.skindb.backend.constructors.PendingStatus;
import de.sprax2013.skindb.backend.constructors.Skin;

public class DatabaseUtils {
	public static Pending getNextPending() {
		Pending result = null;

		try (PreparedStatement pS = getConnection().prepareStatement(
				"SELECT * FROM \"Queue\" WHERE \"SkinID\" IS NULL AND \"Status\" = 'QUEUED' LIMIT 1;")) {
			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				Integer skinID = rs.getInt("SkinID");
				if (rs.wasNull()) {
					skinID = null;
				}

				result = new Pending(rs.getInt("ID"), rs.getString("Data"), skinID, rs.getString("UserAgent"),
						PendingStatus.valueOf(rs.getString("Status")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static void setPending(Pending pending) {
		if (!pending.hasID())
			throw new IllegalStateException();

		try (PreparedStatement pS = getConnection()
				.prepareStatement("UPDATE \"Queue\" SET \"Status\"=?::\"QueueStatus\", \"SkinID\"=? WHERE \"ID\"=?;")) {
			pS.setString(1, pending.getStatus().name());

			if (pending.hasSkinID()) {
				pS.setInt(2, pending.getSkinID());
			} else {
				pS.setNull(2, Types.NULL);
			}

			pS.setInt(3, pending.getID());

//			pS.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static Integer getDuplicate(String hash) {
		Integer result = null;

		try (PreparedStatement pS = getConnection().prepareStatement(
				"SELECT \"ID\" FROM \"Skins\" WHERE \"CleanHash\" = ? AND \"DuplicateOf\" IS NULL LIMIT 1;")) {
			pS.setString(1, hash);

			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				result = rs.getInt("ID");

				if (rs.wasNull()) {
					result = null;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static Skin getSkin(boolean uniqueOnly, int offset) {
		try (PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM \"Skins\""
				+ (uniqueOnly ? " WHERE \"DuplicateOf\" IS NULL" : "") + " LIMIT 1 OFFSET ?;")) {
			pS.setInt(1, offset);

			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				Integer duplicateOf = rs.getInt("DuplicateOf");
				if (rs.wasNull()) {
					duplicateOf = null;
				}

				return new Skin(rs.getInt("ID"), rs.getString("MojangURL"), rs.getString("CleanHash"),
						rs.getBoolean("HasOverlay"), !rs.getBoolean("IsAlex"), duplicateOf, rs.getTimestamp("Added"));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static Skin getSkin(String hash) {
		try (PreparedStatement pS = getConnection()
				.prepareStatement("SELECT * FROM \"Skins\" WHERE \"CleanHash\" = ? LIMIT 1;")) {
			pS.setString(1, hash);

			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				Integer duplicateOf = rs.getInt("DuplicateOf");
				if (rs.wasNull()) {
					duplicateOf = null;
				}

				return new Skin(rs.getInt("ID"), rs.getString("MojangURL"), rs.getString("CleanHash"),
						rs.getBoolean("HasOverlay"), !rs.getBoolean("IsAlex"), duplicateOf, rs.getTimestamp("Added"));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static void setSkin(Skin skin) {
		try (PreparedStatement pS = getConnection().prepareStatement(
				"INSERT INTO \"Skins\"(\"ID\",\"MojangURL\",\"CleanHash\",\"HasOverlay\",\"IsAlex\",\"DuplicateOf\",\"Added\") VALUES ("
						+ (!skin.hasID() ? "DEFAULT" : "?") + ",?,?,?,?,?,"
						+ (skin.getKnownSince() == null ? "DEFAULT" : "?") + ");")) {
			int offset = skin.hasID() ? 0 : 1;

			if (skin.hasID()) {
				pS.setInt(1, skin.getID());
			}

			pS.setString(2 - offset, skin.getMojangURL());
			pS.setString(3 - offset, skin.getCleanHash());

			pS.setBoolean(4 - offset, skin.hasOverlay());

			pS.setBoolean(5 - offset, !skin.hasSteveArms());

			if (skin.isDuplicate()) {
				pS.setInt(6 - offset, skin.getDuplicateOf());
			} else {
				pS.setNull(6 - offset, Types.NULL);
			}

			if (skin.getKnownSince() != null) {
				pS.setTimestamp(7 - offset, skin.getKnownSince());
			}

//			pS.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean canConnect() {
		return getConnection() != null /* && createTables() */;
	}

	private static Connection con;

	private static Connection getConnection() {
		try {
			if (con == null || con.isClosed()) {
				Properties props = Main.getConfig();
				con = DriverManager.getConnection(
						"jdbc:postgresql://" + props.getProperty("Host") + ":" + props.getProperty("Port") + "/"
								+ props.getProperty("Database") + "?user=" + props.getProperty("Username")
								+ "&password=" + props.getProperty("Password") + "&ssl=true&sslmode=require");

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return con;
	}
}