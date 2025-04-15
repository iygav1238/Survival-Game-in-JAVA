package Main;

import java.util.Random;

import attack.swordAttack;
import monster.Banzai;
import monster.Eval;
import monster.Fairy;
import monster.FireMob;
import monster.Knight;
import object.OBJ_Book;
import object.OBJ_Boots;
import object.OBJ_Bow;
import object.OBJ_Sheld;
import object.OBJ_Sword;
import object.OBJ_Tree;

public class AssetSetter {
	GamePanel gp;
	public Random rand = new Random();
	public int px[] = {3, 21, 28, 41, 53, 53, 75, 80, 45, 32, 90};
	public int py[] = {5, 36, 17, 48, 32, 48, 25, 71, 64, 77, 90};
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Sword(gp);
		randomMake(0);
		
		gp.obj[2] = new OBJ_Sword(gp);
		randomMake(2);
		
		gp.obj[4] = new OBJ_Sheld(gp);
		randomMake(4);
		
		gp.obj[6] = new OBJ_Boots(gp);
		randomMake(6);
		
		gp.obj[8] = new OBJ_Boots(gp);
		randomMake(8);
	}
	
	public void setObstacle() {		
		for (int i = 1; i <= 21; i += 2) {
			gp.obj[i] = new OBJ_Tree(gp);
			gp.obj[i].worldX = px[i/2] * gp.tileSize;
			gp.obj[i].worldY = py[i/2] * gp.tileSize;
		}
	}
	
	public void randomMake(int idx) {
		while (true) {
			int x = rand.nextInt(2, 102);
			int y = rand.nextInt(2, 102);
			
			if (gp.tileM.ObjectMap[y][x] == 0) {
				gp.obj[idx].worldX = x*gp.tileSize;
				gp.obj[idx].worldY = y*gp.tileSize;
				return;
			}
		}
	}
	
	public void setAttack(){
		gp.atk.add(new swordAttack(gp));
		gp.atk.get(0).spriteNum = 0;
		gp.atk.get(0).coolTime = gp.atk.get(0).runTime = 0;
	}
	
	public void setMonster(int ptr) {	
		
		for (int i = 0; i < Math.min(gp.limit, gp.monsters.length); i++) {
//			if (gp.monsters[i] != null && ptr == 0) {
//				gp.mp.putEntity(gp.monsters[i]);
//				gp.monsters[i] = null;
//			}
			if (gp.monsters[i] != null) continue;
			
			if (ptr == 0) {
                gp.monsters[i] = gp.mp.getFairy();
            } else if (ptr == 1) {
                gp.monsters[i] = gp.mp.getEval();
            } else if (ptr == 2) {
                gp.monsters[i] = gp.mp.getFireMob();
            } else if (ptr == 3) {
                gp.monsters[i] = gp.mp.getKnight();
            } else if (ptr == 4) {
                gp.monsters[i] = gp.mp.getBanzai();
            }
			
			if (gp.monsters[i] == null) gp.monsters[i] = gp.mp.getFairy();
			gp.monsters[i].setPosition();
		}
		
	}
	
}
