package de.sprax2013.skindb.backend.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import de.sprax2013.skindb.backend.constructors.Skin;

public class SkinAssetUtils {
	private static File assetDir;

	public static void create(Skin skin) throws MalformedURLException, IOException {
		create(skin, null);
	}

	public static void create(Skin skin, BufferedImage skinImg) throws MalformedURLException, IOException {
		if (getAssetDir().exists() && getAssetDir().isDirectory()) {
			if (skin != null && skin.hasID()) {
				File skinDir = new File(getAssetDir(), String.valueOf(skin.getID()));

				if (skin.getDuplicateOf() == null) {
					skinDir.mkdir();

					File orgSkin = new File(skinDir, "original.png"), cleanSkin = new File(skinDir, "skin.png");

					if (!orgSkin.exists()) {
						if (skinImg == null) {
							skinImg = ImageIO.read(new URL(skin.getMojangURL()));
						}

						ImageIO.write(copyImage(skinImg, BufferedImage.TYPE_4BYTE_ABGR), "PNG", orgSkin);
					}

					if (!cleanSkin.exists()) {
						if (skinImg == null) {
							skinImg = ImageIO.read(orgSkin);
						}

						ImageIO.write(SkinUtils.toCleanSkin(skinImg), "PNG", cleanSkin);
					}
				} else {
					if (!skinDir.exists() || !Files.isSymbolicLink(skinDir.toPath())) {
						FileUtils.deleteQuietly(skinDir);

						Files.createSymbolicLink(skinDir.toPath(),
								new File(getAssetDir(), String.valueOf(skin.getDuplicateOf())).toPath());
					}
				}
			} else {
				System.out.println("Der Skin hat noch keine ID!");
			}
		} else {
			System.out.println("Das Verzeichnis " + getAssetDir().getAbsolutePath() + " wurde nicht gefunden!");
		}
	}

	public static void createAll() {
		if (getAssetDir().exists() && getAssetDir().isDirectory()) {
			try {
				int offset = 0;
				Skin skin = null;

				do {
					skin = DatabaseUtils.getSkin(false, offset);

					if (skin != null) {
						create(skin);
					}

					offset++;
				} while (skin != null);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Das Verzeichnis " + getAssetDir().getAbsolutePath() + " wurde nicht gefunden!");
		}
	}

	private static final File getAssetDir() {
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

	private static BufferedImage copyImage(BufferedImage src, int imgType) {
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), imgType);

		Graphics g = result.createGraphics();
		g.drawImage(src, 0, 0, null);
		g.dispose();

		return result;
	}
}