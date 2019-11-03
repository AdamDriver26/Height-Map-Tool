import java.io.FileNotFoundException;

public class Upscale {
	
	public static LayerStack crop(LayerStack layers) throws FileNotFoundException {
		int dim = Config.readConfig().dim;
		
		int[][] big = new int[dim/2][dim/2];
		int[][] mid = new int[dim/4][dim/4];
		int[][] small = new int[dim/8][dim/8];
		
		for (int row = 0; row < dim/2; row++) {
			for (int col = 0; col < dim/2; col++) {
				big[row][col] = layers.layer1[row][col];
			}
		}
		
		for (int row = 0; row < dim/4; row++) {
			for (int col = 0; col < dim/4; col++) {
				mid[row][col] = layers.layer2[row][col];
			}
		}
		
		for (int row = 0; row < dim/8; row++) {
			for (int col = 0; col < dim/8; col++) {
				small[row][col] = layers.layer3[row][col];
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
		
		int[][] l1 = new int[dim][dim];
		int[][] l2 = new int[dim][dim];
		int[][] l3 = new int[dim][dim];
		
		for (int sRow = 0; sRow < dim/16; sRow++) {
			for (int sCol = 0; sCol < dim/16; sCol++) {
				for (int bRow = 0; bRow < dim; bRow++) {
					for (int bCol = 0; bCol < dim; bCol++) {
						
						if ( (bRow <= sRow*16) && (bRow < (sRow + 1)*16)  &&  (bCol <= sCol*16) && (bCol < (sCol + 1)*16) ) {
							l1[bRow][bCol] = big[sRow][sCol];
						}
						
					}
				}
			}
		}
		
		for (int sRow = 0; sRow < dim/32; sRow++) {
			for (int sCol = 0; sCol < dim/32; sCol++) {
				for (int bRow = 0; bRow < dim; bRow++) {
					for (int bCol = 0; bCol < dim; bCol++) {
						
						if ( (bRow <= sRow*32) && (bRow < (sRow + 1)*32)  &&  (bCol <= sCol*32) && (bCol < (sCol + 1)*32) ) {
							l2[bRow][bCol] = mid[sRow][sCol];
						}
						
					}
				}
			}
		}
		
		for (int sRow = 0; sRow < dim/64; sRow++) {
			for (int sCol = 0; sCol < dim/64; sCol++) {
				for (int bRow = 0; bRow < dim; bRow++) {
					for (int bCol = 0; bCol < dim; bCol++) {
						
						if ( (bRow <= sRow*64) && (bRow < (sRow + 1)*64)  &&  (bCol <= sCol*64) && (bCol < (sCol + 1)*64) ) {
							l3[bRow][bCol] = small[sRow][sCol];
						}
						
					}
				}
			}
		}
		
		// Placeholder return LayerStack, change values to resized arrays.
		LayerStack stretchedLayers = new LayerStack(dim, layers.layer0, l1, l2, l3);
		return stretchedLayers;
	}
}
