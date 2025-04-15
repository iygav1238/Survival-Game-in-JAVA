package attack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.GamePanel;
import Main.HitBox;

public class swordAttack extends Attack{

	public swordAttack(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		name = "sword";
		coolTime = 0;
		runTime = 0;
		
		hitbox = new HitBox(0, 0, gp.tileSize, gp.tileSize);
		getImage();
		spriteNum = 0;
	}
	
	public void getImage() {
		
		right.add(setup("/attack/sword_attack1"));
		right.add(setup("/attack/sword_attack2"));
		right.add(setup("/attack/sword_attack3"));
		left.add(setup("/attack/sword_attack4"));
		left.add(setup("/attack/sword_attack5"));
		left.add(setup("/attack/sword_attack6"));
		
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
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
	
}
