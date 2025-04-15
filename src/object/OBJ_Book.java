package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Book extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Book(GamePanel gp) {
		this.gp = gp;
		
		name = "Book";
		image = setup("/objects/book", gp);
//		try {
//			image = ImageIO.read(getClass().getResourceAsStream("/objects/book.png"));
//			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
						
		collision = true;
	}
}
