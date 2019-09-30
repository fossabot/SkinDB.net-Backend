package de.sprax2013.skindb.backend;

import de.sprax2013.skindb.backend.queue.QueueManager;
import de.sprax2013.skindb.backend.utils.DatabaseUtils;
import de.sprax2013.skindb.backend.utils.MineSkinUtils;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            QueueManager.deInit();
            MineSkinUtils.deInit();

            ConsoleManager.deInit();
            DatabaseUtils.deInit();
        }));

        if (DatabaseUtils.canConnect()) {
            QueueManager.init();
            MineSkinUtils.init();

            System.out.println("Die Anwendung prüft nun regelmäßig die Warteschlange");
            ConsoleManager.init();
        } else {
            if (!DatabaseUtils.canConnect()) {
                System.err.println("Es konnte keine Verbindung mit der Datenbank hergestellt werden");
            }

            System.exit(-1);
        }
    }
}