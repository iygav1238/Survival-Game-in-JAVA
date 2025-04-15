package ai;

import Main.GamePanel;
import java.util.*;

public class AStar {

	GamePanel gp;
	Node[][] node;
	PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(node -> node.f));
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	public boolean goalReached = false;
	
	public int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
	public int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
	
	public AStar(GamePanel gp) {
		this.gp = gp;
	}
	
	public void findPath() {

		int nextX, nextY;
		double cost;
		
		while (openList.size() > 0) {
			currentNode = openList.poll();
			node[currentNode.y][currentNode.x].checked = true;
			
			if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
				goalReached = true;
				while (currentNode.parent != null) {
					pathList.add(currentNode);
					currentNode = currentNode.parent;
				}
				Collections.reverse(pathList);
				return;
			}
			
			for (int i = 0; i<8; i++) {
				nextX = currentNode.x + dx[i];
				nextY = currentNode.y + dy[i];
				cost = currentNode.g + ((dx[i] != 0 && dy[i] != 0) ? Math.sqrt(2.0) : 1.0);
				
				if (node[nextY][nextX].checked) continue;
				if (node[nextY][nextX].open && node[nextY][nextX].g <= cost) continue;
				
				if (0 <= nextX && nextX < gp.maxWorldCol && 0 <= nextY && nextY < gp.maxWorldRow) {
					if (node[nextY][nextX].solid) continue;
					node[nextY][nextX].g = cost;
					node[nextY][nextX].h = heuristic(node[nextY][nextX], goalNode);
					node[nextY][nextX].f = node[nextY][nextX].g + node[nextY][nextX].h;
					node[nextY][nextX].parent = currentNode;
					openList.add(node[nextY][nextX]);
				}
			}
		}
		
	}
	
	public void setNode(int startX, int startY, int goalX, int goalY) {
		reset();
		
		startNode = node[startY][startX];
		goalNode = node[goalY][goalX];
		startNode.g = 0;
		startNode.h = startNode.f = heuristic(startNode, goalNode);
		goalNode.f = 1e18;
		
		openList.add(startNode);
	}
	
	public void reset() {
		
		node = new Node[gp.maxWorldRow][gp.maxWorldCol];
		for (int i = 0; i<gp.maxWorldRow; i++) {
			for (int j = 0; j<gp.maxWorldCol; j++) {
				node[i][j] = new Node(j, i);
				if (gp.tileM.map[i][j] == 2 || gp.tileM.ObjectMap[i][j] == 1) node[i][j].solid = true;
			}
		}
		
		openList.clear();
		pathList.clear();
		goalReached = false;
	}
	
	public double heuristic(Node current, Node goal) {
	    int dx = Math.abs(current.x - goal.x);
	    int dy = Math.abs(current.y - goal.y);

	    double D = 1.0;
	    double D2 = Math.sqrt(2.0);

	    return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
	}
}