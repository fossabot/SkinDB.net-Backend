package OLD.de.sprax2013.mc_skin_db;

import OLD.de.sprax2013.mc_skin_db.gui.GUI;
import de.sprax2013.skindb.back_end.utils.RemoteDatabaseUtils;

public class Main {
	public static void main(String[] args) {
		if (RemoteDatabaseUtils.creatTables()) {
			GUI.init();
		} else {
			System.out.println("[Fehler] Es gab einen Fehler, beim Anlegen der Datenbanktabelle(n).");
		}
	}
}