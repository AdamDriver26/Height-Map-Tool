import java.io.FileNotFoundException;

public class HeightGenerator {

	public static int[][] mapFinal() throws FileNotFoundException {

		int dim = Config.readConfig().dim;
		int border = Config.readConfig().border;
		int sea = Config.readConfig().sea;
		String shape = Config.readConfig().shape;
		int slope = Config.readConfig().slope;

		int[][] map = new int[dim][dim];

		map = mapShape(dim, border, sea, shape, slope);

		map = mapSmooth(map, dim, border);

		map = mapAddRandom(map, dim, border);

		map = mapSmooth(mapSmooth(map, dim, border), dim, border);

		return map;
	}

	public static int[][] mapShape(int dim, int border, int sea, String shape, int slope) {

		int[][] map = new int[dim][dim];

		int r, di, dj;

		int rmax = (int) (1.5 * ((dim / 2) - border));

		for (int i = border; i < dim - border; i++) {

			for (int j = border; j < dim - border; j++) {
				// Finds the distance between map[i][j] and the centre and computes height by an
				// inverse square law

				di = (dim / 2) - i;
				dj = (dim / 2) - j;

				r = (int) Math.round(Math.sqrt(di * di + dj * dj)) + 1;

				if (shape.equals("sphere") || shape.equals("s")) {

					map[i][j] = slope - (r * r) / (rmax);
				}

				if (shape.equals("triangle") || shape.equals("t")) {

					map[i][j] = slope / (rmax - r);
				}
			}
		}

		return map;

	}

	public static int[][] mapAddRandom(int[][] map, int dim, int border) {

		for (int i = border; i < dim - border; i++) {

			for (int j = border; j < dim - border; j++) {

				map[i][j] += (int) (Math.random() * 125 - 125);
			}
		}

		return map;
	}

	public static int[][] mapSmooth(int[][] map, int dim, int border) {

		// Smooths locally
		for (int i = border; i < dim - border; i++) {

			for (int j = border; j < dim - border; j++) {

				map[i][j] = (int) ((0.25 * map[i - 1][j + 1] + 0.5 * map[i][j + 1] + 0.25 * map[i + 1][j + 1]
						+ 0.5 * map[i - 1][j] + 1.5 * map[i][j] + 0.5 * map[i + 1][j] + 0.25 * map[i - 1][j - 1]
						+ 0.5 * map[i][j - 1] + 0.25 * map[i + 1][j - 1]) / 4.5);
			}
		}

		return map;
	}
}
