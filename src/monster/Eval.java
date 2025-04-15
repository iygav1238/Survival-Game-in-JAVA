package monster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class Eval extends Entity {

	//private static boolean directInv = false;
	
	public Eval(GamePanel gp) {
		super(gp);
		
		name = "eval";
		speed = 1;
		maxHealth = 8;
		health = maxHealth;
		power = 2;
		direction = "down";
		gaveExp = 40;
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		onPath = true;
		
		getImage();
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;	
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
		image = left.get(spriteNum);
		
		if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
			worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
			worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
			worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
	public void getImage() {
		
		String[] path = new String[10];
		for (int i = 1; i <= path.length; i++) {
		    path[i - 1] = "/monster/eval" + i + ".png";
		}
		
		try {
			for (int i = 0; i<10; i++) {
				
				left.add(ImageIO.read(getClass().getResourceAsStream(path[i])));

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
