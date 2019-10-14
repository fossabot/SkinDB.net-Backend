package de.sprax2013.skindb.backend.utils;

import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NameMCUtils {
    private static ScheduledExecutorService th = Executors.newSingleThreadScheduledExecutor();

    public static void init() {
        th.scheduleAtFixedRate(() -> System.out.println("Es wurden " + importRecentNameChanges(() -> th.isShutdown()) + " Skins aus NameMC.com (Name changes) importiert"),
                0, 1, TimeUnit.DAYS);

        th.scheduleAtFixedRate(() -> System.out.println("Es wurden " + importRecent(() -> th.isShutdown()) + " Skins aus NameMC.com (New) importiert"),
                0, 1, TimeUnit.MINUTES);
        th.scheduleAtFixedRate(() -> System.out.println("Es wurden " + importHourlyHot(() -> th.isShutdown()) + " Skins aus NameMC.com (Hot - Hourly) importiert"),
                0, 1, TimeUnit.HOURS);
        th.scheduleAtFixedRate(() -> System.out.println("Es wurden " + importDailyHot(() -> th.isShutdown()) + " Skins aus NameMC.com (Hot - Daily) importiert"),
                0, 1, TimeUnit.DAYS);
    }

    public static void deInit() {
        th.shutdown();

        try {
            th.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        th.shutdownNow();
    }

    public static int importRecentNameChanges() {
        return importRecentNameChanges(() -> false);
    }

    private static int importRecentNameChanges(Callable<Boolean> shouldBreakLoop) {
        int newSkins = 0;

        try {
            try {
                Connection.Response res = getConnection("https://namemc.com/minecraft-names");

                if (res.statusCode() > 399) {
                    System.out.println(("https://namemc.com/minecraft-names") +
                            " returned HTTP-Status " + res.statusCode());
                }

                Document doc = res.parse();

                for (Element elem : doc.getElementsByTag("a")) {
                    if (shouldBreakLoop.call()) break;

                    String username = elem.absUrl("href");
                    if (username.toLowerCase().startsWith("https://namemc.com/name/")) {
                        username = username.substring(username.lastIndexOf('/') + 1);

                        res = getConnection("https://api.skindb.net/provide?value=" + username);

                        if (res.statusCode() > 399) {
                            System.out.println(("https://api.skindb.net/provide?value=" + username) +
                                    " returned HTTP-Status " + res.statusCode());
                            continue;
                        } else if (JsonParser.parseString(res.body()).getAsJsonObject().has("msg") &&
                                "The skin is already in the database".equals(JsonParser.parseString(res.body()).getAsJsonObject().get("msg").getAsString())) {
                            continue;
                        }

                        newSkins++;
                    }
                }
            } catch (IOException ignored) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newSkins;
    }

    public static int importRecent() {
        return importRecent(() -> false);
    }

    private static int importRecent(Callable<Boolean> shouldBreakLoop) {
        int newSkins = 0;

        try {
            try {
                Connection.Response res = getConnection("https://namemc.com/minecraft-skins/new");

                if (res.statusCode() > 399) {
                    System.out.println(("https://namemc.com/minecraft-skins/new") +
                            " returned HTTP-Status " + res.statusCode());
                }

                Document doc = res.parse();

                for (Element elem : doc.getElementsByClass("card-header")) {
                    if (shouldBreakLoop.call() || !elem.hasText()) break;

//                    String skinURL = elem.parent().absUrl("href");
//                    skinURL = "https://de.namemc.com/texture/" + skinURL.substring(skinURL.lastIndexOf('/') + 1) + ".png";

                    res = getConnection("https://api.skindb.net/provide?value=" + elem.text());

                    if (res.statusCode() > 399) {
                        System.out.println(("https://api.skindb.net/provide?value=" + elem.text()) +
                                " returned HTTP-Status " + res.statusCode());
                        continue;
                    } else if (JsonParser.parseString(res.body()).getAsJsonObject().has("msg") &&
                            "The skin is already in the database".equals(JsonParser.parseString(res.body()).getAsJsonObject().get("msg").getAsString())) {
                        continue;
                    }

                    newSkins++;
                }
            } catch (IOException ignored) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newSkins;
    }

    public static int importHourlyHot() {
        return importHourlyHot(() -> false);
    }

    private static int importHourlyHot(Callable<Boolean> shouldBreakLoop) {
        int newSkins = 0;

        try {
            try {
                Connection.Response res = getConnection("https://namemc.com/minecraft-skins/trending/hourly");

                if (res.statusCode() > 399) {
                    System.out.println(("https://namemc.com/minecraft-skins/trending/hourly") +
                            " returned HTTP-Status " + res.statusCode());
                }

                Document doc = res.parse();

                for (Element elem : doc.getElementsByClass("card-header")) {
                    if (shouldBreakLoop.call() || !elem.hasText()) break;

//                    String skinURL = elem.parent().absUrl("href");
//                    skinURL = "https://de.namemc.com/texture/" + skinURL.substring(skinURL.lastIndexOf('/') + 1) + ".png";

                    res = getConnection("https://api.skindb.net/provide?value=" + elem.text());

                    if (res.statusCode() > 399) {
                        System.out.println(("https://api.skindb.net/provide?value=" + elem.text()) +
                                " returned HTTP-Status " + res.statusCode());
                        continue;
                    } else if (JsonParser.parseString(res.body()).getAsJsonObject().has("msg") &&
                            "The skin is already in the database".equals(JsonParser.parseString(res.body()).getAsJsonObject().get("msg").getAsString())) {
                        continue;
                    }

                    newSkins++;
                }
            } catch (IOException ignored) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newSkins;
    }

    public static int importDailyHot() {
        return importDailyHot(() -> false);
    }

    private static int importDailyHot(Callable<Boolean> shouldBreakLoop) {
        int newSkins = 0;

        try {
            try {
                Connection.Response res = getConnection("https://namemc.com/minecraft-skins/trending/daily");

                if (res.statusCode() > 399) {
                    System.out.println(("https://namemc.com/minecraft-skins/trending/daily") +
                            " returned HTTP-Status " + res.statusCode());
                }

                Document doc = res.parse();

                for (Element elem : doc.getElementsByClass("card-header")) {
                    if (shouldBreakLoop.call() || !elem.hasText()) break;

//                    String skinURL = elem.parent().absUrl("href");
//                    skinURL = "https://de.namemc.com/texture/" + skinURL.substring(skinURL.lastIndexOf('/') + 1) + ".png";

                    res = getConnection("https://api.skindb.net/provide?value=" + elem.text());

                    if (res.statusCode() > 399) {
                        System.out.println(("https://api.skindb.net/provide?value=" + elem.text()) +
                                " returned HTTP-Status " + res.statusCode());
                        continue;
                    } else if (JsonParser.parseString(res.body()).getAsJsonObject().has("msg") &&
                            "The skin is already in the database".equals(JsonParser.parseString(res.body()).getAsJsonObject().get("msg").getAsString())) {
                        continue;
                    }

                    newSkins++;
                }
            } catch (IOException ignored) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newSkins;
    }

    private static Connection.Response getConnection(String url) throws IOException {
        Connection con = Jsoup.connect(url).ignoreHttpErrors(true).ignoreContentType(true).followRedirects(true);

        con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
        con.header("DNT", "1");
        con.header("Upgrade-Insecure-Requests", "1");


        if (url.toLowerCase().startsWith("https://namemc.com")) {
            con.referrer("https://namemc.com/");
        } else if (url.toLowerCase().startsWith("https://api.sprax2013.de") || url.toLowerCase().startsWith("https://api.skindb.net")) {
            con.header("User-Agent", "SkinDB-Backend (MineSkin Import)");
        }

        return con.execute();
    }
}