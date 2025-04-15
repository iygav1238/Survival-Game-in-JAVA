package entity;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.HitBox;
import monster.Fairy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

	protected GamePanel gp;
	
	public BufferedImage image;
	public String name;
	
	// CHARACTER STATE
	public int worldX = 0, worldY = 0;
	public int nextWorldX, nextWorldY;
	public int speed;
	public int power = 1;
	public int magic = 0;
	public int defence = 0;
	public int health = 10;
	public int screenX, screenY;
	public int maxHealth;
	public int invincible = -1;
	public int gaveExp;
	
	// ENTITY MOVE
	public Random rand = new Random();
	public int moveCount = 0;
	public boolean onPath = true;
	
	private int r;
	
	public Vector<BufferedImage> left = new Vector<>();
	public Vector<BufferedImage> right = new Vector<>();
	
	public BufferedImage titleImage;
	public String direction;
	public String d1, d2; // ENTITY DIRECTION
	public String p[] = {"up", "down", "left", "right"};
	public boolean directInv = false;
	
	public int spriteCounter = 0;
	public int spriteNum = 0;
	public HitBox hitbox = new HitBox(8, 8, 32, 32);
	public int hitboxDefaultX, hitboxDefaultY;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {
		moveCount++;
		if (moveCount == gp.FPS/3) {
			
			if (onPath == true) {
				int goalX = (gp.player.worldX + gp.player.hitbox.x)/gp.tileSize;
				int goalY = (gp.player.worldY + gp.player.hitbox.y)/gp.tileSize;
				
				if (worldX/gp.tileSize == goalX && worldY/gp.tileSize == goalY) onPath = false;				
				else searchPath(goalX, goalY);
			} 
			if (onPath == false) {
				int i = rand.nextInt(100);
				int j = rand.nextInt(100);
				
				d1 = direction = p[i/25];
				d2 = p[j/25];
				onPath = true;
			}
			
			moveCount = 0;
			//System.out.println(d1+ " " + d2);
		}
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;	
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
		switch(direction) {
		case "up":
			image = (directInv) ? left.get(spriteNum):right.get(spriteNum);
			break;
		case "down":
			image = (directInv) ? left.get(spriteNum):right.get(spriteNum);
			break;
		case "left":
			image = left.get(spriteNum);
			directInv = true;
			break;
		case "right":
			image = right.get(spriteNum);
			directInv = false;
			break;
		}
		
		if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
			worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
			worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
			worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
	public void check() {
		
		int px = nextWorldX/gp.tileSize;
		int py = nextWorldY/gp.tileSize;
		
		if (0 < px && px < gp.maxWorldCol && 0 < py && py < gp.maxWorldRow) {
			if (!onPath) {
				if (gp.tileM.ObjectMap[py][px] == 0 && gp.tileM.map[py][px] != 2) {
					worldY = nextWorldY;
					worldX = nextWorldX;
				}
			} else {
				worldY = nextWorldY;
				worldX = nextWorldX;
			}
		}
	}
	
	public void levelUp() {
		
	}
	
	public BufferedImage setup(String imagePath) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public void searchPath(int goalX, int goalY) {
		
		int startX = (worldX + hitbox.x)/gp.tileSize;
		int startY = (worldY + hitbox.y)/gp.tileSize;
		
		if (startX == goalX && startY == goalY) {
			onPath = false;
			if (gp.player.invincible <= 0) {
				gp.player.health -= (power - gp.player.defence);
				gp.player.invincible = 10;
			}
			//System.out.println("intersect");
			return;
		}
		
		gp.astar.setNode(startX, startY, goalX, goalY);
		gp.astar.findPath();
		
		if (gp.astar.goalReached == true) {
			
			int nextWorldX = gp.astar.pathList.get(0).x * gp.tileSize;
			int nextWorldY = gp.astar.pathList.get(0).y * gp.tileSize;
			
			d1 = (nextWorldX <= worldX) ? "left":"right";
			d2 = (nextWorldY > worldY) ? "down":"up";
			
			//System.out.println(d1+ " " + d2);
			
		} else {
			onPath = false;
		}
		
		//System.out.println(gp.astar.goalReached);
	}
	
	public void setPosition() {
		
		int cpx = gp.player.worldX/gp.tileSize; // Current Player World X
		int cpy = gp.player.worldY/gp.tileSize; // Current Player World Y
		
		int k, nx, ny;
		double theta, px, py;
		
		r = ThreadLocalRandom.current().nextInt(5, 11);
		direction = "down";
		
		while (true) {
			 k = ThreadLocalRandom.current().nextInt(1, 360);

		     theta = Math.toRadians(k);
		     px = cpx + r * Math.sin(theta);
		     py = cpy + r * Math.cos(theta);

		     nx = (int) Math.round(px);
		     ny = (int) Math.round(py);
		     
		     
		     worldX = nx*gp.tileSize;
    		 worldY = ny*gp.tileSize;
    		 
    		 nextWorldX = worldX;
    		 nextWorldY = worldY;
    		 
    		 
//    		 System.out.println(name + " " + worldX + " " + worldY);

		     if (0 <= nx && nx < gp.maxWorldCol && 0 <= ny && ny < gp.maxWorldRow) {
		    	 if (gp.tileM.map[ny][nx] != 2 && gp.tileM.ObjectMap[ny][nx] == 0) {
		    		 break;
		    	 }
		     }
		}
	}
	
	public boolean dist(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2) return false;
		
		double dist = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		return dist < gp.tileSize*8;
	}
}