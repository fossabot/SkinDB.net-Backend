package de.sprax2013.skindb.backend.utils;

import de.sprax2013.skindb.backend.queue.QueueManager;
import de.sprax2013.skindb.backend.skins.Skin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

public class SkinAssetUtils {
    /**
     * @throws MalformedURLException If the URL from <i>skin</i> is invalid
     * @throws IOException           When an error occurs while reading/writing images
     */
    public static boolean create(Skin skin, BufferedImage img, BufferedImage cleanImg) throws IOException {
        if (!skin.isDuplicate()) {
            if (img == null) {
                img = ImageIO.read(QueueManager.getHTTPS(new URL(skin.getSkinURL())));
            }

            if (cleanImg == null) {
                cleanImg = SkinUtils.removeUnusedSkinParts(SkinUtils.upgradeSkin(img));
            }

            File tmpFile = File.createTempFile("SkinDB_", ".png");
            tmpFile.deleteOnExit();

            ImageIO.write(cleanImg, "PNG", tmpFile);

            boolean result = DatabaseUtils.setSkinHash(skin.getID(), HashingUtils.getHash(tmpFile)) != null && DatabaseUtils.createSkinImages(skin.getID(), img, cleanImg);

            Files.deleteIfExists(tmpFile.toPath());

            return result;
        }

        return false;
    }

    // TODO: 1000er Blöcke abfertigen statt einzelne Anfragen
    public static void createAll() {
        try {
            int offset = 0;
            Skin skin;

            do {
                skin = DatabaseUtils.getSkin(offset);

                if (skin != null) {
                    create(skin, null, null);
                }

                offset++;
            } while (skin != null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}