package Engine;

import Mobs.Mob.dir;
import Mobs.Player;

public class HumanKeys extends Master {

	public int ld, rd;
	public boolean prevup, prevspace, prevleft, prevright;
	
	public HumanKeys(Start st, Player p){
		super(st, p);
	}
	
	@Override
	public void update(){
		super.update();
		
		ld--;
		rd--;
		
		if (st.isSpace() && !prevspace && st.isDownKey()) {
			m.attack(dir.DOWN);
		}
		
		if (st.isSpace() && !prevspace && st.isUpKey()) {
			m.attack(dir.UP);
		}
		
		if (st.isLeftKey() && !st.isRightKey()) {
			if (st.isSpace() && !prevspace) m.attack(dir.LEFT);
			m.moveleft();
			if (!prevleft && m.getRec() <= 0 && ld > 0) m.dash(dir.LEFT);
			ld = 10;
		}else if (st.isRightKey() && !st.isLeftKey()) {
			if (st.isSpace() && !prevspace) m.attack(dir.RIGHT);
			m.moveright();
			if (!prevright && m.getRec() <= 0 && rd > 0) m.dash(dir.RIGHT);
			rd = 10;
		}else if (!st.isRightKey() && !st.isLeftKey() && m.getNoa() <= 0) {
			if (st.isSpace() && !prevspace) m.attack(null);
			m.setAx(m.getVx() > 0 ? -m.getUk() : m.getUk());
		} 
		
		if(st.isUpKey() && !prevup)
			m.jump();
		
		
			
		prevup = st.isUpKey();
		prevspace = st.isSpace();
		prevleft = st.isLeftKey();
		prevright = st.isRightKey();
	}
}
