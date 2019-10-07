package de.sprax2013.skindb.backend.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// TODO: Use the class from ADU
public class HashingUtils {
    public static String getHash(File file) {
        return getHash(file, "SHA-256");
    }

    private static String getHash(File file, @SuppressWarnings("SameParameterValue") String algorithm) {
        if (file.exists() && file.isFile()) {
            try {
                MessageDigest digest = MessageDigest.getInstance(algorithm);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[8192];

                int count;
                while ((count = bis.read(buffer)) > 0) {
                    digest.update(buffer, 0, count);
                }

                bis.close();
                return toHex(digest.digest());
            } catch (IOException | NoSuchAlgorithmException var6) {
                var6.printStackTrace();
            }
        }

        return null;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();

        for (byte aByte : bytes) {
            String hex = Integer.toHexString(255 & aByte);
            if (hex.length() == 1) {
                result.append('0');
            }

            result.append(hex);
        }

        return result.toString();
    }
}