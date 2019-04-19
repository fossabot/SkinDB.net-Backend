package debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

// Jede Zeile (die kein Duplikat ist) wird in den RAM geladen. Gut wenn ausreichend RAM verfügbar ist.
public class temp2 {
	private static final File f = new File("C:\\Users\\Christian\\Downloads\\com.txt");
	private static final boolean IGNORE_CASE_AND_TRIM = true;

	public static void main(String[] args) throws IOException {
		if (f.exists() && f.isFile()) {
			File outF = new File(f.getParentFile(),
					FilenameUtils.getBaseName(f.getName()) + "_out2." + FilenameUtils.getExtension(f.getName()));

			if (!outF.exists()) {
				PrintWriter pw = new PrintWriter(outF);
				BufferedReader br = new BufferedReader(new FileReader(f));

				String line = br.readLine();

				Set<String> hs = new HashSet<>();

				while (line != null) {
					if (hs.add(line)) {
						pw.println(line);
						pw.flush();
					} else {
						System.out.println("Skipped a line");
					}

					line = br.readLine();
					line = IGNORE_CASE_AND_TRIM ? line.trim().toLowerCase() : line;
				}

				pw.flush();

				br.close();
				pw.close();

				System.out.println("Done!");
			} else {
				System.out.println("Output-File '" + outF.getName() + "' does already exist!");
			}
		} else {
			System.out.println("File does not exist!");
		}
	}
}

// Zeitaufwendig aber geringer RAM-Verbrauch (Verhindert OutOfMemory bei großen Dateien)
class FileOperation {
	private static final File f = new File("C:\\Users\\Christian\\Downloads\\com.txt");
	private static final boolean IGNORE_CASE_AND_TRIM = true;

	public static void main(String[] args) throws IOException {
		File outF = new File(f.getParentFile(),
				FilenameUtils.getBaseName(f.getName()) + "_out." + FilenameUtils.getExtension(f.getName()));

		PrintWriter pw = new PrintWriter(outF);
		BufferedReader br1 = new BufferedReader(new FileReader(f));

		String line1 = br1.readLine();
		line1 = IGNORE_CASE_AND_TRIM ? line1.trim().toLowerCase() : line1;

		while (line1 != null) {
			boolean flag = false;

			BufferedReader br2 = new BufferedReader(new FileReader(outF));

			String line2 = br2.readLine();

			while (line2 != null) {
				if (line1.equals(line2)) {
					flag = true;
					break;
				}

				line2 = br2.readLine();
			}

			if (!flag) {
				pw.println(line1);
				pw.flush();
			}

			line1 = br1.readLine();

			br2.close();
		}

		br1.close();
		pw.close();

		System.out.println("Done!");
	}
}