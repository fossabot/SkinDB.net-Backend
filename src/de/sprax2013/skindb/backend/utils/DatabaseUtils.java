package de.sprax2013.skindb.backend.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import de.sprax2013.advanced_dev_utils.mysql.MySQLAPI;
import de.sprax2013.skindb.backend.Main;
import de.sprax2013.skindb.backend.constructors.Pending;
import de.sprax2013.skindb.backend.constructors.PendingStatus;
import de.sprax2013.skindb.backend.constructors.Skin;

public class DatabaseUtils {
//	public static boolean createTables() {
//		boolean errored = false;
//
//		try (PreparedStatement pS = getConnection().prepareStatement(
//				"CREATE TABLE IF NOT EXISTS `Pending`(" + "`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,"
//						+ "`SkinData` VARCHAR(255) NOT NULL COMMENT 'SkinURL or UUID' COLLATE 'utf8_unicode_ci',"
//						+ "`SkinID` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT 'SkinID on success',"
//						+ "`UserAgent` TINYTEXT NULL COLLATE 'utf8_unicode_ci',"
//						+ "`Status` INT(11) NULL DEFAULT NULL COMMENT '0 and 1 are Non-Err'," + "PRIMARY KEY (`ID`),"
//						+ "INDEX `SkinID` (`SkinID`)" + ")COLLATE='utf8_unicode_ci' ENGINE=InnoDB;")) {
//			pS.execute();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//
//			errored = true;
//		}
//
//		if (!errored) {
//			try (PreparedStatement pS = getConnection().prepareStatement(
//					"CREATE TABLE IF NOT EXISTS `Skins`(" + "`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,"
//							+ "`MojangURL` VARCHAR(150) NOT NULL COLLATE 'utf8_unicode_ci',"
//							+ "`Hash` VARCHAR(64) NOT NULL COMMENT 'SHA-256' COLLATE 'utf8_unicode_ci',"
//							+ "`4px-Arms` TINYINT(1) UNSIGNED NULL DEFAULT NULL,"
//							+ "`DuplicateOf` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT 'ID'," + "PRIMARY KEY (`ID`),"
//							+ "UNIQUE INDEX `MojangURL` (`MojangURL`)" + ")COLLATE='utf8_unicode_ci' ENGINE=InnoDB;")) {
//				pS.execute();
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//
//				errored = true;
//			}
//		}
//
//		if (!errored) {
//			try (PreparedStatement pS = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `SkinTags`("
//					+ "`Skin` INT(10) UNSIGNED NOT NULL," + "`Tag` INT(10) UNSIGNED NOT NULL,"
//					+ "PRIMARY KEY (`Skin`, `Tag`)" + ")COLLATE='utf8_unicode_ci' ENGINE=InnoDB;")) {
//				pS.execute();
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//
//				errored = true;
//			}
//		}
//
//		if (!errored) {
//			try (PreparedStatement pS = getConnection().prepareStatement(
//					"CREATE TABLE IF NOT EXISTS `TagCategory`(" + "`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,"
//							+ "`Designation` VARCHAR(125) NOT NULL COLLATE 'utf8_unicode_ci'," + "PRIMARY KEY (`ID`)"
//							+ ")COLLATE='utf8_unicode_ci' ENGINE=InnoDB;")) {
//				pS.execute();
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//
//				errored = true;
//			}
//		}
//
//		if (!errored) {
//			try (PreparedStatement pS = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Tags`("
//					+ "`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,"
//					+ "`Designation` VARCHAR(255) NOT NULL COMMENT 'Bezeichnung' COLLATE 'utf8_unicode_ci',"
//					+ "`Category` INT(10) UNSIGNED NULL DEFAULT NULL,PRIMARY KEY (`ID`),"
//					+ "UNIQUE INDEX `Designation` (`Designation`)" + ")COLLATE='utf8_unicode_ci' ENGINE=InnoDB;")) {
//				pS.execute();
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//
//				errored = true;
//			}
//		}
//
//		return !errored;
//	}

	public static Pending getNextPending() {
		Pending result = null;

		try (PreparedStatement pS = getConnection()
				.prepareStatement("SELECT * FROM `Pending` WHERE `SkinID` IS NULL AND `Status` IS NULL LIMIT 1;")) {
			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				Integer skinID = rs.getInt("SkinID");
				if (rs.wasNull()) {
					skinID = null;
				}

				Integer status = rs.getInt("Status");
				if (rs.wasNull()) {
					status = null;
				}

				result = new Pending(rs.getInt("ID"), rs.getString("SkinData"), skinID, rs.getString("UserAgent"),
						status == null ? null : PendingStatus.getByID(status));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static void setPending(Pending pending) {
		try (PreparedStatement pS = getConnection().prepareStatement(
				"REPLACE INTO `Pending`(`ID`,`SkinData`,`SkinID`,`UserAgent`,`Status`)VALUES(?,?,?,?,?);")) {
			if (pending.hasID()) {
				pS.setInt(1, pending.getID());
			} else {
				pS.setNull(1, Types.NULL);
			}

			pS.setString(2, pending.getData().toString());

			if (pending.hasSkinID()) {
				pS.setInt(3, pending.getSkinID());
			} else {
				pS.setNull(3, Types.NULL);
			}

			if (pending.hasUserAgent()) {
				pS.setString(4, pending.getUserAgent());
			} else {
				pS.setNull(4, Types.NULL);
			}

			if (pending.hasStatus()) {
				pS.setInt(5, pending.getStatus().getID());
			} else {
				pS.setNull(5, Types.NULL);
			}

			pS.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static Integer getDuplicate(String hash) {
		Integer result = null;

		try (PreparedStatement pS = getConnection()
				.prepareStatement("SELECT `ID` FROM `Skins` WHERE `CleanHash` = ? LIMIT 1;")) {
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
		try (PreparedStatement pS = getConnection().prepareStatement(
				"SELECT * FROM `Skins`" + (uniqueOnly ? " WHERE `DuplicateOf` IS NULL" : "") + " LIMIT 1 OFFSET ?;")) {
			pS.setInt(1, offset);

			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				Integer duplicateOf = rs.getInt("DuplicateOf");
				if (rs.wasNull()) {
					duplicateOf = null;
				}

				return new Skin(rs.getInt("ID"), rs.getString("MojangURL"), rs.getString("CleanHash"),
						rs.getBoolean("HasOverlay"), rs.getBoolean("HasSteveArms"), duplicateOf,
						rs.getTimestamp("KnownSince"));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static Skin getSkin(String hash) {
		try (PreparedStatement pS = getConnection()
				.prepareStatement("SELECT * FROM `Skins` WHERE `CleanHash` = ? LIMIT 1;")) {
			pS.setString(1, hash);

			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				Integer duplicateOf = rs.getInt("DuplicateOf");
				if (rs.wasNull()) {
					duplicateOf = null;
				}

				return new Skin(rs.getInt("ID"), rs.getString("MojangURL"), rs.getString("CleanHash"),
						rs.getBoolean("HasOverlay"), rs.getBoolean("HasSteveArms"), duplicateOf,
						rs.getTimestamp("KnownSince"));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static void setSkin(Skin skin) {
		try (PreparedStatement pS = getConnection().prepareStatement(
				"REPLACE INTO `Skins`(`ID`,`MojangURL`,`CleanHash`,`HasOverlay`,`HasSteveArms`,`DuplicateOf`,`KnownSince`)VALUES(?,?,?,?,?,?,?);")) {
			if (skin.hasID()) {
				pS.setInt(1, skin.getID());
			} else {
				pS.setNull(1, Types.NULL);
			}

			pS.setString(2, skin.getMojangURL());
			pS.setString(3, skin.getCleanHash());

			if (skin.hasOverlay()) {
				pS.setBoolean(4, skin.hasOverlay());
			} else {
				pS.setNull(4, Types.NULL);
			}

			if (skin.hasSteveArms()) {
				pS.setBoolean(5, skin.hasSteveArms());
			} else {
				pS.setNull(5, Types.NULL);
			}

			if (skin.isDuplicate()) {
				pS.setInt(6, skin.getDuplicateOf());
			} else {
				pS.setNull(6, Types.NULL);
			}

			if (skin.getKnownSince() != null) {
				pS.setTimestamp(7, skin.getKnownSince());
			} else {
				pS.setNull(7, Types.NULL);
			}

			pS.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean canConnect() {
		return getConnection() != null /* && createTables() */;
	}

	private static Connection getConnection() {
		Properties props = Main.getConfig();

		return MySQLAPI.getConnection(props.getProperty("MySQL.Host"), Integer.valueOf(props.getProperty("MySQL.Port")),
				props.getProperty("MySQL.Username"), props.getProperty("MySQL.Password"),
				props.getProperty("MySQL.Database"));
	}
}