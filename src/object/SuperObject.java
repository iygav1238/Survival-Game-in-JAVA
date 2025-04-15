package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.HitBox;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public int objSize = 48;
	public HitBox hitbox = new HitBox(0, 0, objSize, objSize);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	public void draw(Graphics2D g2, GamePanel gp, boolean Obstacle) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
			worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
			worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
			worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
			if (Obstacle) g2.drawImage(image, screenX, screenY, 2*gp.tileSize, 2*gp.tileSize, null);
			else g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
	public BufferedImage setup(String imagePath, GamePanel gp) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
