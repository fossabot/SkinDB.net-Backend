package debug;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import de.sprax2013.skindb.backend.utils.SkinUtils;

/**
 * Creates a Point[] using an image like raw_4px.png
 */
class SkinToPointArray {
	private static final Color COLOR_BODY = new Color(0, 177, 211), COLOR_OVERLAY = new Color(251, 178, 0);

	private static final boolean SKIN_4PX = true; // 4px arm or 3px?
	private static final boolean BODY = false; // Body or overlay?

	public static void main(String[] args) throws IOException {
		InputStream in = SkinToPointArray.class
				.getResourceAsStream("/resources/skins/" + (SKIN_4PX ? "raw_4px.png" : "raw_3px.png"));
		BufferedImage img = ImageIO.read(in);
		in.close();

		List<Point> list = getCoordinates(img, BODY ? COLOR_BODY.getRGB() : COLOR_OVERLAY.getRGB());

		String s = "";

		for (Point p : list) {
			boolean isDiff = true;

			if (SKIN_4PX) {
				for (Point p2 : BODY ? SkinUtils.SKIN_3PX : SkinUtils.SKIN_3PX_OVERLAY) {
					if (p2.equals(p)) {
						isDiff = false;
						break;
					}
				}
			}

			if (!SKIN_4PX || isDiff) {
				if (!s.isEmpty()) {
					s += ',';
				}

				s += "new Point(" + p.x + "," + p.y + ")";
			}
		}

		String varName = "SKIN_" + (SKIN_4PX ? '4' : '3') + "PX" + (BODY ? "" : "_OVERLAY") + (SKIN_4PX ? "_DIFF" : "");

		System.out.println("private static final Point[] " + varName + "={" + s + "};");
	}

	/**
	 * Gets all coordinates where the pixel has the given RGB-value
	 *
	 * @param img The image to use
	 * @param rgb The RGB-Value to check
	 * 
	 * @return The matching coordinates
	 */
	static List<Point> getCoordinates(BufferedImage img, int rgb) {
		List<Point> result = new ArrayList<>();

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if (img.getRGB(x, y) == rgb) {
					result.add(new Point(x, y));
				}
			}
		}

		return result;
	}
}