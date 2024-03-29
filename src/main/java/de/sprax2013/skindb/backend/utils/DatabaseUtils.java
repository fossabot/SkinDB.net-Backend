package de.sprax2013.skindb.backend.utils;

import de.sprax2013.skindb.backend.queue.QueueObject;
import de.sprax2013.skindb.backend.queue.QueueStatus;
import de.sprax2013.skindb.backend.skins.Skin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DatabaseUtils {
    private static final String HOST = "localhost", PORT = "5432",
            USERNAME = "SpraxAPI", PASSWORD = "6573071046f547205c511eeae9e928b8d8ad94e649590683e8c8617c884a69b1",
            DATABASE = "SkinDB";

    private static Connection con;

    public static void deInit() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            con = null;
        }
    }

    /* Queue */

    public static QueueObject getNextQueued() {
        try (PreparedStatement ps = getConnection().prepareStatement(
                "SELECT * FROM \"Queue\" WHERE \"Status\" = 'QUEUED' LIMIT 1;")) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long skinID = rs.getLong("SkinID");
                if (rs.wasNull()) {
                    skinID = -1;
                }

                return new QueueObject(rs.getLong("ID"), rs.getString("SkinURL"), rs.getString("Value"), rs.getString(
                        "Signature"), rs.getString("UserAgent"), QueueStatus.valueOf(rs.getString("Status")),
                        skinID, rs.getTimestamp("Added"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void updateQueueObject(long queueID, long skinID, QueueStatus status) {
        try (PreparedStatement ps = getConnection()
                .prepareStatement("UPDATE \"Queue\" SET \"Status\"=?::\"QueueStatus\", \"SkinID\"=? WHERE " +
                        "\"ID\"=?;")) {
            ps.setString(1, status.name());

            if (skinID != -1) {
                ps.setLong(2, skinID);
            } else {
                ps.setNull(2, Types.NULL);
            }

            ps.setLong(3, queueID);

            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /* Skins */

    public static Skin getSkin(String hash) {
        try (PreparedStatement ps = getConnection().prepareStatement(
                "SELECT * FROM \"Skins\" WHERE \"CleanHash\" =? LIMIT 1;")) {
            ps.setString(1, hash);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return toSkin(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Legacy - recode SkinAssetUtils#createAll and delete this method
    static Skin getSkin(int offset) {
        try (PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM \"Skins\" ORDER BY \"ID\" LIMIT 1 OFFSET ?;")) {
            ps.setInt(1, offset);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return toSkin(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static Skin createSkin(String hash, String skinURL, String textureValue, String textureSignature,
                                  boolean isAlex, long duplicateOf) {
        try (PreparedStatement ps = getConnection()
                .prepareStatement(
                        "INSERT INTO \"Skins\"" +
                                "(\"CleanHash\", \"SkinURL\", \"TextureValue\", \"TextureSignature\", " +
                                "\"IsAlex\", \"DuplicateOf\") " +
                                "VALUES(?,?,?,?,?,?) RETURNING *;")) {
            ps.setString(1, hash);
            ps.setString(2, skinURL);

            if (textureValue != null) {
                ps.setString(3, textureValue);
            } else {
                ps.setNull(3, Types.NULL);
            }

            if (textureSignature != null) {
                ps.setString(4, textureSignature);
            } else {
                ps.setNull(4, Types.NULL);
            }

            ps.setBoolean(5, isAlex);

            if (duplicateOf > 0) {
                ps.setLong(6, duplicateOf);
            } else {
                ps.setNull(6, Types.NULL);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return toSkin(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    static Skin setSkinHash(long skinID, String hash) {
        try (PreparedStatement ps = getConnection()
                .prepareStatement(
                        "UPDATE \"Skins\" SET \"CleanHash\"=? WHERE \"ID\"=? " +
                                "RETURNING *;")) {
            ps.setString(1, hash);
            ps.setLong(2, skinID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return toSkin(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    static boolean createSkinImages(long skinID, BufferedImage original, BufferedImage clean) {
        try (PreparedStatement ps = getConnection()
                .prepareStatement(
                        "INSERT INTO \"Images\"" +
                                "(\"SkinID\", original, clean) " +
                                "VALUES(?,?,?) ON CONFLICT DO NOTHING;")) {
            ps.setLong(1, skinID);

            ByteArrayOutputStream orgBytes = new ByteArrayOutputStream();
            ImageIO.write(original, "PNG", orgBytes);
            orgBytes.flush();

            ByteArrayOutputStream cleanBytes = new ByteArrayOutputStream();
            ImageIO.write(clean, "PNG", cleanBytes);
            cleanBytes.flush();

            ps.setBytes(2, orgBytes.toByteArray());
            ps.setBytes(3, cleanBytes.toByteArray());

            orgBytes.close();
            cleanBytes.close();

            ps.execute();
            return true;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /* Connection */

    public static boolean canConnect() {
        return getConnection() != null;
    }

    private static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(
                        "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE + "?user=" + USERNAME + "&password" +
                                "=" + PASSWORD + "&ssl=true&sslmode=require");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return con;
    }

    /* Helper */
    private static Skin toSkin(ResultSet rs) throws SQLException {
        long duplicateOf = rs.getLong("DuplicateOf");
        if (rs.wasNull()) {
            duplicateOf = -1;
        }

        return new Skin(rs.getLong("ID"), rs.getString("CleanHash"),
                rs.getString("SkinURL"), rs.getString("TextureValue"),
                rs.getString("TextureSignature"), rs.getBoolean("IsAlex"),
                duplicateOf, rs.getTimestamp("Added"));
    }
}