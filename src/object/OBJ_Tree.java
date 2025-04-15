package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Tree extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Tree(GamePanel gp) {
		this.gp = gp;
		
		name = "Tree";
		image = setup("/objects/tree", gp);
//		try {
//			image = ImageIO.read(getClass().getResourceAsStream("/objects/tree.png"));
//			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
		
		hitbox.x = 24;
		hitbox.y = 32;
		hitbox.height = 64;
				
		collision = true;
	}
}
