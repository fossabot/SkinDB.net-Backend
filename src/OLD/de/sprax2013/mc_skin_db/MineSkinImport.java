package OLD.de.sprax2013.mc_skin_db;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import OLD.de.sprax2013.mc_skin_db.skin.Skin;
import OLD.de.sprax2013.mc_skin_db.util.DownloadUtils;
import OLD.de.sprax2013.mc_skin_db.util.HashingUtils;
import OLD.de.sprax2013.mc_skin_db.util.RemoteDatabaseUtils;

public class MineSkinImport {
	static final boolean FORCE_FULL_UPDATE = false;

	public static void main(String[] args) {
		try {
			Response res = Jsoup.connect("https://api.mineskin.org/get/list/?size=0").ignoreContentType(true).execute();

			if (res.statusCode() == 200) {
				int total = ((JsonObject) new JsonParser().parse(res.body())).get("page").getAsJsonObject().get("total")
						.getAsInt();

				res = Jsoup.connect("https://api.mineskin.org/get/list/?size=" + total).ignoreContentType(true)
						.maxBodySize(0).execute();

				if (res.statusCode() == 200) {
					int addedNew = 0, addedDuplicate = 0, alreadyInDB = 0;

					int loop = 0;
					for (JsonElement elem : ((JsonObject) new JsonParser().parse(res.body())).get("skins")
							.getAsJsonArray()) {
						if (loop % 50 == 0) {
							System.out.println(loop + " von " + total + " ["
									+ round((Double.valueOf(loop) / Double.valueOf(total)) * 100, 2) + "%]");
						}

						if (!FORCE_FULL_UPDATE && alreadyInDB >= 50) {
							System.out.println("Vorzeitiger Abbruch, da 50 Skins bereits in der Datenbank sind!");
							break;
						}

						try {
							String skinURL = elem.getAsJsonObject().get("url").getAsString();

							if (!RemoteDatabaseUtils.isInDatabase(skinURL)) {
								File f = DownloadUtils.downloadToTempFile(skinURL);
								String fileHash = HashingUtils.calcFileHash(f);
								f.delete();

								Integer duplicate = RemoteDatabaseUtils.getDuplicate(fileHash);

								if (RemoteDatabaseUtils.setSkin(new Skin(skinURL, fileHash, duplicate))) {
									if (duplicate == null) {
										addedNew++;
									} else {
										addedDuplicate++;
									}
								}
							} else {
								alreadyInDB++;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						loop++;
					}

					System.out.println("Neu: " + addedNew);
					System.out.println("Neue duplikate: " + addedDuplicate);
					System.out.println("Bereits in der Datenbank: " + alreadyInDB);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}