package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Sheld extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Sheld(GamePanel gp) {
		this.gp = gp;
		
		name = "Sheld";
		image = setup("/objects/sheld", gp);
//		try {
//			image = ImageIO.read(getClass().getResourceAsStream("/objects/.sheldpng"));
//			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
		
		collision = true;
	}
}
