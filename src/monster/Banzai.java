package monster;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class Banzai extends Entity {
	
	private static boolean directInv = false;
	
	public Banzai (GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		
		name = "banzai";
		speed = 3;
		maxHealth = 20;
		health = maxHealth;
		power = 10;
		direction = "down";
		gaveExp = 140;
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		onPath = true;
		
		getImage();
		
	}
	
	
	
	public void getImage() {
		
		String[] path = new String[90];
		for (int i = 1; i <= path.length; i++) {
		    path[i - 1] = "/monster/banzai" + i + ".png";
		}
		
		try {
			for (int i = 0; i<45; i++) {
				
				left.add(ImageIO.read(getClass().getResourceAsStream(path[i])));
				right.add(ImageIO.read(getClass().getResourceAsStream(path[i+45])));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		try {
			invincible--;
			setAction();
			
			if (d2 == "up") {
				nextWorldY = worldY - speed;
			}
			else if (d2 == "down") {
				nextWorldY = worldY + speed;
			}	
			
			check();
			
			if (d1 == "left") {
				nextWorldX = worldX - speed;
			}
			else if (d1 == "right") {
				nextWorldX = worldX + speed;
			}
			
			check();
	
			spriteCounter++;
			if (spriteCounter > 5) {
				spriteNum++;
				spriteNum %= left.size();
				spriteCounter = 0;
			}
		} catch (Exception e) {
			//System.out.println("Update Entity Error");
		}
		//System.out.println("current x, y: " + worldX + " " + worldY);
	}
	

}
