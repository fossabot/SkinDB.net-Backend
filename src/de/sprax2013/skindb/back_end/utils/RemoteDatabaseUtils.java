package de.sprax2013.skindb.back_end.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import OLD.de.sprax2013.mc_skin_db.skin.Skin;
import OLD.de.sprax2013.mc_skin_db.skin.Tag;
import de.sprax2013.advanced_dev_utils.mysql.MySQLAPI;

public class RemoteDatabaseUtils {
	private static final String PREFIX = "mcDB_";

	/* Tags */

	public static List<Tag> getTags() {
		List<Tag> result = new ArrayList<>();

		try (PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM `" + PREFIX + "Tags`;")) {
			ResultSet rs = pS.executeQuery();

			while (rs.next()) {
				Set<String> synonyms = new HashSet<>();
				String synonymStr = rs.getString("Synonyms");

				if (synonymStr != null) {
					synonyms.addAll(Arrays.asList(synonymStr.split(",")));
				}

				Set<Integer> similar = new HashSet<>();
				String similarStr = rs.getString("Similar");

				if (similarStr != null) {
					for (String s : similarStr.split(",")) {
						if (!s.trim().isEmpty()) {
							try {
								similar.add(Integer.parseInt(s));
							} catch (NumberFormatException ex) {
								ex.printStackTrace();
							}
						}
					}
				}

				result.add(new Tag(rs.getInt("ID"), rs.getString("Identifier"), synonyms, similar));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static boolean setTag(Tag tag) {
		boolean result = false;

		try (PreparedStatement pS = getConnection().prepareStatement(
				"INSERT INTO `" + PREFIX + "Tags`(`ID`,`Identifier`,`Synonyms`,`Similar`) VALUES (?,?,?,?)")) {
			if (tag.getID() == -1) {
				pS.setNull(1, Types.NULL);
			} else {
				pS.setInt(1, tag.getID());
			}

			pS.setString(2, tag.getIdentifier());

			if (tag.getSynonyms().isEmpty()) {
				pS.setNull(3, Types.NULL);
			} else {
				String synonyms = null;

				for (String synonym : tag.getSynonyms()) {
					if (synonyms == null) {
						synonyms = synonym;
					} else {
						synonyms += synonym + ",";
					}
				}

				pS.setString(3, synonyms);
			}

			if (tag.getSimilar().isEmpty()) {
				pS.setNull(4, Types.NULL);
			} else {
				String similars = null;

				for (Integer similar : tag.getSimilar()) {
					similars += similar + ",";
				}

				pS.setString(4, similars);
			}

			pS.execute();

			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	/* Skins */

	public static List<Skin> getSkins() {
		List<Skin> result = new ArrayList<>();

		try (PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM `" + PREFIX + "Skins`;")) {
			ResultSet rs = pS.executeQuery();

			while (rs.next()) {
				String tagsString = rs.getString("Tags");
				Set<Integer> tags = new HashSet<>();

				if (tagsString != null && !tagsString.isEmpty()) {
					for (String s : tagsString.split(",")) {
						try {
							tags.add(Integer.parseInt(s));
						} catch (NumberFormatException ex) {
							ex.printStackTrace();
						}
					}
				}

				Integer duplicate = rs.getInt("Duplicate");

				if (rs.wasNull()) {
					duplicate = null;
				}

				result.add(
						new Skin(rs.getInt("ID"), rs.getString("SkinURL"), rs.getString("FileHash"), tags, duplicate));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static boolean setSkin(Skin skin) {
		boolean result = false;

		try (PreparedStatement pS = getConnection().prepareStatement(
				"REPLACE INTO `" + PREFIX + "Skins`(`ID`,`SkinURL`,`FileHash`,`Tags`,`Duplicate`)VALUES(?,?,?,?,?);")) {
			if (skin.getID() == -1) {
				pS.setNull(1, Types.NULL);
			} else {
				pS.setInt(1, skin.getID());
			}

			pS.setString(2, skin.getURL());
			pS.setString(3, skin.getFileHash());

			if (skin.getTags().isEmpty()) {
				pS.setNull(4, Types.NULL);
			} else {
				String tags = "";

				for (int tagID : skin.getTags()) {
					tags += tagID + ",";
				}

				if (tags.isEmpty()) {
					pS.setNull(4, Types.NULL);
				} else {
					pS.setString(4, tags);
				}
			}

			if (skin.hasDuplicate()) {
				pS.setInt(5, skin.getDuplicate());
			} else {
				pS.setNull(5, Types.NULL);
			}

			pS.execute();

			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static boolean isInDatabase(String skinURL) {
		boolean result = false;

		try (PreparedStatement pS = getConnection()
				.prepareStatement("SELECT `ID` FROM `" + PREFIX + "Skins` WHERE `SkinURL` LIKE ?;")) {
			pS.setString(1, skinURL);

			ResultSet rs = pS.executeQuery();

			result = rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static Integer getDuplicate(String fileHash) {
		Integer result = null;

		try (PreparedStatement pS = getConnection().prepareStatement(
				"SELECT `ID` FROM `" + PREFIX + "Skins` WHERE `FileHash` LIKE ? AND `Duplicate` IS NULL;")) {

			pS.setString(1, fileHash);

			ResultSet rs = pS.executeQuery();

			if (rs.next()) {
				result = rs.getInt("ID");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static boolean creatTables() {
		boolean errored = false;

		try (PreparedStatement pS = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + PREFIX
				+ "Tags`(`ID` INT NOT NULL AUTO_INCREMENT," + "`Identifier` TINYTEXT NOT NULL,"
				+ "`Synonyms` TEXT NULL DEFAULT NULL," + "`Similar` TEXT NULL DEFAULT NULL," + "PRIMARY KEY (`ID`)"
				+ ")ENGINE = InnoDB CHARSET=utf8 COLLATE utf8_unicode_ci;")) {
			pS.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();

			errored = true;
		}

		try (PreparedStatement pS = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + PREFIX
				+ "Skins`(`ID` INT NOT NULL AUTO_INCREMENT," + "`SkinURL` VARCHAR(150) NOT NULL,"
				+ "`FileHash` VARCHAR(64) NOT NULL," + "`Tags` TEXT NULL DEFAULT NULL,"
				+ "`Duplicate` INT NULL DEFAULT NULL," + "PRIMARY KEY (`ID`), UNIQUE (`SkinURL`)"
				+ ")ENGINE = InnoDB CHARSET=utf8 COLLATE utf8_unicode_ci;")) {
			pS.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();

			errored = true;
		}

		if (errored) {
			System.out.println("[ERROR] Die benötigten Datenbank-Tabellen konnten nicht erstellt werden!");
		}

		return !errored;
	}

	private static Connection getConnection() {
		return MySQLAPI.getConnection("193.31.25.193", 3306, "sprax2013",
				"GnyQB=l3hX=A#rlK*covABJC;v#3Lk6PosJBK%RSK^5$h", "sprax2013_mc");
	}
}