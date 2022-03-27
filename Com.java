package Engine;

import java.util.Random;

import Mobs.Floor;
import Mobs.Mob;
import Mobs.Mob.dir;
import Mobs.Player;
import Worlds.World;

public class Com extends Master {

	int difficulty = 3, wait = 0;
	World w;
	Random r = new Random();

	public Com(Start st, Player m) {
		super(st, m);
		w = st.getMa().getW();
	}

	public void update() {

		if (difficulty == 3){
		
			super.update();
			if (m.getGround() == null) {
				Floor f = (Floor) w.nearestx(m, Floor.class);
				if (Math.abs(f.getX() - m.getX()) > m.getWidth())
					if (f.getY() - m.getHeight() / 2 > m.getY() + f.getHeight() / 2){
						if (r.nextBoolean())
							m.attack(dir.UP);
						else if (f.getX() > m.getX())
							m.dash(dir.LEFT);
						else if (f.getX() < m.getX())
							m.dash(dir.RIGHT);
					}else if (f.getX() - f.getWidth() / 2 > m.getX())
						m.dash(dir.RIGHT);
					else if (f.getX() + f.getWidth() / 2 < m.getX())
						m.dash(dir.LEFT);
			} else
				m.setAx(m.getVx() > 0 ? -m.getUk() : m.getUk());
	
			Mob np = w.nearest(m, Player.class);
			
			if (np.getY() > m.getY() && r.nextInt(15) == 0)
				m.jump();
			
			//if (m.getGround() == null && m.chance(160) && m.getY() - np.getY() > 0 && m.getY() - np.getY() > Math.abs(m.getX() - np.getX()))
			//	m.downair();
			
			if(r.nextInt(2) == 0){
				int d = 1500;
				if (np.getX() + d < m.getX())
					m.moveleft();
				if (np.getX() - d > m.getX())
					m.moveright();
			}
			
			if (r.nextInt(30) == 0) {
				if (m.chance(3))
					m.attack(null);
				else if (m.chance(2)){
					if (m.isRight())
						m.attack(dir.RIGHT);
					else
						m.attack(dir.LEFT);
				}else
					m.attack(dir.DOWN);
			}
			
		}else if (difficulty == 2){
			
			super.update();
			if (m.getGround() == null) {
				Floor f = (Floor) w.nearestx(m, Floor.class);
				if (Math.abs(f.getX() - m.getX()) > m.getWidth())
					if (f.getY() > m.getY() && m.chance(5)){
						if (r.nextBoolean())
							m.attack(dir.UP);
						else if (f.getX() > m.getX())
							m.dash(dir.LEFT);
						else if (f.getX() < m.getX())
							m.dash(dir.RIGHT);
					}
			} else
				m.setAx(m.getVx() > 0 ? -m.getUk() : m.getUk());
	
			Mob np = w.nearest(m, Player.class);
			
			if (np.getY() > m.getY() && r.nextInt(15) == 0)
				m.jump();
			
			//if (m.getGround() == null && m.chance(160) && m.getY() - np.getY() > 0 && m.getY() - np.getY() > Math.abs(m.getX() - np.getX()))
			//	m.downair();
			
			wait--;
			
			if(r.nextInt(2) == 0 && wait <= 0){
				int d = 1500;
				if (np.getX() + d < m.getX())
					m.moveleft();
				if (np.getX() - d > m.getX())
					m.moveright();
				if (m.chance(60))
					wait = 40;
			}
			
			if (m.chance(60)) {
				if (m.chance(3))
					m.attack(null);
				else if (m.chance(2)){
					if (m.isRight())
						m.attack(dir.RIGHT);
					else
						m.attack(dir.LEFT);
				}else
					m.attack(dir.DOWN);
			}
			
		}
		
	}

}
