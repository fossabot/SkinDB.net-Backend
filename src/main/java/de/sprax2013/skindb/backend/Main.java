package de.sprax2013.skindb.backend;

import de.sprax2013.skindb.backend.queue.QueueManager;
import de.sprax2013.skindb.backend.utils.DatabaseUtils;
import de.sprax2013.skindb.backend.utils.SkinAssetUtils;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConsoleManager.deInit();
            QueueManager.deInit();
            MineSkinUtils.deInit();
            DatabaseUtils.deInit();
        }));

        if (DatabaseUtils.canConnect() && SkinAssetUtils.isReady()) {
            QueueManager.init();
            MineSkinUtils.init();
            System.out.println("Die Anwendung prüft nun regelmäßig die Warteschlange");
            ConsoleManager.init();
        } else {
            if (!DatabaseUtils.canConnect()) {
                System.err.println("Es konnte keine Verbindung mit der Datenbank hergestellt werden");
            }

            if (!SkinAssetUtils.isReady()) {
                if (!SkinAssetUtils.getAssetDir().exists()) {
                    System.err.println("Das Verzeichnis '" + SkinAssetUtils.getAssetDir().getAbsolutePath() +
                            "' wurde nicht gefunden!");
                } else {
                    System.err.println("Für das Verzeichnis '" + SkinAssetUtils.getAssetDir().getAbsolutePath() +
                            "' liegen nicht ausreichende Zugriffsrechte vor!");
                }
            }

            System.exit(-1);
        }
    }
}