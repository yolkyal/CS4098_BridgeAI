package neural_network;

public class Layer {
	private int start_index;
	private int end_index;
	
	public Layer(int start_index, int end_index) {
		this.start_index = start_index;
		this.end_index = end_index;
	}
	
	public int getSize(){
		return end_index - start_index;
	}
	
	public int getStart_index() {
		return start_index;
	}
	public void setStart_index(int start_index) {
		this.start_index = start_index;
	}
	public int getEnd_index() {
		return end_index;
	}
	public void setEnd_index(int end_index) {
		this.end_index = end_index;
	}
	
}
