package de.sprax2013.skindb.back_end;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SkinExtractionTest {
	static final File orgSkinFile = new File(
			"C:\\Users\\chris\\Desktop\\a614ad9427c16d0ded6f6bd0fa18c9121667bda6a7ddc7902799d36584ee5773.png");

	public static void main(String[] args) throws IOException {
		BufferedImage orgSkin = ImageIO.read(orgSkinFile);

		if (orgSkin.getWidth() == 32) {

		} else {
			throw new IllegalStateException("Skin zu breit");
		}

		Graphics2D graph = orgSkin.createGraphics();
		graph.setComposite(AlphaComposite.Clear);

	}
}