package de.sprax2013.skindb.backend.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MineSkinUtils {
    private static ScheduledExecutorService th = Executors.newSingleThreadScheduledExecutor();

    public static void init() {
        th.scheduleAtFixedRate(() -> System.out.println("Es wurden " + importRecent(() -> th.isShutdown()) + " Skins aus MineSkin.org importiert"),
                0, 10, TimeUnit.MINUTES);
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


    public static int importRecent() {
        return importRecent(() -> false);
    }

    private static int importRecent(Callable<Boolean> shouldBreakLoop) {
        int newSkins = 0;

        int page = 1;
        try {
            while (page <= 10 && page > 0 && !shouldBreakLoop.call()) {
                try {
                    Connection.Response res = getConnection("https://api.mineskin.org/get/list/" + page + "?size=32");

                    if (res.statusCode() > 399) {
                        System.out.println(("https://api.mineskin.org/get/list/" + page + "?size=32") +
                                " returned HTTP-Status " + res.statusCode());
                        break;
                    }

                    for (JsonElement skin : JsonParser.parseString(res.body()).getAsJsonObject().getAsJsonArray("skins")) {
                        if (shouldBreakLoop.call()) break;

                        res = getConnection("https://api.mineskin.org/get/id/" + skin.getAsJsonObject().get("id").getAsString());

                        if (res.statusCode() > 399) {
                            System.out.println(("https://api.mineskin.org/get/list/" + page + "?size=32") +
                                    " returned HTTP-Status " + res.statusCode());
                            page = -1;
                            break;
                        }

                        JsonObject skinTex = JsonParser.parseString(res.body()).getAsJsonObject()
                                .get("data").getAsJsonObject().get("texture").getAsJsonObject();

                        res = getConnection("https://api.skindb.net/provide?value=" +
                                encodeURIComponent(skinTex.get("value").getAsString()) +
                                "&signature=" + encodeURIComponent(skinTex.get("signature").getAsString()));

                        if (res.statusCode() > 399) {
                            System.out.println(("https://api.skindb.net/provide?value=" +
                                    encodeURIComponent(skinTex.get("value").getAsString()) +
                                    "&signature=" + encodeURIComponent(skinTex.get("signature").getAsString())) +
                                    " returned HTTP-Status " + res.statusCode());
                            page = -1;
                            break;
                        } else if (JsonParser.parseString(res.body()).getAsJsonObject().has("msg") &&
                                "The skin is already in the database".equals(JsonParser.parseString(res.body()).getAsJsonObject().get("msg").getAsString())) {
                            page = -1;
                            break;
                        }

                        newSkins++;
                    }
                } catch (IOException ignored) {
                    break;
                }

                page++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newSkins;
    }

    private static Connection.Response getConnection(String url) throws IOException {
        Connection con = Jsoup.connect(url).ignoreHttpErrors(true).ignoreContentType(true).followRedirects(true);

        con.header("Accept", "application/json, text/plain, */*");
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
        con.header("DNT", "1");


        if (url.toLowerCase().startsWith("https://mineskin.org")) {
            con.header("Origin", "https://mineskin.org");
            con.referrer("https://mineskin.org/");
        } else if (url.toLowerCase().startsWith("https://api.sprax2013.de") || url.toLowerCase().startsWith("https://api.skindb.net")) {
            con.header("User-Agent", "SkinDB-Backend (MineSkin Import)");
        }

        return con.execute();
    }

    private static String encodeURIComponent(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }
}