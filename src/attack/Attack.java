package attack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.HitBox;

public class Attack {

	GamePanel gp;
	public int coolTime;
	public int screenX, screenY;
	public int runTime;
	public int spriteNum;
	public HitBox hitbox;
	public String name;
	public Vector<BufferedImage> left = new Vector<>();
	public Vector<BufferedImage> right = new Vector<>();
	
	public Attack(GamePanel gp) {
		this.gp = gp;
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
	
	public void draw(Graphics2D g2) {
		
	}
	
	public void setPosition() {
		
	}
}
