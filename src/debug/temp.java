package debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;

public class temp {
	private static final File f = new File("C:\\Users\\Christian\\Downloads\\2nd-level-domains\\uk.txt");
	private static final File dir = new File("C:\\Users\\Christian\\Downloads\\2nd-level-domains\\uk");

	private static final int SKIP_LINES = 0;
	private static final int MAX_LINES_PER_FILE = 500_000;

	public static void main(String[] args) throws IOException {
		if (f.exists()) {
			dir.mkdirs();

			Scanner sc = new Scanner(f);

			String fileHeader = FilenameUtils.getExtension(f.getName()).equalsIgnoreCase("csv") ? sc.nextLine() : "";

			FileWriter partFile = null;

			int partFileCount = 0;
			int partFileLineCount = 0;

			int lineCount = 0;
			while (sc.hasNextLine()) {
				lineCount++;
				String line = sc.nextLine();

				if (lineCount > SKIP_LINES) {
					if (partFile != null && partFileLineCount >= MAX_LINES_PER_FILE) {
						partFile.close();
						partFile = null;

						partFileCount++;
						partFileLineCount = 0;
					}

					if (partFile == null) {
						partFile = new FileWriter(new File(dir, partFileCount + ".txt"));

						if (!fileHeader.isEmpty()) {
							partFile.write(fileHeader + System.lineSeparator());
						}
					}

					partFile.write(line.trim().toLowerCase() + System.lineSeparator());
					partFileLineCount++;
				}
			}

			System.out.println("Processed a total of " + lineCount + " lines");

			sc.close();
		}
	}
}