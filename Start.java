package Engine;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Start extends JPanel implements Runnable, KeyListener {

	public static void main(String[] args) {
		new Start();
	}

	JFrame f;

	public Start() {
		
		f = new JFrame();
		f.setTitle("Yay Game");
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(this, BorderLayout.CENTER);
		f.setLocationRelativeTo(null);
		this.requestFocus();
		final Start s = this;
		
		f.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        	
		    	s.closing = true;
		    	s.paint(s.getGraphics());
		    	
		    	for(MakeSound s : Start.sounds)
	            	s.stop = true;
		    	/*
		    	try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				*/
		        System.exit(0);
		    }
		});

		try {
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void start() throws IOException {
		addKeyListener(this);
		
		st = this;
		
		ma = new Main(this);
		pc = new PaintControl(this);
		
		update = new Thread(this);
		update.start();
		
		paint = new Thread(pc);
		paint.start();
		
		/*for(int i = 0; i < 2; i++){
			Thread t = new Thread(pc);
			t.start();
		}*/
	}
	
	int tps = 60;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double sWidth = screenSize.getWidth();
	double sHeight = screenSize.getHeight();
	long sCount1, sCount, tc, fpsc, cfpsc, tfrs, tcs = -1, avfps;
	boolean printfps = true, pause;
	boolean upWasd, downWasd, leftWasd, rightWasd, upKey, downKey, leftKey, rightKey, up, down, left, right, R, esc, prevesc, space, closing, ctrl, shift;
	private Graphics doubleG;
	public static ArrayList<MakeSound> sounds = new ArrayList<MakeSound>();
	public static Start st;
	BufferedImage bi;
	Image i;
	Main ma;
	PaintControl pc;
	Thread paint, update;

	@Override
	public void run() {
		while(true){
			sCount1 = System.nanoTime();
			long k;
			if (sCount1 - sCount >= 1000000000 / tps) {
				k = sCount1 - sCount;
				if (printfps) {
					tc += k;
					if (tc >= 1000000000) {
						tcs++;
						tfrs+= fpsc;
						if(tcs != 0)
						avfps = tfrs / tcs;
						System.out.println(fpsc + " " + avfps);
						cfpsc = fpsc;
						tc = fpsc = (long) 0;
					}
				}
				if(upWasd || upKey){
					up = true;
				}else{
					up = false;
				}
				if(downWasd || downKey){
					down = true;
				}else{
					down = false;
				}
				if(leftWasd || leftKey){
					left = true;
				}else{
					left = false;
				}
				if(rightWasd || rightKey){
					right = true;
				}else{
					right = false;
				}
				
				if(esc && !prevesc)
					pause = !pause;
				
				if(!pause)
				ma.update();
				sCount = System.nanoTime();
				
				prevesc = esc;
			}
			//repaint();
		}
	}
	
	public static void play(String name){
		MakeSound ms = new MakeSound();
		sounds.add(ms);
		ms.play(name);
	}
	
	/*
	public static void play(String name) {
	    try {
	        Clip clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File("./" + name + ".wav").getAbsoluteFile()));
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	*/
	
	/*@Override
	public void update(Graphics g) {
		if (i == null) {
			i = createImage((int) (sWidth), (int) (sHeight));
			doubleG = i.getGraphics();
		}

		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);

		doubleG.setColor(getForeground());
		paint(doubleG);

		g.drawImage(i, 0, 0, this);

	}*/
	
	/*
	public void update(Graphics g) {
		if (bi != null)
			g.drawImage(bi, 0, 0, null);
		super.update(g);
	}
	*/
	
	@Override
	public void paint(Graphics g) {
		//super.paint(g);

		fpsc++;

		g.setColor(new Color(99, 66, 99));
		//g.fillRect(0, 0, getWidth(), getHeight());
		if(ma != null)
		ma.paint(g);
		
		if(closing){
			g.setColor(new Color(0, 0, 0, 0.7f));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			String s = "Saving and Quitting";
			Font f = new Font("Impact", Font.BOLD, 70);
			g.setFont(f);
			g.setColor(Color.CYAN);
			g.drawString(s, (int)(sWidth / 2 - Camera.strwidth(s, f) / 2), (int)(sHeight / 2 - Camera.strheight(s, f) / 2));
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_UP:
			upKey = true;
			break;
		case KeyEvent.VK_DOWN:
			downKey = true;
			break;
		case KeyEvent.VK_LEFT:
			leftKey = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightKey = true;
			break;
		case KeyEvent.VK_W:
			upWasd = true;
			break;
		case KeyEvent.VK_S:
			downWasd = true;
			break;
		case KeyEvent.VK_A:
			leftWasd = true;
			break;
		case KeyEvent.VK_D:
			rightWasd = true;
			break;
		case KeyEvent.VK_R:
			R = true;
			break;
		case KeyEvent.VK_ESCAPE:
			esc = true;
			break;
		case KeyEvent.VK_SPACE:
			space = true;
			break;
		case KeyEvent.VK_CONTROL:
			ctrl = true;
			break;
		case KeyEvent.VK_SHIFT:
			shift = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_UP:
			upKey = false;
			break;
		case KeyEvent.VK_DOWN:
			downKey = false;
			break;
		case KeyEvent.VK_LEFT:
			leftKey = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightKey = false;
			break;
		case KeyEvent.VK_W:
			upWasd = false;
			break;
		case KeyEvent.VK_S:
			downWasd = false;
			break;
		case KeyEvent.VK_A:
			leftWasd = false;
			break;
		case KeyEvent.VK_D:
			rightWasd = false;
			break;
		case KeyEvent.VK_R:
			R = false;
			break;
		case KeyEvent.VK_ESCAPE:
			esc = false;
			break;
		case KeyEvent.VK_SPACE:
			space = false;
			break;
		case KeyEvent.VK_CONTROL:
			ctrl = false;
			break;
		case KeyEvent.VK_SHIFT:
			shift = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public JFrame getF() {
		return f;
	}

	public void setF(JFrame f) {
		this.f = f;
	}

	public int getTps() {
		return tps;
	}

	public void setTps(int tps) {
		this.tps = tps;
	}

	public long getsCount1() {
		return sCount1;
	}

	public void setsCount1(long sCount1) {
		this.sCount1 = sCount1;
	}

	public long getsCount() {
		return sCount;
	}

	public void setsCount(long sCount) {
		this.sCount = sCount;
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public long getFpsc() {
		return fpsc;
	}

	public void setFpsc(long fpsc) {
		this.fpsc = fpsc;
	}

	public long getCfpsc() {
		return cfpsc;
	}

	public void setCfpsc(long cfpsc) {
		this.cfpsc = cfpsc;
	}

	public boolean isPrintfps() {
		return printfps;
	}

	public void setPrintfps(boolean printfps) {
		this.printfps = printfps;
	}

	public boolean isUpWasd() {
		return upWasd;
	}

	public void setUpWasd(boolean upWasd) {
		this.upWasd = upWasd;
	}

	public boolean isDownWasd() {
		return downWasd;
	}

	public void setDownWasd(boolean downWasd) {
		this.downWasd = downWasd;
	}

	public boolean isLeftWasd() {
		return leftWasd;
	}

	public void setLeftWasd(boolean leftWasd) {
		this.leftWasd = leftWasd;
	}

	public boolean isRightWasd() {
		return rightWasd;
	}

	public void setRightWasd(boolean rightWasd) {
		this.rightWasd = rightWasd;
	}

	public boolean isUpKey() {
		return upKey;
	}

	public void setUpKey(boolean upKey) {
		this.upKey = upKey;
	}

	public boolean isDownKey() {
		return downKey;
	}

	public void setDownKey(boolean downKey) {
		this.downKey = downKey;
	}

	public boolean isLeftKey() {
		return leftKey;
	}

	public void setLeftKey(boolean leftKey) {
		this.leftKey = leftKey;
	}

	public boolean isRightKey() {
		return rightKey;
	}

	public void setRightKey(boolean rightKey) {
		this.rightKey = rightKey;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public Main getMa() {
		return ma;
	}

	public void setMa(Main ma) {
		this.ma = ma;
	}

	public long getTfrs() {
		return tfrs;
	}

	public void setTfrs(long tfrs) {
		this.tfrs = tfrs;
	}

	public long getTcs() {
		return tcs;
	}

	public void setTcs(long tcs) {
		this.tcs = tcs;
	}

	public long getAvfps() {
		return avfps;
	}

	public void setAvfps(long avfps) {
		this.avfps = avfps;
	}

	public boolean isR() {
		return R;
	}

	public void setR(boolean r) {
		R = r;
	}

	public Dimension getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}

	public double getsWidth() {
		return sWidth;
	}

	public void setsWidth(double sWidth) {
		this.sWidth = sWidth;
	}

	public double getsHeight() {
		return sHeight;
	}

	public void setsHeight(double sHeight) {
		this.sHeight = sHeight;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isEsc() {
		return esc;
	}

	public void setEsc(boolean esc) {
		this.esc = esc;
	}

	public boolean isPrevesc() {
		return prevesc;
	}

	public void setPrevesc(boolean prevesc) {
		this.prevesc = prevesc;
	}

	public boolean isSpace() {
		return space;
	}

	public void setSpace(boolean space) {
		this.space = space;
	}

	public Graphics getDoubleG() {
		return doubleG;
	}

	public void setDoubleG(Graphics doubleG) {
		this.doubleG = doubleG;
	}

	public Image getI() {
		return i;
	}

	public void setI(Image i) {
		this.i = i;
	}

	public PaintControl getPc() {
		return pc;
	}

	public void setPc(PaintControl pc) {
		this.pc = pc;
	}

	public boolean isClosing() {
		return closing;
	}

	public void setClosing(boolean closing) {
		this.closing = closing;
	}

	public boolean isCtrl() {
		return ctrl;
	}

	public void setCtrl(boolean ctrl) {
		this.ctrl = ctrl;
	}

	public static ArrayList<MakeSound> getSounds() {
		return sounds;
	}

	public static void setSounds(ArrayList<MakeSound> sounds) {
		Start.sounds = sounds;
	}

	public static Start getSt() {
		return st;
	}

	public static void setSt(Start st) {
		Start.st = st;
	}

	public Thread getPaint() {
		return paint;
	}

	public void setPaint(Thread paint) {
		this.paint = paint;
	}

	public Thread getUpdate() {
		return update;
	}

	public void setUpdate(Thread update) {
		this.update = update;
	}

	public boolean isShift() {
		return shift;
	}

	public void setShift(boolean shift) {
		this.shift = shift;
	}

	public BufferedImage getBi() {
		return bi;
	}

	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}
	
}
