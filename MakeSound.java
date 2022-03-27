package Engine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineEvent.Type;

public class MakeSound extends Thread {

	boolean repeat, stop, playing;
	private String name;
	private final int BUFFER_SIZE = 128000;
	private File soundFile;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceLine;

	/**
	 * @param filename
	 *            the name of the file that is going to be played
	 */

	public MakeSound() {
		// empty on purpose
	}

	public MakeSound(boolean repeat) {
		this.repeat = repeat;
	}

	public MakeSound(boolean repeat, String name) {
		this.repeat = repeat;
		play(name);
	}

	public void play(String name) {
		this.name = "./" + name + ".wav";
		start();
	}

	@Override
	public void run() {
		playSound(name);
	}

	public void playSound(String filename) {

		playing = true;

		do {

			String strFilename = filename;

			try {
				soundFile = new File(strFilename);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			try {
				audioStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			audioFormat = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			try {
				sourceLine = (SourceDataLine) AudioSystem.getLine(info);
				sourceLine.open(audioFormat);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			sourceLine.start();

			int nBytesRead = 0;
			byte[] abData = new byte[BUFFER_SIZE];
			while (nBytesRead != -1 && !stop) {
				try {
					nBytesRead = audioStream.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (nBytesRead >= 0) {
					@SuppressWarnings("unused")
					int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
				}
			}

			sourceLine.drain();
			sourceLine.close();

		} while (repeat && !stop);

		playing = false;
	}

	public static synchronized void playSound(final String url, final float v,
			final boolean l) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(new File(url + ".wav"));
					clip.open(inputStream);
					FloatControl gainControl = (FloatControl) clip
							.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(v);
					if (!l)
						clip.start();
					else
						clip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
}