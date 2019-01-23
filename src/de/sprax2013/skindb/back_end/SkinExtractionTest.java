package de.sprax2013.skindb.back_end;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SkinExtractionTest {
//	static final File orgSkinFile = new File("C:\\Users\\Christian\\Desktop\\a614ad9427c16d0ded6f6bd0fa18c9121667bda6a7ddc7902799d36584ee5773.png");
	static final File orgSkinFile = new File(System.getProperty("user.home"),
			"Desktop" + File.separator + "Skin-Kopie.png");

	public static void main(String[] args) throws IOException {
//		getCleanSkin(ImageIO.read(orgSkinFile));

		int x = 50, y = 16, w = 2, h = 4;

		BufferedImage img = getCleanSkin(ImageIO.read(orgSkinFile));
		Graphics2D graph = img.createGraphics();

		graph.setColor(Color.GREEN);

		for (int i = 0; i < h; i++) {
			for (int i2 = 0; i2 < w; i2++) {
				graph.fillRect(x + i2, y + i, 1, 1);

				System.out.println("(" + (x + i2) + "|" + (y + i) + ")");
			}
		}

		graph.dispose();

		ImageIO.write(img, "PNG", new File(orgSkinFile.getParentFile(), "newSkin.png"));
	}

	public static BufferedImage getSkin(String skinURL) {
		try {
			return ImageIO.read(new URL(skinURL));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static BufferedImage getCleanSkin(String skinURL) {
		return getCleanSkin(getSkin(skinURL));
	}

	public static BufferedImage getCleanSkin(BufferedImage skin) {
		if (skin != null && skin.getWidth() == 64 && (skin.getHeight() == 32 || skin.getHeight() == 64)) {
			BufferedImage newSkin = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);

			Graphics2D graph = newSkin.createGraphics();
			graph.drawImage(skin, 0, 0, null); // Original Skin aufs neues Bild

			graph.setComposite(AlphaComposite.Clear); // Transparente Pixel auswählen

			// Alle im Spiel nicht sichtbaren Bereiche entfernen
			graph.fillRect(0, 0, 8, 8);
			graph.fillRect(24, 0, 16, 8);
			graph.fillRect(56, 0, 8, 8);

			graph.fillRect(0, 16, 4, 4);
			graph.fillRect(12, 16, 8, 4);
			graph.fillRect(36, 16, 8, 4);
			graph.fillRect(52, 16, 12, 4);

			graph.fillRect(56, 20, 8, 28);

			graph.fillRect(0, 32, 4, 4);
			graph.fillRect(12, 32, 8, 4);
			graph.fillRect(36, 32, 8, 4);
			graph.fillRect(52, 32, 4, 4);

			graph.fillRect(0, 48, 4, 4);
			graph.fillRect(12, 48, 8, 4);
			graph.fillRect(28, 48, 8, 4);
			graph.fillRect(44, 48, 8, 4);
			graph.fillRect(60, 48, 4, 4);

			graph.dispose();

			return newSkin;
		}

		return null;
	}

	final int[] xCoords = { 50, 51, 50, 51, 50, 51, 50, 51,

			54, 55 };
	final int[] yCoords = { 16, 16, 17, 17, 18, 18, 19, 19,

			20, 20, 21, 21 };

	public static boolean isSlimOnly(BufferedImage skin) {
		boolean slimOnly = true;
		if (skin.getWidth() == 64 && (skin.getHeight() == 32 || skin.getHeight() == 64)) {

			if (skin.getHeight() == 64) {
			}
		}

		return slimOnly;
	}

	private static boolean isTransparent(BufferedImage img, int x, int y) {
		return (img.getRGB(x, y) >> 24) == 0x00;
	}
}