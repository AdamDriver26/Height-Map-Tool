import java.io.*;

public class FractalHeightMap {

	public static void main(String[] args) throws Exception {
		
		File param = new File("config.txt");
		
		if (!param.exists()) {
			Config.writeConfig();
		}
		
		String name = Config.readConfig().name;
		
		File map = new File(name + ".png");

		if (!map.exists()) {
			Render.createPNG();
		}
		
		Render.drawHeight(HeightGenerator.mapFinal());
	
	}

}
