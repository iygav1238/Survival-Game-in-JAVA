package tile;

import java.util.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int map[][];
	public int ObjectMap[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		tile = new Tile[10];
		map = new int[gp.maxWorldRow][gp.maxWorldCol];
		ObjectMap = new int[gp.maxWorldRow][gp.maxWorldCol];
		
		getTileImage();
		loadMap("/maps/map.txt");
		loadObject("/maps/ObjectMap.txt");
	}
	
	public void getTileImage() {
			
		setup(0, "grass", false);
		setup(1, "soil", false);
		setup(2, "wall", true); // 이 타일은 통과 못하고 막혀야함
	}
	
	public void setup(int idx, String imagePath, boolean collision) {
		
		try {
			tile[idx] = new Tile();
			tile[idx].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
			tile[idx].collision = collision;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			for (int i = 0; i < gp.maxWorldRow; i++) {
			    String line = br.readLine();
			    String[] numbers = line.split(" ");
			    
			    for (int j = 0; j < gp.maxWorldCol; j++) {
			        int num = Integer.parseInt(numbers[j]);
			        map[i][j] = num;
			    }
			}
			br.close();
			
		} catch(Exception e) {
			//System.out.println("background error");
		}
		
	}
	
	public void loadObject(String filePath) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			for (int i = 0; i < gp.maxWorldRow; i++) {
			    String line = br.readLine();
			    String[] numbers = line.split(" ");
			    
			    for (int j = 0; j < gp.maxWorldCol; j++) {
			        int num = Integer.parseInt(numbers[j]);
			        ObjectMap[i][j] = num;
			    }
			}
			br.close();
			
		} catch(Exception e) {
			System.out.println("Object error");
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
			for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
				int tileNum = map[worldCol][worldRow];
				
				int worldX = worldCol*gp.tileSize;
				int worldY = worldRow*gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
					worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
					worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
					worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
					g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				}
			}
		}
		
	}
	
	public static int parseInt(String str) {
        int num = 0;
        boolean isNegative = false;
        int i = 0;

        if (str.charAt(0) == '-') {
            isNegative = true;
        }

        for (; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                num = num * 10 + (c - '0');
            } else {
                throw new NumberFormatException("error");
            }
        }

        return isNegative ? -num : num;
    }
}
