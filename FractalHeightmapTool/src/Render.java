import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Render {

	public static void createPNG() throws IOException {

		String name = Config.readConfig().name;
		int dim = Config.readConfig().dim;

		BufferedImage draw = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = draw.createGraphics();

		g2d.setColor(Color.darkGray);
		g2d.fillRect(0, 0, dim, dim);

		File file = new File(name + ".png");
		ImageIO.write(draw, "png", file);
	}

	public static void drawHeight(int[][] map) throws IOException {

		int dim = Config.readConfig().dim;
		String name = Config.readConfig().name; 
		int sea = Config.readConfig().sea;

		BufferedImage draw = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = draw.createGraphics();

		for (int i = 0; i < dim; i++) {

			for (int j = 0; j < dim; j++) {

				if (map[i][j] <= sea) {
					g2d.setColor(Color.BLUE);
					g2d.drawLine(i, j, i, j);
				}

				else if (map[i][j] >= 255) {
					g2d.setColor(Color.WHITE);
					g2d.drawLine(i, j, i, j);
				}

				else {
					Color green = new Color(0, map[i][j], 0);
					g2d.setColor(green);
					g2d.drawLine(i, j, i, j);
				}

			}
		}

		File file = new File(name + ".png");
		ImageIO.write(draw, "png", file);

	}
}