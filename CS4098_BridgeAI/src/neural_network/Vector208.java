package neural_network;

public class Vector208 {
	private int[] vector;
	
	public Vector208(){
		vector = new int[208];
	}
	
	public Vector208(Vector52 v1, Vector52 v2, Vector52 v3, Vector52 v4){
		for(int i = 0; i < 52; i++){
			vector[i] = v1.getVector()[i];
		}
		for(int i = 0; i < 52; i++){
			vector[i+52] = v2.getVector()[i+52];
		}
		for(int i = 0; i < 52; i++){
			vector[i+104] = v3.getVector()[i+104];
		}
		for(int i = 0; i < 52; i++){
			vector[i+156] = v4.getVector()[i+156];
		}
	}
}