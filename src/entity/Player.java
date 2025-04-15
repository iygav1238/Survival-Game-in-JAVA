package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.HitBox;
import Main.KeyHandler;
import attack.SpecialAttack;
import attack.fireAttack;
import attack.manulAttack;

public class Player extends Entity {

	KeyHandler KeyH;
	
	public int Exp = 0;
	public int level = 1;
	public int levelExp = 100;
	public int kill = 0;
	public int attackSpriteCount = 0;
	
	private static int ptr = 0;
	public boolean recovery = false;
	
	public Player(GamePanel gp, KeyHandler KeyH) {
		
		super(gp);
		
		this.KeyH = KeyH;
		hitbox = new HitBox(0, 0, 96, 96);
		hitboxDefaultX = 0;
		hitboxDefaultY = 0;
		
		resetPlayer();
		getPlayerImage();
	}
	
	public void resetPlayer() {
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		invincible = -1;
		maxHealth = 20;
		directInv = false;
		worldX = nextWorldX = gp.tileSize*51;
		worldY = nextWorldY = gp.tileSize*51;
		speed = 2;
		health = maxHealth;
		direction = "down";
		power = 1;
		defence = 0;
	}
		
	public void getPlayerImage() {
		
		String p = "/player/sonic1.png";
		try {
			titleImage = ImageIO.read(getClass().getResourceAsStream(p));
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		String[] path = new String[16];
		for (int i = 1; i <= path.length; i++) {
		    path[i - 1] = "/player/sonic" + i + ".png";
		}
		
		try {
			for (int i = 0; i<8; i++) {
				
				right.add(ImageIO.read(getClass().getResourceAsStream(path[i])));
				left.add(ImageIO.read(getClass().getResourceAsStream(path[i+8])));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void levelUp() {
		level++;
		if (level == 5) {
			
			gp.atk.add(new manulAttack(gp));
			level++;
			
		} else if (level == 11) {
			
			gp.atk.add(new fireAttack(gp));
			level++;
			
		} else if (level == 15) {
			
			gp.atk.add(new SpecialAttack(gp));
		}
		
		if (level%3 == 0) {
			
			power++;
			health++;
			
			health = Math.min(health, maxHealth);
			maxHealth++;
		}
		
		if (level%8 == 0) health = maxHealth;
		
	}
	
	public void update() {
		
		if (KeyH.upPressed == true || KeyH.downPressed == true ||
				KeyH.leftPressed == true || KeyH.rightPressed == true) {
			
			if (KeyH.upPressed == true) {
				direction = "up";
				nextWorldY = worldY - speed;
			} else if (KeyH.downPressed == true) {
				direction = "down";
				nextWorldY = worldY + speed;
			} 
			
			gp.inter.checkPos(this);
			//check(true);
			
			if (KeyH.leftPressed == true) {
				direction = "left";
				nextWorldX = worldX - speed;
			} else if (KeyH.rightPressed == true) {
				direction = "right";
				nextWorldX = worldX + speed;
			}
			
			gp.inter.checkPos(this);
			
			//check(false);
			
			spriteCounter++;
			if (spriteCounter > 5) {
				spriteNum++;
				spriteNum %= 8;
				spriteCounter = 0;
			}
		} 		
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		if (!gp.isInvinvible) invincible--;
		
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
		
		if (invincible > 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}
}
