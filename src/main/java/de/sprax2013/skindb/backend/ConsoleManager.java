package de.sprax2013.skindb.backend;

import de.sprax2013.skindb.backend.utils.MineSkinUtils;
import de.sprax2013.skindb.backend.utils.SkinAssetUtils;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsoleManager {
    private static ExecutorService th;

    private static Scanner sc;

    static void init() {
        th = Executors.newSingleThreadExecutor();
        sc = new Scanner(System.in);

        th.execute(() -> {
            while (!th.isShutdown()) {
                try {
                    System.out.print("> ");

                    String in = sc.nextLine().trim();

                    if (in.equalsIgnoreCase("help")
                            || in.equalsIgnoreCase("?")
                            || in.equalsIgnoreCase("commands")) {
                        System.out.println("Command (Alias1, Alias2)" + System.lineSeparator());

                        System.out.println("help (?, commands)");
                        System.out.println("create Skins (c Skins)");
                        System.out.println("import MineSkin (i MineSkin)");
                        System.out.println("exit (stop)");
                    } else if (in.equalsIgnoreCase("create Skins") || in.equalsIgnoreCase("c Skins")) {
                        System.out.println("Generiere alle Skin-Assets...");
                        SkinAssetUtils.createAll();
                        System.out.println("Alle Skin-Assets wurde überprüft und erstellt!");
                    } else if (in.equalsIgnoreCase("import MineSkin") || in.equalsIgnoreCase("i MineSkin")) {
                        System.out.println("Es wurden " + MineSkinUtils.importRecent() + " Skins hinzugefügt");
                    } else if (in.equalsIgnoreCase("exit") || in.equalsIgnoreCase("stop")) {
                        System.out.println("Anwendung wird beendet...");

                        deInit();
                        System.exit(0);
                    } else {
                        System.out.println("Unknown command!");
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    static void deInit() {
        th.shutdownNow();
        sc.close();
    }
}