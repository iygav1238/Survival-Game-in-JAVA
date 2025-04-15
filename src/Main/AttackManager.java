package Main;

import java.awt.Graphics2D;

import attack.manulAttack;

public class AttackManager {

	GamePanel gp;
	
	public AttackManager(GamePanel gp) {
		this.gp = gp;
	}
	
	void update() {
		for (int i = 0; i < gp.atk.size(); i++) {
			if (gp.atk.get(i) != null) {
				if (gp.atk.get(i).coolTime <= 0) {
					gp.validAttack[i] = true;
				} else {
					gp.atk.get(i).coolTime--;
				}
			}
		}
	}
	
	void draw(Graphics2D g2) {
		for (int i = 0; i < gp.atk.size(); i++) {
			if (gp.atk.get(i) != null) {
				if (gp.validAttack[i] == true) {
					gp.atk.get(i).spriteNum = gp.atk.get(i).runTime/5;
					gp.atk.get(i).draw(g2);
					gp.atk.get(i).runTime++;
					if (gp.atk.get(i).runTime == 5*gp.atk.get(i).left.size() && i != 1) {
						gp.atk.get(i).runTime = 0;
						gp.atk.get(i).coolTime = gp.FPS;
						
						if (gp.atk.get(i).name == "special") {
							gp.atk.get(i).setPosition();
						}
						else gp.validAttack[i] = false;
					}
				}
			}
		}
	}
}
