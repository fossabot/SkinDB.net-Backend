package de.sprax2013.skindb.backend.utils;

import de.sprax2013.skindb.backend.queue.QueueManager;
import de.sprax2013.skindb.backend.skins.Skin;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

public class SkinAssetUtils {
    private static File assetDir;

    /**
     * @throws MalformedURLException If the URL from <i>skin</i> is invalid
     * @throws IOException           When an error occurs while reading/writing images
     */
    public static void create(Skin skin, BufferedImage img, BufferedImage cleanImg) throws IOException {
        File skinDir = new File(getAssetDir(), String.valueOf(skin.getID()));

        if (!skin.isDuplicate()) {
            //noinspection ResultOfMethodCallIgnored
            skinDir.mkdirs();

            File orgSkinFile = new File(skinDir, "original.png"),
                    cleanSkinFile = new File(skinDir, "skin.png");

            if (!orgSkinFile.exists()) {
                img = img == null ?
                        ImageIO.read(QueueManager.getHTTPS(new URL(skin.getSkinURL()))) :
                        copyImage(img);

                ImageIO.write(img, "PNG", orgSkinFile);
            }

            if (!cleanSkinFile.exists()) {
                cleanImg = cleanImg == null ?
                        SkinUtils.removeUnusedSkinParts(SkinUtils.upgradeSkin(img)) :
                        copyImage(cleanImg);

                ImageIO.write(cleanImg, "PNG", cleanSkinFile);
            }
        } else {
            if (!skinDir.exists() || !Files.isSymbolicLink(skinDir.toPath())) {
                FileUtils.deleteQuietly(skinDir);

                Files.createSymbolicLink(skinDir.toPath(),
                        new File(getAssetDir(), String.valueOf(skin.getDuplicateOf())).toPath());
            }
        }
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

    public static boolean isReady() {
        return getAssetDir().exists() && getAssetDir().isDirectory() && getAssetDir().canRead() && getAssetDir().canWrite();
    }

    public static File getAssetDir() {
        if (assetDir == null) {
            assetDir = new File("SkinAssets");

            if (Files.isSymbolicLink(assetDir.toPath())) {
                try {
                    assetDir = Files.readSymbolicLink(assetDir.toPath()).toFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return assetDir;
    }

    private static BufferedImage copyImage(BufferedImage src) {
        BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        Graphics g = result.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();

        return result;
    }
}