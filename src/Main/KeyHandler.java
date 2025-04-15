package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if (gp.gameState == gp.titleState) {
			
			if (gp.ui.titleScreenState == 0) { // TITLE
				if(code == KeyEvent.VK_W) {
					if (gp.ui.commandNum > 0) gp.ui.commandNum--;
				}
				if(code == KeyEvent.VK_S) {
					if (gp.ui.commandNum < 2) gp.ui.commandNum++;
				}
				if(code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						gp.ui.titleScreenState = 1;
						gp.playTime = gp.lastPlayTime = Instant.now().getEpochSecond();
						gp.playMusic(0);
						
					}
					if (gp.ui.commandNum == 1) {
						gp.ui.commandNum = 0;
						gp.ui.titleScreenState = gp.settingState;
					}
					if (gp.ui.commandNum == 2) {
						System.exit(0);
					}
				}
			}
			else if (gp.ui.titleScreenState == 1) { // IN GAME
				if(code == KeyEvent.VK_W) {
					if (gp.ui.commandNum > 0) gp.ui.commandNum--;
				}
				if(code == KeyEvent.VK_S) {
					if (gp.ui.commandNum < 1 + gp.unlockMage) gp.ui.commandNum++;
				}
				if(code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						gp.gameState = gp.roadingState;
						// gp.playMusic(0);
					}
					else if (gp.ui.commandNum == 1 + gp.unlockMage) {
						gp.ui.titleScreenState = 0;	
						gp.gameState = gp.titleState;
						gp.ui.commandNum = 0;
						gp.stopMusic();
					}
					else if (gp.ui.commandNum == 1) {
						gp.gameState = gp.roadingState;
						// gp.playMusic(0);
					}
					
					
				}
			} else if (gp.ui.titleScreenState == 2) { // GAME OVER
				if(code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						gp.ui.titleScreenState = 0;	
						gp.stopMusic();
						gp.newClear();
					}
				}
			} else if (gp.ui.titleScreenState == 3) { // SETTING
				
				if (code == KeyEvent.VK_ESCAPE) {
					gp.gameState = gp.settingState;
				}
				if(code == KeyEvent.VK_W) {
					if (gp.ui.commandNum > 0) gp.ui.commandNum--;
				}
				if(code == KeyEvent.VK_S) {
					if (gp.ui.commandNum < 3) gp.ui.commandNum++;
				}
				if(code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) { // INVINCIBLE
						gp.isInvinvible = !gp.isInvinvible;
					}
					
					if (gp.ui.commandNum == 1) { // MUSIC ON/OFF
						gp.gameSoundOn = !gp.gameSoundOn;
					}
					
					if (gp.ui.commandNum == 2) { // MAGE UNLOCK
						if (gp.unlockMage == 0) {
							gp.setFile("1");
						} else {
							gp.setFile("0");
						}
						gp.unlockMage = gp.loadFile();
						System.out.println("unlockMage: " + gp.unlockMage);
					}
					
					if (gp.ui.commandNum == 3) {
						gp.ui.titleScreenState = 0;	
						gp.ui.commandNum = 0;
						gp.gameState = gp.titleState;
					}
				}
				
			}
			
		}
		
		if (gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W) {
				upPressed = true;
			}
			if(code == KeyEvent.VK_A) {
				leftPressed = true;
			}
			if(code == KeyEvent.VK_S) {
				downPressed = true;
			}
			if(code == KeyEvent.VK_D) {
				rightPressed = true;
			}
			if (code == KeyEvent.VK_ESCAPE) {
				if(gp.gameState == gp.playState) {
					gp.gameState = gp.pauseState;
					//gp.stopMusic();
				} else if (gp.gameState == gp.pauseState) {
					gp.gameState = gp.playState;
					//gp.playMusic(0);
				}
			}
		}
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
				gp.lastPlayTime = Instant.now().getEpochSecond();
				gp.preTime = gp.playTime;
			}
			
			if (code == KeyEvent.VK_ENTER) {
				gp.ui.titleScreenState = 0;	
				gp.stopMusic();
				gp.newClear();
				gp.ui.commandNum = 0;
			}
		}
		else if (gp.gameState == gp.endingState) {
			if (code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState = 0;	
				gp.stopMusic();
				gp.newClear();
				gp.ui.commandNum = 0;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}

}
