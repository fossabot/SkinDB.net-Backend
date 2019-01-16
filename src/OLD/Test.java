package OLD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import de.sprax2013.advanced_dev_utils.mojang.MojangAPI;
import de.sprax2013.advanced_dev_utils.mojang.MojangProfile;

public class Test {
	private static final String STR = " Anthobet Barbiel Baum_Stamm_LP Belastenderhaze BinLame BinMarcoDNA bySpydex CalledNovaYT CG_Niclas CJTheFish_ Der_Slush diesemello DinMuddiKlaut DomenCraftHD DrKyoshia dsp1901_7 EinBaumkuchen ekobutz Erdtiger EvilDev FelixGamezz Foxymaster FreezerHG g3t0nmy25KRxlex Hb_checker HerrBergmann HerrBoergsmen HerrKomp3tenz hotfiredog IcePlayZCreeper jascha64 JudutsSohn juzoplier K1nqKnxck K3ksTV Kezel KingMaatti KiwimelonHD kleinerEmre Knittler Leon2706 Letsplaymine55 LetsTobiYT LukasDerSchwizer luqezYT Madlvss MicksLife Minimi_Chicken MongolenKind Mostgamer MrEinSmiley MrEliteGamingTV MTSProduction Neosun28 NinjaSeinVader noqtuh OpaWirdGrau PainxZ passi06 Qwntix  calculator24 Chaoz2008 GansGucci HerrBergmann HerrBergmann_ORG HerrKomp3tenz LogischePizza PuffiJrpg xXNinjaNinaXx  SamiGHG Sandro_Wagner Scenco Spyro20 TaddlsAdlibs  BergmannToGo Bjorn_Jarnsida Erdtiger HerrBergmann ovp SmokeyGHG zRolex  TDMS_MICHELLE TheGlitchi TraurigerShadow Tribunion TuersteherPxddy VanDyren Whis_1 xX_VanilleEis_Xx zJazzon ZockerEnte zVerbuggt ";

	private static final File skinURLFile = new File("C:\\Users\\Christian\\Desktop\\skinURLs.txt"),
			uuidFile = new File("C:\\Users\\Christian\\Desktop\\UUIDs.txt");

	public static void main(String[] args) throws IOException {
		Set<String> list = new HashSet<>();

		for (String s : STR.trim().split(" ")) {
			if (!s.trim().isEmpty()) {
				list.add(s.trim().toLowerCase());
			}
		}

		FileWriter fWURL = new FileWriter(skinURLFile, true), fWUUID = new FileWriter(uuidFile, true);

		for (String s : list) {
			MojangProfile profile = MojangAPI.getProfile(s);

			if (profile != null) {
				fWUUID.write(System.lineSeparator() + profile.getUUID().toString());

				if (profile.hasTextureProp() && profile.getTextureProp().hasSkinURL()) {
					fWURL.write(System.lineSeparator() + profile.getTextureProp().getSkinURL());
				}
			}
		}

		fWURL.close();
		fWUUID.close();
	}
}