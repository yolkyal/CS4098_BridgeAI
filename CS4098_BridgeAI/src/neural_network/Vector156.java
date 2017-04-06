package neural_network;

public class Vector156 {
	private int[] vector;
	
	public Vector156(){
		vector = new int[156];
	}
	
	public Vector156(Vector52 v1, Vector52 v2, Vector52 v3){
		vector = new int[156];
		for(int i = 0; i < 52; i++){
			vector[i] = v1.getVector()[i];
		}
		for(int i = 0; i < 52; i++){
			vector[i+52] = v2.getVector()[i];
		}
		for(int i = 0; i < 52; i++){
			vector[i+104] = v3.getVector()[i];
		}
	}

	public int[] getVector() {
		return vector;
	}

	public void setVector(int[] vector) {
		this.vector = vector;
	}
	
	public double[] getDoubleVector(){
		double[] result = new double[156];
		for(int i = 0; i < 156; i++){
			result[i] = (double)vector[i];
		}
		return result;
	}
	
}
