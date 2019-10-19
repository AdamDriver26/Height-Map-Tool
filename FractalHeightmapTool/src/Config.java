import java.io.*;
import java.util.Scanner;

public class Config {

	String name; // The name of the png image 
	int dim; // Dimensions of the map (> 0)
	int border; // Border around map (1 - dim/2)
	int sea; // Sea level (0 - 255)
	String shape; // The main shape of the slope to the peak (sphere, s, triangle, t)
	int slope; // The steepness of the slope (1 - 255)
	int hFreq; // Frequency of hills (0 - 100)
	int hSize; // Size of hills (1 - 255)

	Config(String n, int d, int b, int c, String s, int l, int f, int h) {
		name = n;
		dim = d;
		border = b;
		sea = c;
		shape = s;
		slope = l;
		hFreq = f;
		hSize = h;
	}

	public static void writeConfig() throws Exception {

		File param = new File("config.txt");
		PrintStream p = new PrintStream(param);

		p.println("Image filename: default");
		p.println("Map dimensions: 500");
		p.println("Map border: 10");
		p.println("Sea level: 25");
		p.println("Map shape: sphere");
		p.println("Slope steepness: 250");
		p.println("Hill frequency: 50");
		p.println("Hill size: 20");

		p.close();
	}

	public static Config readConfig() throws FileNotFoundException {

		File param = new File("config.txt");

		Scanner s = new Scanner(param);

		String name = s.nextLine().substring(16);
		int dim = Integer.valueOf(s.nextLine().substring(16));
		int border = Integer.valueOf(s.nextLine().substring(12));
		int sea = Integer.valueOf(s.nextLine().substring(11));
		String shape = s.nextLine().substring(11);
		int slope = Integer.valueOf(s.nextLine().substring(17));
		int hFreq = Integer.valueOf(s.nextLine().substring(16));
		int hSize = Integer.valueOf(s.nextLine().substring(11));

		s.close();

		return new Config(name, dim, border, sea, shape, slope, hFreq, hSize);

	}

}
