package de.sprax2013.skindb.backend.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MineSkinUtils {
	private static final int SIZE = 16;

	public static void importMineSkin() {
		try {
			Response res = getResponse("https://api.mineskin.org/get/list/?size=0");

			if (res != null && res.statusCode() == 200) {
				final int total = ((JsonObject) new JsonParser().parse(res.body())).get("page").getAsJsonObject()
						.get("total").getAsInt();
				int pages = (total / 16);

				if (pages > 25) {
					pages = 25;
				}

//				if (total % 16 != 0) {
//					pages++;
//				}

				for (int i = 1; i <= pages; i++) {
					System.out.println("Page " + i + " of " + pages + "[" + round((i / (double) pages) * 100) + "%]");

					res = getResponse("http://api.mineskin.org/get/list/" + i + "?size=" + SIZE);

					if (res != null && res.statusCode() == 200) {
						for (JsonElement elem : ((JsonObject) new JsonParser().parse(res.body())).get("skins")
								.getAsJsonArray()) {
							try {
								String skinURL = elem.getAsJsonObject().get("url").getAsString();

								getResponse(
										"https://api.skindb.net/provide?data=" + URLEncoder.encode(skinURL, "UTF-8"),
										"MineSkin (Backend-Import)");
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);

		return bd.doubleValue();
	}

	private static Response getResponse(String url) {
		return getResponse(url, "SkinDB (Backend v0.1.3-DEV)");
	}

	private static Response getResponse(String url, String userAgent) {
		try {
			return Jsoup.connect(url).header("User-Agent", userAgent).ignoreContentType(true).execute();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;
	}
}