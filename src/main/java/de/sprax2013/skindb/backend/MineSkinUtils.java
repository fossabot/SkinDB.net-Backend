package de.sprax2013.skindb.backend;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class MineSkinUtils {
    private static ScheduledExecutorService th = Executors.newSingleThreadScheduledExecutor();

    static void init() {
        th.scheduleAtFixedRate(() -> System.out.println("Es wurden " + importRecent() + " Skins aus MineSkin.org importiert"),
                0, 1, TimeUnit.HOURS);
    }

    static void deInit() {
        th.shutdown();

        try {
            th.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        th.shutdownNow();
    }

    static int importRecent() {
        int newSkins = 0;

        int page = 1;
        while (page <= 10 && page > 0) {
            try {
                Connection.Response res = getConnection("https://api.mineskin.org/get/list/" + page + "?size=32");

                if (res.statusCode() > 399) {
                    System.out.println(("https://api.mineskin.org/get/list/" + page + "?size=32") +
                            " returned HTTP-Status " + res.statusCode());
                    break;
                }

                for (JsonElement skin : new JsonParser().parse(res.body()).getAsJsonObject().getAsJsonArray("skins")) {
                    res = getConnection("https://api.mineskin.org/get/id/" + skin.getAsJsonObject().get("id").getAsString());

                    if (res.statusCode() > 399) {
                        System.out.println(("https://api.mineskin.org/get/list/" + page + "?size=32") +
                                " returned HTTP-Status " + res.statusCode());
                        page = -1;
                        break;
                    }

                    JsonObject skinTex = new JsonParser().parse(res.body()).getAsJsonObject()
                            .get("data").getAsJsonObject().get("texture").getAsJsonObject();

                    res = getConnection("https://api.skindb.net/provide?value=" +
                            encodeURIComponent(skinTex.get("value").getAsString()) +
                            "&signature=" + encodeURIComponent(skinTex.get("signature").getAsString()));

                    if (res.statusCode() > 399) {
                        System.out.println(("https://api.mineskin.org/get/list/" + page + "?size=32") +
                                " returned HTTP-Status " + res.statusCode());
                        page = -1;
                        break;
                    } else if (new JsonParser().parse(res.body()).getAsJsonObject().get("msg").getAsString().equalsIgnoreCase("The skin is already in the database")) {
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
        }

        return con.execute();
    }

    private static String encodeURIComponent(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }
}