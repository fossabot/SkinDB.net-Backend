package OLD.de.sprax2013.mc_skin_db.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;

public class DownloadUtils {
	public static File downloadToTempFile(String url) throws IOException {
		File result = File.createTempFile("mcSkinDB_", null);
		result.deleteOnExit();

		FileOutputStream fos = new FileOutputStream(result);
		fos.write(Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).execute().bodyAsBytes());
		fos.close();

		return result;
	}

	public static BufferedInputStream getInputStream(String url) throws IOException {
		return Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).execute().bodyStream();
	}
}