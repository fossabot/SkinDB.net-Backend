package OLD.de.sprax2013.mc_skin_db.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashingUtils {
	public static String calcFileHash(File file) {
		if (file.exists()) {
			try {
				byte[] buffer = new byte[8192];
				int count;
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				while ((count = bis.read(buffer)) > 0) {
					digest.update(buffer, 0, count);
				}
				bis.close();

				byte[] hash = digest.digest();

				return Base64.getEncoder().encodeToString(hash);
			} catch (NoSuchAlgorithmException | IOException ex) {
				ex.printStackTrace();
			}
		}

		return null;
	}
}