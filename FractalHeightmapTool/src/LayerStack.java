/**
 * This class creates an object representing 4 domains to be manipulated.
 * @author Adam Driver
 *
 */
public class LayerStack {
	/**
	 * Layer 0 of the stack.
	 */
	int[][] layer0;
	/**
	 * Layer 1 of the stack.
	 */
	int[][] layer1;
	/**
	 * Layer 2 of the stack.
	 */
	int[][] layer2;
	/**
	 * Layer 3 of the stack.
	 */
	int[][] layer3;
	
	/**
	 * The constructor for the LayerStack class.
	 * @param dim the map dimension.
	 * @param l0 the layer0 dummy variable.
	 * @param l1 the layer1 dummy variable.
	 * @param l2 the layer2 dummy variable.
	 * @param l3 the layer3 dummy variable.
	 */
	public LayerStack(int dim, int[][] l0, int[][] l1, int[][] l2, int[][] l3){
		layer0 = new int[dim][dim];
		layer1 = new int[dim][dim];
		layer2 = new int[dim][dim];
		layer3 = new int[dim][dim];
	}
	
	/**
	 * "Squashes" the layers of a LayerStack object inversely weighted by the layer index. 
	 * @param layers the LayerStack object to "squash".
	 * @param dim the map dimension.
	 * @return an array[][] of the squashed layers.
	 */
	public static int[][] WeightedSquash(LayerStack layers, int dim){
		int[][] flat = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				
					flat[i][j] += (4*layers.layer0[i][j] + 3*layers.layer1[i][j]/5 + 2*layers.layer2[i][j]/5 + layers.layer3[i][j])/10;
			}
		}
		return flat;
	}
	
	
}
