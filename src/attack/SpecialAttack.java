package attack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import Main.GamePanel;
import Main.HitBox;

public class SpecialAttack extends Attack{
	
	public SpecialAttack(GamePanel gp) {
		super(gp);
		
		name = "special";
		
		coolTime = runTime = spriteNum = 0;
		hitbox = new HitBox(3*gp.tileSize/2, 3*gp.tileSize/2, 3*gp.tileSize/2);
		getImage();
		setPosition();
	}
	
	public void getImage() {
		
		left.add(setup("/attack/Special1"));
		left.add(setup("/attack/Special2"));
		left.add(setup("/attack/Special3"));
	}
	
	public void draw(Graphics2D g2) {
		spriteNum %= left.size();
		BufferedImage image = left.get(spriteNum);	
		g2.drawImage(image, screenX, screenY, 3*gp.tileSize, 3*gp.tileSize, null);
	}
	
	public void setPosition() {
		
		int cpx = gp.player.worldX/gp.tileSize; // Current Player World X
		int cpy = gp.player.worldY/gp.tileSize; // Current Player World Y
		
		int k, nx, ny;
		double theta, px, py;
		
		int r = ThreadLocalRandom.current().nextInt(3, 8);
		String direction = "down";
		
		k = ThreadLocalRandom.current().nextInt(1, 360);

		theta = Math.toRadians(k);
		px = cpx + r * Math.sin(theta);
		py = cpy + r * Math.cos(theta);

		nx = (int) Math.round(px);
		ny = (int) Math.round(py);
		     
		int diffX = cpx - nx;
		int diffY = cpy - ny;
		
		screenX = gp.player.screenX - diffX*gp.tileSize;
		screenY = gp.player.screenY - diffY*gp.tileSize;
    		 
	}

}
