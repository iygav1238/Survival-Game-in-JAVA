package attack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.GamePanel;
import Main.HitBox;

public class fireAttack extends Attack{

	public fireAttack(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		name = "fire";
		coolTime = 0;
		runTime = 0;
		
		hitbox = new HitBox(0, 0, 3*gp.tileSize, gp.tileSize);
		getImage();
		spriteNum = 0;
	}
	
	public void getImage() {
		
		left.add(setup("/attack/fire1"));
		left.add(setup("/attack/fire2"));
		left.add(setup("/attack/fire3"));
		left.add(setup("/attack/fire4"));
		left.add(setup("/attack/fire5"));
		left.add(setup("/attack/fire6"));
		right.add(setup("/attack/fire7"));
		right.add(setup("/attack/fire8"));
		right.add(setup("/attack/fire9"));
		right.add(setup("/attack/fire10"));
		right.add(setup("/attack/fire11"));
		right.add(setup("/attack/fire12"));
		
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;	
		screenX = gp.player.screenX;
		screenY = gp.player.screenY;
		String direction = gp.player.direction;
		boolean directInv = gp.player.directInv;
		spriteNum %= left.size();
		
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
		
		if (directInv == false) {
			screenX += gp.tileSize;
		} else {
			screenX -= gp.tileSize;
		}
		if (directInv == true) {
			screenX -= 2*(gp.tileSize);
		}
		
		g2.drawImage(image, screenX, screenY, 3*gp.tileSize, gp.tileSize, null);
	}
	
}
