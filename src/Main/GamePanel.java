package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import ai.AStar;
import attack.Attack;
import entity.Entity;
import entity.Player;
import monster.Banzai;
import monster.Eval;
import monster.Fairy;
import monster.FireMob;
import monster.Knight;
import monster.MonsterPool;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	// SCREEN SETTINGS	
	public final int tileSize = 48; // 48x48 Tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 512 pixels
	
	//WORLD SETTING
	public final int maxWorldCol = 103;
	public final int maxWorldRow = 103;
	public final int worldWidth = maxWorldCol * tileSize;
	public final int worldHeight = maxWorldRow * tileSize;
	
	//FPS
	public int FPS = 60;
	public double drawInterval = 1e9/FPS;
	public double delta = 0;
	public long currentTime;
	
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public AStar astar = new AStar(this);
	KeyHandler KeyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound soundEffect = new Sound();
	Thread gameThread;
	
	boolean gameSoundOn = true;
	
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public Interaction inter = new Interaction(this);
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, KeyH);	
	public SuperObject obj[] = new SuperObject[30];
	
	// ENTITY - MONSTER
	public int ptr = 0;
	public int lastPtr;
	public int limit = 10;
	public int monsterDelta = 0;
	public boolean re = true;
	public Entity monsters[] = new Entity[20];
	public MonsterPool mp = new MonsterPool(this);;
	
	
	// ATTACK
	public ArrayList<Attack> atk = new ArrayList<>();
	public AttackManager attackM = new AttackManager(this);
	public boolean validAttack[] = new boolean[5]; 
	
	// GAME STATE
	public long playTime = 0;
	public long lastPlayTime = 0;
	public long preTime = 0;
	public boolean isInvinvible = false;
	public int unlockMage;
	
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int settingState = 3;
	public final int roadingState = 4;
	public final int endingState = 5;
	
	public String classpathFile = "/log/unlock.txt";
    public File file = new File(GamePanel.class.getResource(classpathFile).getPath());
	
	public GamePanel() {
		
		unlockMage = loadFile();
//		System.out.println("unlockMage: " + unlockMage);
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));	
		this.setBackground(new Color(0, 0, 0));
		this.setDoubleBuffered(true); // 화면 그릴때 깜빡임 적고 더 부드럽게 함
		this.addKeyListener(KeyH);
		this.setFocusable(true); // 키보드 이벤트를 허용
	}

	public void newClear() {
		player.resetPlayer();
		monsterDelta = 0;
		player.level = 1;
		re = true;
		limit = 10;
		
		for (int i = 0; i<5; i++) validAttack[i] = true;
		for (int i = 0; i<monsters.length; i++) 
			if (monsters[i] != null) {
				mp.putEntity(monsters[i]);
				monsters[i] = null;
			}
		
		atk.clear();
		
		gameState = ui.titleScreenState = ui.commandNum = 0;
		playTime = lastPlayTime = preTime = 0;
		setupGame();
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setAttack();
		aSetter.setObstacle();
		gameState = titleState;
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this); // 스레드 초기화
		gameThread.start(); // run 함수 자동 실행
		
	}
		
	public void run() {
		
		long lastTime = System.nanoTime();
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if (delta >= 1) {
				
				if (gameState == roadingState) {
					repaint();
					update();
				} else {
					update();
					repaint();
				}
				delta--;
				monsterDelta++;
			}
			
			if (monsterDelta == 3*FPS) {
				re = true;
				monsterDelta = 0;
			}
		}
	}
	
	public void update() {
				
		if (gameState == playState) {
			
			long currentTime = Instant.now().getEpochSecond();
			playTime = (currentTime-lastPlayTime) + preTime;
			
			player.update();
			
			attackM.update();
			
			monsterQuery();
			
			//System.out.println("curr: " + monsters.size());
		}
		if (gameState == pauseState) {
			// nothing
		}
		if (gameState == roadingState) {
			
			mp.init(30);
			ptr = lastPtr = 0;
			aSetter.setMonster(ptr);
			gameState = playState;
			player.invincible = 120;
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// TITLE SCREEN
		if (gameState == titleState || gameState == roadingState || gameState == endingState) {
			ui.draw(g2);
		}
		else {
			// BACKGROUND
			tileM.draw(g2);
			
			// OBJECT
			for (int i = 0; i<obj.length; i++) {
				if (obj[i] != null) {
					//System.out.println(obj[i].name + ": " + obj[i].worldX + " " + obj[i].worldY);
					
					if (i%2 == 1) obj[i].draw(g2,  this, true);
					else obj[i].draw(g2, this, false);	
				}
			}
			
			// MONSTER
			for (int i = 0; i < Math.min(limit, monsters.length); i++) {
				if (monsters[i] != null) monsters[i].draw(g2);
			}
			
			attackM.draw(g2);
			
			// PLAYER
			player.draw(g2);
			
			// UI
			ui.draw(g2);
			
			if (playTime >= 300) {
				gameState = endingState;
			}
		}
		
		g2.dispose();
	}
	
	public void monsterQuery() {
		lastPtr = ptr;
		ptr = Math.min(4, (int)playTime/60);
		
		if (ptr > lastPtr) {
			aSetter.setMonster(ptr);
			limit += 5;
		}
		
		
		for (int i = 0; i < Math.min(limit, monsters.length); i++) {
		    if (monsters[i] != null) {
		    	
		    	//System.out.println(monsters[i].worldX + " " + monsters[i].worldY);
		    	
		        try {
		            inter.attackProcess(monsters[i]);
		            
		            if (dist(monsters[i], player) > 40) {
		            	monsters[i].health = 0;
		            }
		            //monsters[i].health = 0;
		            if (monsters[i].health <= 0) {
		            	mp.putEntity(monsters[i]);
		                monsters[i] = null;
		            } else {
		                monsters[i].update();
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    } 
		    else if (re == true) {
		        try {
		            //System.out.println("Creating monster at index: " + i + ", ptr: " + ptr + ", limit: " + limit);
		            if (ptr == 0) {
		                monsters[i] = mp.getFairy();
		            } else if (ptr == 1) {
		                monsters[i] = mp.getEval();
		            } else if (ptr == 2) {
		                monsters[i] = mp.getFireMob();
		            } else if (ptr == 3) {
		                monsters[i] = mp.getKnight();
		            } else if (ptr == 4) {
		                monsters[i] = mp.getBanzai();
		            }
		            monsters[i].setPosition();
		            
		        } catch (Exception e) {
		            e.printStackTrace();  // 예외 출력
		        }
		    }
		}

		re = false;
	}
	
	public double dist(Entity a, Entity b) {
		int X = Math.abs(a.worldX - b.worldX)/tileSize;
		int Y = Math.abs(a.worldY - b.worldY)/tileSize;
		
		return Math.max(X, Y);
	}
	
	// background sound
	public void playMusic(int i) {
		
		if (gameSoundOn) {
			music.setFile(i);
			music.play();
			music.loop();
		}
		
	}
	
	public int loadFile() {
		
		int res = 0;
		try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                res = Integer.parseInt(line.trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return res;
	}
	
	public void setFile(String newData) {
		try {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(newData);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void stopMusic() {
		
		if (gameSoundOn) music.stop();
	}
	
	// effect sound
	public void playSE(int i) {
		
		soundEffect.setFile(i);
		soundEffect.play();
	}
}
