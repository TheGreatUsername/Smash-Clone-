package Engine;

public class PaintControl implements Runnable {

	Start st;
	
	public PaintControl(Start st){
		this.st = st;
	}
	
	@Override
	public void run() {
		
		while(true)
		st.repaint();
		
	}

}
