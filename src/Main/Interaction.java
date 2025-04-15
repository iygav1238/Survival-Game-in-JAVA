package Main;

import entity.Entity;

public class Interaction {

	GamePanel gp;
	public Interaction(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkPos(Entity entity) {
		entity.nextWorldX = Math.min(gp.worldWidth-gp.tileSize, Math.max(gp.tileSize, entity.nextWorldX));
		entity.nextWorldY = Math.min(gp.worldHeight-gp.tileSize, Math.max(gp.tileSize, entity.nextWorldY));
		
		HitBox next = new HitBox(entity.nextWorldX, entity.nextWorldY, gp.tileSize, gp.tileSize);
		HitBox object = new HitBox(0, 0, gp.tileSize, gp.tileSize);
		
		int idx = -1;
		for (int i = 0; i<gp.obj.length; i++) {
			if (gp.obj[i] != null) {
				object.setX(gp.obj[i].worldX + gp.obj[i].hitbox.x);
				object.setY(gp.obj[i].worldY + gp.obj[i].hitbox.y);
				
				if (next.intersect(object) == true) {
					System.out.println("intersect");
					idx = i;
					break;
				}
			}	
		}
		
		pickUpObject(idx);
		
		int Midx = -1;
		for (int i = 0; i < gp.monsters.length; i++) {
			if (gp.monsters[i] != null) {
				object.setX(gp.monsters[i].worldX + gp.monsters[i].hitbox.x);
				object.setY(gp.monsters[i].worldY + gp.monsters[i].hitbox.y);
					
				if (next.intersect(object) == true) {
					//System.out.println("intersect");
					Midx = i;
					break;
				}
			}	
		}
		monster(Midx);
		
		if (gp.tileSize <= entity.nextWorldX && entity.nextWorldX <= gp.worldWidth-2*gp.tileSize 
				&& gp.tileSize <= entity.nextWorldY && entity.nextWorldY <= gp.worldHeight - 2*gp.tileSize) {
			if (idx == -1 || idx%2 == 0) {
				entity.worldX = entity.nextWorldX;
				entity.worldY = entity.nextWorldY;
			} else {
				entity.nextWorldX = entity.worldX;
				entity.nextWorldY = entity.worldY;
			}
		}
		
	}
	
	public void attackProcess(Entity entity) {
		HitBox monster = new HitBox(entity.worldX + entity.hitbox.x, entity.worldY + entity.hitbox.y, 48, 48);
		HitBox attack = new HitBox(0, 0, 48, 48);
	
		int diff, diffX, diffY;
		
		//System.out.println(monster.x + " " + monster.y);
		for (int i = 0; i < gp.atk.size(); i++) {
			if (gp.atk.get(i) != null) {
				if (gp.validAttack[i] == true) {
					
					attack.setWidth(gp.atk.get(i).hitbox.width);
					attack.setHeight(gp.atk.get(i).hitbox.height);
					
					if (gp.atk.get(i).name == "special") {
						diffX = gp.player.screenX - gp.atk.get(i).screenX;
						diffY = gp.player.screenY - gp.atk.get(i).screenY;
						
						attack.setX(gp.player.worldX - diffX + gp.atk.get(i).hitbox.x);
						attack.setY(gp.player.worldY - diffY + gp.atk.get(i).hitbox.y);
						attack.setR(gp.atk.get(i).hitbox.r);
						
						if (attack.intersectC(monster) == true) {
							
							//System.out.println("attack");
							if (entity.invincible <= 0) {
								entity.health -= 3*gp.player.power;
								entity.invincible = 10;
							}
							if (entity.health <= 0) {
								gp.player.kill++;
								gp.player.Exp += entity.gaveExp/gp.player.level;
								if (gp.player.Exp >= gp.player.levelExp) {
									gp.player.Exp %= gp.player.levelExp;
									gp.player.levelUp();
								}
								return;
							}				
						}
						continue;
						
					}
					else if (gp.atk.get(i).name == "manul") {
						
						attack.setX(gp.player.worldX - gp.tileSize/2);
						attack.setY(gp.player.worldY - gp.tileSize/2);
						
						//System.out.println(attack.x + " " + attack.y + " " + attack.width + " " + attack.height);
					}
					else {
						diff = gp.player.screenX - gp.atk.get(i).screenX;
						//System.out.println(diff);
						attack.setX(gp.player.worldX - diff);
						attack.setY(gp.player.worldY);
					}
					//System.out.println(i + " " + attack.x + " " + attack.y);
					if (attack.intersect(monster) == true) {
						
						//System.out.println("attack");
						if (entity.invincible <= 0) {
							entity.health -= gp.player.power;
							entity.invincible = 10;
						}
						if (entity.health <= 0) {
							gp.player.kill++;
							gp.player.Exp += entity.gaveExp/gp.player.level;
							if (gp.player.Exp >= gp.player.levelExp) {
								gp.player.Exp %= gp.player.levelExp;
								gp.player.levelUp();
							}
							return;
						}				
					}
				}
			}
		}
	}
	
	public void monster(int idx) {
		if (idx == -1) return;
		
		if (gp.player.invincible <= 0) {
			gp.player.health -= (gp.monsters[idx].power - gp.player.defence);
			gp.player.invincible = 10;
		}
		if (gp.player.health <= 0) {
			gp.gameState = 0;
			gp.ui.titleScreenState = 2;
			gp.stopMusic();
			gp.playSE(2);
		}		
	}
	
	public void pickUpObject(int idx) {
		if (idx != -1) {
			String objectName = gp.obj[idx].name;
			switch(objectName) {
			case "Sword":
				gp.player.power += 2;
				break;
			case "Book":
				gp.player.magic += 2;
				break;
			case "Sheld":
				gp.player.defence++;
				break;
			case "Bow":
				gp.player.power++;
				break;
			case "Tree":
				break;
			case "Boots":
				gp.player.speed++;
				break;
			}
				
			if(idx%2 == 0) {
				gp.playSE(1);
				gp.obj[idx] = null;
			}
		}
	}
}
