package Engine;

import Mobs.Mob;
import Mobs.Player;

public class Master {

	public Start st;
	public Player m;
	
	public Master(Start st, Player m) {
		this.st = st;
		this.m = m;
	}

	public void update() {
		m.setRec(m.getRec() - 1);
	}
	
	
}
