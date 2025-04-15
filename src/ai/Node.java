package ai;

public class Node {
	
	Node parent;
	public int x, y;
	double g, h, f;
	boolean solid, open, checked;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.g = this.h = this.f = 1e18;
		this.solid = this.open = this.checked = false;
	}
	
}
