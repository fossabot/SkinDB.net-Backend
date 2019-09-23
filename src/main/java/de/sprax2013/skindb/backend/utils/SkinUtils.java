package de.sprax2013.skindb.backend.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SkinUtils {
    public static BufferedImage upgradeSkin(BufferedImage skin) {
        if (skin.getHeight() == 64) return skin;

        BufferedImage newImg = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D graphics = newImg.createGraphics();

        graphics.drawImage(skin, 0, 0, null);

        copyFlipped(graphics, skin, 8, 16, 4, 4, 24, 48);
        copyFlipped(graphics, skin, 4, 16, 4, 4, 20, 48);
        copyFlipped(graphics, skin, 44, 16, 4, 4, 36, 48);
        copyFlipped(graphics, skin, 48, 16, 4, 4, 40, 48);
        copyFlipped(graphics, skin, 4, 20, 4, 12, 20, 52);
        copyFlipped(graphics, skin, 8, 20, 4, 12, 16, 52);
        copyFlipped(graphics, skin, 12, 20, 4, 12, 28, 52);
        copyFlipped(graphics, skin, 0, 20, 4, 12, 24, 52);

        copyFlipped(graphics, skin, 44, 20, 4, 12, 36, 52);
        copyFlipped(graphics, skin, 48, 20, 4, 12, 32, 52);
        copyFlipped(graphics, skin, 52, 20, 4, 12, 44, 52);
        copyFlipped(graphics, skin, 40, 20, 4, 12, 40, 52);

        graphics.dispose();

        return newImg;
    }

    public static BufferedImage removeUnusedSkinParts(BufferedImage skin) {
        BufferedImage newImg = new BufferedImage(skin.getWidth(), skin.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D graphics = newImg.createGraphics();
        graphics.drawImage(skin, 0, 0, null);

        deleteArea(graphics, 0, 0, 8, 8);
        deleteArea(graphics, 24, 0, 16, 8);
        deleteArea(graphics, 56, 0, 8, 8);
        deleteArea(graphics, 0, 16, 4, 4);
        deleteArea(graphics, 12, 16, 8, 4);
        deleteArea(graphics, 36, 16, 8, 4);
        deleteArea(graphics, 56, 16, 8, 16);
        deleteArea(graphics, 52, 16, 4, 4);

        if (skin.getHeight() == 32) {
            deleteArea(graphics, 32, 8, 32, 8);
            deleteArea(graphics, 40, 0, 16, 8);
        } else if (skin.getHeight() == 64) {
            deleteArea(graphics, 0, 32, 4, 4);
            deleteArea(graphics, 0, 48, 4, 4);
            deleteArea(graphics, 12, 32, 8, 4);
            deleteArea(graphics, 12, 48, 8, 4);
            deleteArea(graphics, 28, 48, 8, 4);
            deleteArea(graphics, 36, 32, 8, 4);
            deleteArea(graphics, 44, 48, 8, 4);
            deleteArea(graphics, 52, 32, 4, 4);
            deleteArea(graphics, 60, 48, 4, 4);
            deleteArea(graphics, 56, 32, 8, 16);
        }

        graphics.dispose();

        return skin;
    }

    public static boolean isSlim(BufferedImage skin) {
        return skin.getRGB(50, 16) >> 24 == 0x00;
    }

    public static boolean isLegacySkin(BufferedImage img) {
        return img.getHeight() == 32;
    }

    public static boolean hasSkinDimensions(BufferedImage img) {
        return img.getWidth() == 64 && (img.getHeight() == 64 || img.getHeight() == 32);
    }

    public static void copyFlipped(Graphics graphics, BufferedImage skin, int orgX, int orgY, int width, int height, int targetX, int targetY) {
        graphics.drawImage(skin, targetX + width, targetY, targetX, targetY + height, orgX, orgY, orgX + width, orgY + height, null);
    }

    public static void copyVerticalFlipped(Graphics graphics, BufferedImage skin, int orgX, int orgY, int width, int height, int targetX, int targetY) {
        graphics.drawImage(skin, targetX, targetY + height, targetX + width, targetY, orgX, orgY, orgX + width, orgY + height, null);
    }

    public static void deleteArea(Graphics2D graphics, int x, int y, int width, int height) {
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(x, y, width, height);
        graphics.setComposite(AlphaComposite.Src);
    }

//    Implement this in JS (Api.Sprax2013.de)
//    /**
//     * Checks if a players skin model is slim or the default. The Alex model is
//     * slime while the Steve model is default.
//     */
//    private static boolean isSlimSkin(UUID playerUUID) {
//        return (playerUUID.hashCode() & 1) == 1;
//    }
}