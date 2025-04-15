package monster;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.HitBox;
import entity.Entity;

public class Fairy extends Entity{

	private static boolean directInv = false;
	
	public Fairy(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		
		name = "fairy";
		speed = 1;
		maxHealth = 1;
		health = maxHealth;
		power = 1;
		direction = "down";
		gaveExp = 20;
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		onPath = true;
		
		getImage();
		
	}
	
	
	
	public void getImage() {
		
		String[] path = new String[16];
		for (int i = 1; i <= path.length; i++) {
		    path[i - 1] = "/monster/fairy" + i + ".png";
		}
		
		try {
			for (int i = 0; i<8; i++) {
				
				left.add(ImageIO.read(getClass().getResourceAsStream(path[i])));
				right.add(ImageIO.read(getClass().getResourceAsStream(path[i+8])));
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
				spriteNum %= 8;
				spriteCounter = 0;
			}
		} catch (Exception e) {
			//System.out.println("Update Entity Error");
		}
		//System.out.println("current x, y: " + worldX + " " + worldY);
	}
	
}
