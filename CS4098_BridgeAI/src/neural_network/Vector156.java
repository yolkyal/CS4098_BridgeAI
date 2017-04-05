package neural_network;

public class Vector156 {
	private int[] vector;
	
	public Vector156(){
		vector = new int[156];
	}
	
	public Vector156(Vector52 v1, Vector52 v2, Vector52 v3){
		for(int i = 0; i < 52; i++){
			vector[i] = v1.getVector()[i];
		}
		for(int i = 0; i < 52; i++){
			vector[i+52] = v2.getVector()[i+52];
		}
		for(int i = 0; i < 52; i++){
			vector[i+104] = v3.getVector()[i+104];
		}
	}

	public int[] getVector() {
		return vector;
	}

	public void setVector(int[] vector) {
		this.vector = vector;
	}
	
}
