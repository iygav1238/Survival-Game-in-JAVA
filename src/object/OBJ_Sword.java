package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Sword extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Sword(GamePanel gp) {
		this.gp = gp;
		name = "Sword";
		image = setup("/objects/sword", gp);
//		try {
//			image = ImageIO.read(getClass().getResourceAsStream("/objects/sword.png"));
//			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
		
		collision = true;
	}
}
