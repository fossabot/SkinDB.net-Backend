package de.sprax2013.skindb.backend.queue;

import de.sprax2013.skindb.backend.skins.Skin;
import de.sprax2013.skindb.backend.utils.DatabaseUtils;
import de.sprax2013.skindb.backend.utils.HashingUtils;
import de.sprax2013.skindb.backend.utils.SkinAssetUtils;
import de.sprax2013.skindb.backend.utils.SkinUtils;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QueueManager {
    private static final ScheduledExecutorService runner = Executors.newSingleThreadScheduledExecutor();

    public static void init() {
        runner.scheduleAtFixedRate(() -> {
            QueueObject qObj;

            while ((qObj = DatabaseUtils.getNextQueued()) != null && !runner.isShutdown()) {
                System.out.println("Processing Queue #" + qObj.getID());

                try {
                    URL url = new URL(qObj.getSkinURL());

                    if (isWhitelisted(url)) {
                        File tmpFile = File.createTempFile("SkinDB_", ".png");
                        FileUtils.copyURLToFile(getHTTPS(url), tmpFile);

                        BufferedImage img = ImageIO.read(tmpFile);

                        if (SkinUtils.hasSkinDimensions(img)) {
                            BufferedImage cleanImg = SkinUtils.removeUnusedSkinParts(SkinUtils.upgradeSkin(img));

                            ImageIO.write(cleanImg, "PNG", tmpFile);

                            String cleanHash = HashingUtils.getHash(tmpFile);

                            Skin skin;
                            if ((skin = DatabaseUtils.getSkin(cleanHash)) == null) {
                                skin = DatabaseUtils.createSkin(cleanHash, normalizeURL(url),
                                        qObj.getTextureValue(), qObj.getTextureSignature(),
                                        SkinUtils.isSlim(cleanImg), -1);

                                if (skin != null) {
                                    SkinAssetUtils.create(skin, img, cleanImg);

                                    DatabaseUtils.updateQueueObject(qObj.getID(), skin.getID(), QueueStatus.SUCCESS);
                                    System.out.println("Queue #" + qObj.getID() + " => Skin #" + skin.getID());
                                } else {
                                    DatabaseUtils.updateQueueObject(qObj.getID(), -1, QueueStatus.UNKNOWN_ERROR);
                                }
                            } else {
                                Skin dupSkin = DatabaseUtils.createSkin(cleanHash, qObj.getSkinURL(),
                                        qObj.getTextureValue(), qObj.getTextureSignature(),
                                        SkinUtils.isSlim(cleanImg), skin.getID());

                                if (dupSkin != null) {
                                    SkinAssetUtils.create(dupSkin, img, cleanImg);

                                    DatabaseUtils.updateQueueObject(qObj.getID(), dupSkin.getID(),
                                            QueueStatus.DUPLICATE);
                                    System.out.println("Queue #" + qObj.getID() + " => Skin #" + dupSkin.getID() + " " +
                                            "(Duplicate of #" + skin.getID() + ")");
                                } else {
                                    DatabaseUtils.updateQueueObject(qObj.getID(), -1, QueueStatus.UNKNOWN_ERROR);
                                }
                            }
                        } else {
                            DatabaseUtils.updateQueueObject(qObj.getID(), qObj.getSkinID(),
                                    QueueStatus.WRONG_DIMENSIONS);
                        }

                        if (!tmpFile.delete()) {
                            System.err.println("Could not delete " + tmpFile.getAbsolutePath());
                        }
                    } else {
                        DatabaseUtils.updateQueueObject(qObj.getID(), qObj.getSkinID(),
                                QueueStatus.NON_WHITELISTED_URL);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();

                    DatabaseUtils.updateQueueObject(qObj.getID(), -1, QueueStatus.UNKNOWN_ERROR);
                }
            }
        }, 5, 60, TimeUnit.SECONDS);
    }

    public static void deInit() {
        runner.shutdown();

        try {
            runner.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        runner.shutdownNow();
    }

    private static boolean isWhitelisted(URL url) {
        return url.getHost().equalsIgnoreCase("textures.minecraft.net");
    }

    public static URL getHTTPS(URL url) throws MalformedURLException {
        if (url.getHost().equalsIgnoreCase("textures.minecraft.net")) {
            return new URL("https", url.getHost(), url.getPort(), url.getFile());
        }

        return url;
    }

    private static String normalizeURL(URL url) throws MalformedURLException {
        if (url.getHost().equalsIgnoreCase("textures.minecraft.net")) {
            return getHTTPS(url).toString().toLowerCase();
        }

        return url.toString();
    }
}