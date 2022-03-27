package Engine;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import Mobs.Mob;


public class Camera {
	
	public int zoom = 10000, x, y, width = 1, height = zoom;
	public double sgrat;
	
	static AffineTransform affinetransform = new AffineTransform();
	static FontRenderContext frc = new FontRenderContext(affinetransform, true,
			true);
	
	Start st;

	public Camera(Start st){
		this.st = st;
	}
	
	public void update() {
		sgrat = ((double)zoom / (double)st.getHeight());
		height = zoom;
		if(st.getHeight() != 0)
		width = st.getWidth() * zoom / st.getHeight();
	}
	
	public void drawImage(Graphics g, int px, int py,
			int width, int height, int ix, int iy, int xres, int yres, int xpixsize,
			int ypixsize, boolean toggleDirection, Image img) {
		boolean r = !toggleDirection;
		int a = 0;
		if (!r)
			a = 1;
		px -= xpixsize * xres / 2;
		py += yres * ypixsize - height / 2;
		int wx = xpixsize * xres;
		int wy = ypixsize * yres;
		if(img != null){
		g.drawImage(img, (int) (st.getWidth() / 2 + (px - x) * st.getHeight()
				/ zoom), (int) (st.getHeight() / 2 + (-py + y) * st.getHeight()
				/ zoom),
				(int) (st.getWidth() / 2 + (px - x + wx) * st.getHeight()
						/ zoom + 1), (int) (st.getHeight() / 2 + (-py + y + wy)
						* st.getHeight() / zoom + 1), (ix + a) * xres, iy * yres,
				(ix + 1 - a) * xres, (iy + 1) * yres, st);
		}else{
			g.setColor(new Color(255, 255, 255));
			g.fillRect((int) (st.getWidth() / 2 + (px - x) * st.getHeight()
					/ zoom), (int) (st.getHeight() / 2 + (-py + y) * st.getHeight()
							/ zoom), wx * st.getHeight() / zoom, wy * st.getHeight() / zoom);
		}
	}
	
	public boolean isonscreen(Mob m) {
		if(st.getHeight() != 0)
		if (Math.abs(m.getX() - x) - m.getWidth() / 2 <= st.getWidth() / 2 * zoom
				/ st.getHeight()
				&& Math.abs(m.getY() - y) - m.getHeight() / 2 <= zoom / 2)
			return true;
		return false;
	}
	
	public static boolean isTransparent(int x, int y, BufferedImage img) {
		int pixel = img.getRGB(x, y);
		if ((pixel >> 24) == 0x00) {
			return true;
		}
		return false;
	}
	
	
	public static BufferedImage toBufferedImage(Image img) {
		ImageIcon imgi = new ImageIcon(img);

		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(imgi.getIconWidth(),
				imgi.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
	
	public static int strheight(String s, Font f){
		return (int) f.getStringBounds(s, frc).getHeight();
	}
	
	public static int strwidth(String s, Font f){
		return (int) f.getStringBounds(s, frc).getWidth();
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Start getSt() {
		return st;
	}

	public void setSt(Start st) {
		this.st = st;
	}
	
}
