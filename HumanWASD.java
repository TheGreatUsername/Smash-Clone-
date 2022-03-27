package Engine;

import Mobs.Mob.dir;
import Mobs.Player;

public class HumanWASD extends Master {

	public int ld, rd;
	public boolean prevup, prevspace, prevleft, prevright;
	
	public HumanWASD(Start st, Player p){
		super(st, p);
	}
	
	@Override
	public void update(){
		super.update();
		
		ld--;
		rd--;
		
		if (st.isShift() && !prevspace && st.isDownWasd()) {
			m.attack(dir.DOWN);
		}
		
		if (st.isShift() && !prevspace && st.isUpWasd()) {
			m.attack(dir.UP);
		}
		
		if (st.isLeftWasd() && !st.isRightWasd()) {
			if (st.isShift() && !prevspace) m.attack(dir.LEFT);
			m.moveleft();
			if (!prevleft && m.getRec() <= 0 && ld > 0) m.dash(dir.LEFT);
			ld = 10;
		}else if (st.isRightWasd() && !st.isLeftWasd()) {
			if (st.isShift() && !prevspace) m.attack(dir.RIGHT);
			m.moveright();
			if (!prevright && m.getRec() <= 0 && rd > 0) m.dash(dir.RIGHT);
			rd = 10;
		}else if (!st.isRightWasd() && !st.isLeftWasd() && m.getNoa() <= 0) {
			if (st.isShift() && !prevspace) m.attack(null);
			m.setAx(m.getVx() > 0 ? -m.getUk() : m.getUk());
		} 
		
		if(st.isUpWasd() && !prevup)
			m.jump();
		
		
			
		prevup = st.isUpWasd();
		prevspace = st.isShift();
		prevleft = st.isLeftWasd();
		prevright = st.isRightWasd();
	}
}
