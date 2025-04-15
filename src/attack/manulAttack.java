package attack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.GamePanel;
import Main.HitBox;

public class manulAttack extends Attack {
	public manulAttack(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		name = "manul";
		coolTime = 0;
		runTime = 0;
		
		hitbox = new HitBox(0, 0, 2*gp.tileSize, 2*gp.tileSize);
		getImage();
		spriteNum = 0;
	}
	
	public void getImage() {
		
		left.add(setup("/attack/manul"));
		
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = left.get(0);	
		screenX = gp.player.screenX - gp.tileSize/2;
		screenY = gp.player.screenY - gp.tileSize/2;
		
		g2.drawImage(image, screenX, screenY, 2*gp.tileSize, 2*gp.tileSize, null);
	}
}
