package de.sprax2013.skindb.backend;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import de.sprax2013.advanced_dev_utils.mysql.MySQLAPI;

public class Main {
	public final static String VERSION = "1.0";

	private static Properties props;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				ConsoleManager.deInit();
				ServerManager.deInit();
				PendingManager.deInit();

				MySQLAPI.deInit();
			}
		}));

		if (PendingManager.init() && ServerManager.init()) {
			ConsoleManager.init();
		} else {
			System.out.println("An error has occurred! Exiting...");
			Runtime.getRuntime().exit(-1);
		}
	}

	public static Properties getConfig() {
		if (props == null) {
			props = new Properties();

			props.setProperty("MySQL.Host", "localhost");
			props.setProperty("MySQL.Port", "3306");
			props.setProperty("MySQL.Username", "notRoot");
			props.setProperty("MySQL.Password", "s3cr3t");
			props.setProperty("MySQL.Database", "skinDB");

			File cfg = new File("config.properties");

			if (cfg.exists()) {
				try (FileReader fR = new FileReader(cfg)) {
					props.load(fR);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			try (FileWriter fW = new FileWriter(cfg)) {
				props.store(fW, "SkinDB-Backend (by Sprax2013)");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return props;
	}
}