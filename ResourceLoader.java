package Engine;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;


public class ResourceLoader {

	static ResourceLoader rl = new ResourceLoader();
	
	public static BufferedImage getImage(String filename) {
		return Camera.toBufferedImage(Toolkit.getDefaultToolkit().getImage(
				rl.getClass().getResource("Images/" + filename)));
	}
}
