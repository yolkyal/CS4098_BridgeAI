package neural_network;

import bridge_data_structures.*;

public class Vector52 {
	private int[] vector;
	
	public Vector52(){
		vector = new int[52];
	}
	
	public Vector52(Card card){
		vector = new int[52];
		vector[card.getVector52Index()] = 1;
	}
	
 	public Vector52(Hand hand){
		vector = new int[52];
		setVectorFromHand(hand);
	}
 	
 	public Vector52 deepCopy(){
 		Vector52 copy_vector = new Vector52();
 		int[] copy_ints = copy_vector.getVector();
 		for(int i = 0; i < 52; i++){
 			copy_ints[i] = vector[i];
 		}
 		copy_vector.setVector(copy_ints);
 		return copy_vector;
 	}
	
	private void setVectorFromHand(Hand hand){
		for(Card card : hand.getCards()){
			int vector_index = card.getVector52Index();
			vector[vector_index] = 1;
		}
	}
	
	public int[] getVector(){
		return vector;
	}
	
	public void setVector(int[] vector){
		this.vector = vector;
	}
	
	public void setHand(Hand hand){
		setVectorFromHand(hand);
	}
	
	public void addCard(Card card){
		vector[card.getVector52Index()] = 1;
	}
	
	public void takeCard(Card card){
		vector[card.getVector52Index()] = 0;
	}
	
	public void print(){
		for(int i = 0; i < 52; i++){
			System.out.print(vector[i]);
		}
		System.out.println();
	}
	
}
