package Engine;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Worlds.FallingWorld;
import Worlds.FileWorld;
import Worlds.RGenWorld;
import Worlds.SectWorld;
import Worlds.TestWorld;
import Worlds.World;


public class Main {

	int tim;
	boolean initialize = true;
	Start st;
	World w;
	Camera c;
	
	public Main(Start st){
		this.st = st;
	}
	
	public void init(Start st){
		this.st = st;
		c = new Camera(st);
		
		/*String s = "";
		try {
			BufferedReader Buffer = new BufferedReader(new FileReader("default.world"));
			while(Buffer.ready())
				s += Buffer.readLine() + "\n";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		w = new FileWorld(st, DataBase.teststage);
		
		//Start.sounds.add(new MakeSound(true, "fighting game beta"));
		MakeSound.playSound("fighting game beta", 20, true);
	}
	
	public void update(){
		//tim++;
		if(tim > 500 || st.isR()){
			tim = 0;
			w = new SectWorld(st);
			//w = new FallingWorld(st, 0, 3, 0, 1);
		}
		if(initialize){
			init(st);
			initialize = false;
		}
		w.update();
		c.update();
	}
	
	public void paint(Graphics g){
		if(w != null)
		w.paint(g);
	}

	public Start getSt() {
		return st;
	}

	public void setSt(Start st) {
		this.st = st;
	}

	public World getW() {
		return w;
	}

	public void setW(World w) {
		this.w = w;
	}

	public Camera getC() {
		return c;
	}

	public void setC(Camera c) {
		this.c = c;
	}
	
}
