package de.sprax2013.skindb.backend;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.sprax2013.skindb.backend.utils.MineSkinUtils;
import de.sprax2013.skindb.backend.utils.SkinAssetUtils;

public class ConsoleManager {
	private static ExecutorService thread;
	static Scanner sc;

	public static void init() {
		thread = Executors.newSingleThreadExecutor();

		thread.execute(new Runnable() {
			@Override
			public void run() {
				sc = new Scanner(System.in);

				while (sc != null) {
					try {
						System.out.print("> ");

						String in = sc.nextLine().trim();

						if (in.equalsIgnoreCase("help") || in.equalsIgnoreCase("?")
								|| in.equalsIgnoreCase("commands")) {
							System.out.println("Command (Alias1, Alias2)" + System.lineSeparator());

							System.out.println("help (?, commands)");
							System.out.println("checkDB");
							System.out.println("create-SkinAssets (c-sa)");
							System.out.println("import-MineSkin (i-ms)");
							System.out.println("exit (stop)");
						} else if (in.equalsIgnoreCase("checkDB")) {
							PendingManager.shouldCheckDB(true);
						} else if (in.equalsIgnoreCase("create-SkinAssets") || in.equalsIgnoreCase("c-sa")) {
							SkinAssetUtils.createAll();
						} else if (in.equalsIgnoreCase("import-MineSkin") || in.equalsIgnoreCase("i-ms")) {
							MineSkinUtils.importMineSkin();
						} else if (in.equalsIgnoreCase("exit") || in.equalsIgnoreCase("stop")) {
							sc.close();
							sc = null;

							Runtime.getRuntime().exit(0);
						}
					} catch (Throwable th) {
						th.printStackTrace();
					}
				}
			}
		});
	}

	public static void deInit() {
		if (thread != null) {
			thread.shutdownNow();
			thread = null;
		}

		if (sc != null) {
			sc.close();
			sc = null;
		}
	}
}