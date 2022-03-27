package Engine;

import Mobs.Mob.dir;
import Mobs.Player;

public class Human extends Master {

	public int ld, rd, dd;
	public boolean prevup, prevspace, prevleft, prevright, prevdown;
	
	public Human(Start st, Player p){
		super(st, p);
	}
	
	@Override
	public void update(){
		super.update();
		
		ld--;
		rd--;
		dd--;
		
		if (st.isSpace() && !prevspace && st.isDown()) {
			m.attack(dir.DOWN);
		}
		
		if (st.isSpace() && !prevspace && st.isUp()) {
			m.attack(dir.UP);
		}
		
		if (st.isDown() && !prevdown){
			if (dd > 0)
				m.fastfall();
			dd = 10;
		}
		
		if (st.isLeft() && !st.isRight()) {
			if (st.isSpace() && !prevspace) m.attack(dir.LEFT);
			m.moveleft();
			if (!prevleft && m.getRec() <= 0 && ld > 0) m.dash(dir.LEFT);
			ld = 10;
		}else if (st.isRight() && !st.isLeft()) {
			if (st.isSpace() && !prevspace) m.attack(dir.RIGHT);
			m.moveright();
			if (!prevright && m.getRec() <= 0 && rd > 0) m.dash(dir.RIGHT);
			rd = 10;
		}else if (!st.isRight() && !st.isLeft() && m.getNoa() <= 0) {
			if (st.isSpace() && !prevspace) m.attack(null);
			m.setAx(m.getVx() > 0 ? -m.getUk() : m.getUk());
		} 
		
		if(st.isUp() && !prevup)
			m.jump();
		
		
			
		prevdown = st.isDown();
		prevup = st.isUp();
		prevspace = st.isSpace();
		prevleft = st.isLeft();
		prevright = st.isRight();
	}
}
