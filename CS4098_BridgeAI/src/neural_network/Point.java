package neural_network;

public class Point {
	//Used as a key for the weights hashmap
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode(){
		return x * 1000 + y;
	}
	
	@Override
	public boolean equals(Object o){
		Point p = (Point)o;
		if (p.x == this.x && p.y == this.y) return true;
		return false;
	}
	
}
