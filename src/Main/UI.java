package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class UI {

	GamePanel gp;
	Font arial_40;
	Graphics2D g2;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int PlayerHealth;
	BufferedImage titleImage = null;
	
	double playtime;
	DecimalFormat dFormat = new DecimalFormat("0");
	
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: the first screen, 1: the second screen
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 25);
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if (gp.gameState == gp.playState) {
			drawPosition();
			drawTime();
			drawPlayerLife();
			drawPlayerExp();
			
		}
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if (gp.gameState == gp.roadingState) {
			drawRoadingScreen();
		}
		if (gp.gameState == gp.endingState) {
			drawEndingScreen();
		}
	}
	
	public void drawEndingScreen() {
		
		if (gp.unlockMage == 0) {
			
			gp.setFile("1");
		}
		gp.unlockMage = 1;
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		String text = "Game Clear !";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
		y += gp.tileSize;
		text = "> return title";
		x = getXforCenteredText(text);
		g2.drawString(text, x, y);
	}
	
	public void drawRoadingScreen() {
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		BufferedImage roadImage = null;
		try {
			roadImage = ImageIO.read(getClass().getResourceAsStream("/screen/backgroundCity.png"));
			g2.drawImage(roadImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		String text = "Roading";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
	}
	
	public void drawPosition() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
		String text = "current x y: " + gp.player.worldX + " " + gp.player.worldY;
		g2.drawString(text, 16, 16);
	}
	
	public void drawTime() {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		String text = String.valueOf(gp.playTime);
		
		int x = getXforCenteredText(text);
		int y = gp.tileSize;
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
	}
	
	public void drawPlayerExp() {
		int size = gp.tileSize;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(gp.player.screenX, gp.player.screenY+size+size/8, size, size/8);
		
		int exp = gp.player.Exp;
		g2.setColor(new Color(135, 206, 235));
		g2.fillRect(gp.player.screenX, gp.player.screenY+size+size/8, exp*size/100, size/8);		
		
	}
	
	public void drawPlayerLife() {
		
		int size = gp.tileSize;
		g2.setColor(Color.BLACK);
		g2.fillRect(gp.player.screenX, gp.player.screenY+size, size, size/8);
		
		int life = gp.player.health;
		g2.setColor(Color.red);
		g2.fillRect(gp.player.screenX, gp.player.screenY+size, life*size/gp.player.maxHealth, size/8);
			
	}
	
	public void drawTitleScreen() {
		
		if (titleScreenState == 0) {
			g2.setColor(new Color(70, 120, 80));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			if (titleImage == null) {
				try {
				titleImage = ImageIO.read(getClass().getResourceAsStream("/screen/backgroundForest.jpg"));
				
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			g2.drawImage(titleImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
			String text = "Sonic Adventure";
			
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			// SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x+5, y+5);
			
			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			// CHARACTER IMAGE
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			g2.drawImage(gp.player.titleImage, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
			
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			
			text = "SETTING";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
		} else if (titleScreenState == 1) {
			
			// CLASS SELECTION SCREEN
			
			//g2.drawImage(titleImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(35F));
			
			String text = "Select your class!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Warrior";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			if (gp.unlockMage == 1) {
				text = "Mage";
				x = getXforCenteredText(text);
				y += gp.tileSize;
				g2.drawString(text, x, y);
				if (commandNum == 1) {
					g2.drawString(">", x-gp.tileSize, y);
				}
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1 + gp.unlockMage) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		} else if (titleScreenState == 2) {			
			// CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(35F));
			
			String text = "Game Over";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Time: " + String.valueOf(gp.playTime);
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "return";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		} else if (titleScreenState == 3) {
			
			// SETTING STATE
			//g2.drawImage(titleImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(32F));
			
			String text = "SETTING";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "INVINCIBLE MOD";
			
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			if (gp.isInvinvible == true) text += " v";
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Music Off";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			
			if (gp.gameSoundOn == false) text += " v";
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Mage On/Off";
			
			x = getXforCenteredText(text);
			y += gp.tileSize;
			if (gp.unlockMage == 1) text += " v";
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
		} 
	}
	
	public void drawPauseScreen() {
		
		g2.setColor(Color.white);
		g2.fillRect(gp.tileSize*4-24, gp.tileSize*2-24, gp.tileSize*9, gp.tileSize*9);
		
		g2.setColor(Color.black);
		g2.fillRect(gp.tileSize*4, gp.tileSize*2, gp.tileSize*8, gp.tileSize*8);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2 - gp.tileSize;
		g2.drawString(text, x, y);
		
		text = "Current Level: " + gp.player.level;
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		
		text = "> GO TITLE";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
