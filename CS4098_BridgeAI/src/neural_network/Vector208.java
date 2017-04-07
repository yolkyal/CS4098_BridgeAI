package neural_network;

public class Vector208 {
	private int[] vector;
	
	public Vector208(){
		vector = new int[208];
	}
	
	public Vector208(Vector52 v1, Vector52 v2, Vector52 v3, Vector52 v4){
		vector = new int[208];
		for(int i = 0; i < 52; i++){
			vector[i] = v1.getVector()[i];
		}
		for(int i = 0; i < 52; i++){
			vector[i+52] = v2.getVector()[i];
		}
		for(int i = 0; i < 52; i++){
			vector[i+104] = v3.getVector()[i];
		}
		for(int i = 0; i < 52; i++){
			vector[i+156] = v4.getVector()[i];
		}
	}
	
	public double[] getDoubleVector(){
		double[] result = new double[208];
		for(int i = 0; i < 208; i++){
			result[i] = (double)vector[i];
		}
		return result;
	}
}
