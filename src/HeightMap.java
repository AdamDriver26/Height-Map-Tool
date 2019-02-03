import java.io.*;

public class FractalHeightMap {

	public static void main(String[] args) throws Exception {
		
		File param = new File("config.txt");
		
		if (param.exists() == false) {
			Config.writeConfig();
		}
		
		String name = Config.readConfig().name;
		
		File map = new File(name + ".png");

		if (map.exists() == false) {
			Render.createPNG();
		}

		
		Render.createPNG();
		
		Render.drawHeight(HeightGenerator.mapFinal());
		
		
	}

}
