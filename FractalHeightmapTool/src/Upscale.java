import java.io.FileNotFoundException;

public class Upscale {
	
	public static LayerStack crop(LayerStack layers) throws FileNotFoundException {
		int dim = Config.readConfig().dim;
		
		int[][] big = new int[dim/2][dim/2];
		int[][] mid = new int[dim/4][dim/4];
		int[][] small = new int[dim/8][dim/8];
		
		for (int i = 0; i < dim/2; i++) {
			for (int j = 0; j < dim/2; j++) {
				big[i][j] = layers.layer1[i][j];
			}
		}
		
		for (int i = 0; i < dim/4; i++) {
			for (int j = 0; j < dim/4; j++) {
				mid[i][j] = layers.layer2[i][j];
			}
		}
		
		for (int i = 0; i < dim/8; i++) {
			for (int j = 0; j < dim/8; j++) {
				small[i][j] = layers.layer3[i][j];
			}
		}
		
		LayerStack croppedLayers = new LayerStack(dim, layers.layer0, big, mid, small); 
		return croppedLayers;
	}
	
	public static LayerStack stretch(LayerStack layers) throws FileNotFoundException {
		int dim = Config.readConfig().dim;
		
		int[][] big = layers.layer1;
		int[][] mid = layers.layer2;
		int[][] small = layers.layer3;
		
		int b = big.length;
		int m = mid.length;
		int s = small.length;
		
		int[][] l1 = new int[dim][dim];
		int[][] l2 = new int[dim][dim];
		int[][] l3 = new int[dim][dim];
		
		for (int h = 0; h < b; h++) {
			for (int i = 0; i < dim/2; i++) {
				for (int j = 0; j < dim/2; j++) {
					big[i][j] = layers.layer1[i][j];
				}
			}
		}
		
		// Placeholder return LayerStack, change values to resized arrays.
		LayerStack stretchedLayers = new LayerStack(dim, layers.layer0, l1, l2, l3);
		return stretchedLayers;
	}
}
