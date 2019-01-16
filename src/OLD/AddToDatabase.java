package OLD;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

import OLD.de.sprax2013.mc_skin_db.skin.Skin;
import OLD.de.sprax2013.mc_skin_db.util.DownloadUtils;
import OLD.de.sprax2013.mc_skin_db.util.HashingUtils;
import OLD.de.sprax2013.mc_skin_db.util.RemoteDatabaseUtils;
import de.sprax2013.advanced_dev_utils.misc.UUIDUtils;
import de.sprax2013.advanced_dev_utils.mojang.MojangAPI;
import de.sprax2013.advanced_dev_utils.mojang.MojangProfile;

public class AddToDatabase {
	private static final File file = new File("C:\\Users\\Christian\\Desktop\\skinURLs.txt");

	public static void main(String[] args) throws IOException {
		if (file.exists() && file.isFile()) {
			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();

				if (!line.isEmpty()) {
					String skinURL = null;

					if (isUUID(line)) {
						MojangProfile profile = MojangAPI.getProfile(UUID.fromString(UUIDUtils.addDashesToUUID(line)));

						if (profile != null && profile.hasTextureProp() && profile.getTextureProp().hasSkinURL()) {
							skinURL = profile.getTextureProp().getSkinURL();
						}
					} else if (isTextureURL(line)) {
						skinURL = line;
					} else {
						System.out.println(line + " wurde übersprungen");
					}

					if (skinURL != null) {
						if (!RemoteDatabaseUtils.isInDatabase(skinURL)) {
							File skinFile = DownloadUtils.downloadToTempFile(skinURL);
							String fileHash = HashingUtils.calcFileHash(skinFile);
							skinFile.delete();

							RemoteDatabaseUtils
									.setSkin(new Skin(skinURL, fileHash, RemoteDatabaseUtils.getDuplicate(fileHash)));
						} else {
							System.out.println("Der Skin (von) '" + line + "', befindet sich bereits in der Datenbank");
						}
					}
				}
			}

			sc.close();
		} else {
			System.out.println("'" + file.getAbsolutePath() + "' existiert nicht oder ist keine Datei!");
		}
	}

	private static boolean isUUID(String s) {
		if (s != null && !s.trim().isEmpty()) {
			try {
				return UUID.fromString(UUIDUtils.addDashesToUUID(s)) != null;
			} catch (@SuppressWarnings("unused") IllegalArgumentException ex) {
			}
		}

		return false;
	}

	@SuppressWarnings("unused")
	private static boolean isTextureURL(String s) {
		if (s != null && !s.trim().isEmpty()) {
			try {
				new URL(s);
				return s.toLowerCase().startsWith("http://textures.minecraft.net/texture/");
			} catch (MalformedURLException ex) {
			}
		}

		return false;
	}
}