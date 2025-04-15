package monster;

import java.util.Queue;

import Main.GamePanel;
import entity.Entity;

import java.util.LinkedList;

public class MonsterPool {
	
	GamePanel gp;
	
    private Queue<Entity> fairyPool = new LinkedList<>();
    private Queue<Entity> evalPool = new LinkedList<>();
    private Queue<Entity> fireMobPool = new LinkedList<>();
    private Queue<Entity> knightPool = new LinkedList<>();
    private Queue<Entity> banzaiPool = new LinkedList<>();
    
    private boolean initFlag = true;

    public MonsterPool(GamePanel gp) {
        this.gp = gp;
    }
    
    public void init(int initialSize) {
    	if (initFlag == true) {
    		newClear();
	    	for (int i = 0; i < initialSize; i++) {
	            fairyPool.add(new Fairy(gp));
	            evalPool.add(new Eval(gp));
	            fireMobPool.add(new FireMob(gp));
	            knightPool.add(new Knight(gp));
	            banzaiPool.add(new Banzai(gp));
    	}
    	initFlag = false;
        }
    }

    public Entity getFairy() {
        return fairyPool.poll();
    }

    public Entity getEval() {
        return evalPool.poll();
    }

    public Entity getFireMob() {
        return fireMobPool.poll();
    }

    public Entity getKnight() {
        return knightPool.poll();
    }

    public Entity getBanzai() {
        return banzaiPool.poll();
    }
    
    public void putEntity(Entity entity) {
    	
    	entity.health = entity.maxHealth;
    	entity.setPosition();
    	
    	if (entity.name == "fairy") fairyPool.add(entity);
    	else if (entity.name == "eval") evalPool.add(entity);
    	else if (entity.name == "223") fireMobPool.add(entity);
    	else if (entity.name == "knight") knightPool.add(entity);
    	else if (entity.name == "banzai") banzaiPool.add(entity);
    }
    
    public void newClear() {
    	fairyPool.clear();
    	evalPool.clear();
    	fireMobPool.clear();
    	knightPool.clear();
    	banzaiPool.clear();
    }
}
