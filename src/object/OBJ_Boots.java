package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Boots extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Boots(GamePanel gp) {
		this.gp = gp;
		
		name = "Boots";
		image = setup("/objects/boots", gp);
		
//		try {
//			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
//			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
				
		collision = true;
	}
}
