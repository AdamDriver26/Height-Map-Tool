import java.io.FileNotFoundException;

public class HeightGenerator {

	public static int[][] mapFinal() throws FileNotFoundException {

		int dim = Config.readConfig().dim;
		int border = Config.readConfig().border;
		int sea = Config.readConfig().sea;
		String shape = Config.readConfig().shape;
		int slope = Config.readConfig().slope;
		int hFreq = Config.readConfig().hFreq;
		int hSize = Config.readConfig().hSize;

		int[][] map = new int[dim][dim];

		// Generates the basic structure determined by map shape in the config file
		map = mapShape(dim, border, sea, shape, slope);
		
		// Generates hills and valleys
		map = mapHills(map, dim, border, hFreq, hSize);
		map = mapValleys(map, dim, border, hFreq, hSize);
		
		// The first 2 passes adding noise
		map = mapAddRandom(map, dim, border);
		map = mapAddRandom(map, dim, border);

		// The first 2 smoothing passes
		map = mapSmooth(map, dim, border);
		map = mapSmooth(map, dim, border);
		
		//map = mapAddRandom(map, dim, border);
		//map = mapSmooth(map, dim, border);
		//map = mapSmooth(map, dim, border);

		return map;
	}

	public static int[][] mapShape(int dim, int border, int sea, String shape, int slope) {

		int[][] map = new int[dim][dim];
		int r, di, dj;
		int rmax = (int) ((dim / 2.0) - border);
		//double angle = Math.atan(slope / rmax);

		for (int i = border; i < dim - border; i++) {

			for (int j = border; j < dim - border; j++) {
				// Finds the distance between map[i][j] and the centre and computes height by an
				// inverse square law

				di = (dim / 2) - i;
				dj = (dim / 2) - j;

				r = (int) (Math.sqrt(di*di + dj*dj) + 1);

				if ((shape.equals("triangle") || shape.equals("t")) && r < rmax) {
					// Linear decline from peak to sea level
					map[i][j] = rmax - r;
				}

				if (shape.equals("sphere") || shape.equals("s")) {
					// Height calculated using equation of sphere
					map[i][j] = (int) Math.sqrt(rmax * rmax - r * r);
				}

				if ((shape.contentEquals("bell") || shape.equals("b")) && r < rmax) {
					// cosine cubed with period equal to rmax
					map[i][j] = (int) (slope * Math.pow(Math.cos((r * Math.PI) / (rmax * 2)), 3));
				}
			}
		}

		return map;

	}

	public static int[][] mapHills(int[][] map, int dim, int border, int hFreq, int hSize) {

		for (int k = 0; k <= hFreq; k++) {

			// Randomly chooses hFreq many random points to become the centre of hills.

			int i = (int) (Math.random() * dim);
			int j = (int) (Math.random() * dim);

			// Ensures hills fit on the map.
			if ((i > border + hSize) && (j > border + hSize) && (i < dim - border - hSize) && (j < dim - border - hSize)) {

				// Randomly scales each hill
				double rand = Math.random();

				for (int l = -2 * hSize; l <= 2 * hSize; l++) {

					for (int m = -2 * hSize; m <= 2 * hSize; m++) {

						int r = (int) Math.round(Math.sqrt(l * l + m * m)) + 1;

						if (r * r < hSize) {
							map[i - l][j - m] += rand * (hSize - r * r);
						}
					}
				}
			}
			else {
				k--;
			}
		}

		return map;
	}

	public static int[][] mapValleys(int[][] map, int dim, int border, int hFreq, int hSize) {

		for (int k = 0; k <= hFreq; k++) {

			// Randomly chooses hFreq many random points to become the centre of valleys.

			int i = (int) (Math.random() * (dim - border - hSize) - (border + hSize));
			int j = (int) (Math.random() * (dim - border - hSize) - (border + hSize));
			// Ensures valleys fit on the map.
			if ((i > border + hSize) && (j > border + hSize) && (i < dim - border - hSize) && (j < dim - border - hSize)) {
				double rand = Math.random();
				for (int l = -2 * hSize; l <= 2 * hSize; l++) {
					for (int m = -2 * hSize; m <= 2 * hSize; m++) {
						int r = (int) Math.round(Math.sqrt(l * l + m * m)) + 1;
						if (r * r < hSize) {
							map[i - l][j - m] -= rand * (hSize - r * r);
						}
					}
				}
			} 
			else {
				k--;
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
