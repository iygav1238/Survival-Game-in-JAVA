package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Bow extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Bow(GamePanel gp) {
		this.gp = gp;
		
		name = "Bow";
		image = setup("/objects/bow", gp);
//		try {
//			image = ImageIO.read(getClass().getResourceAsStream("/objects/bow.png"));
//			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
				
		collision = true;
	}
}
