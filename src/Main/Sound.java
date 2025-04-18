package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/mapSound.wav");
		soundURL[1] = getClass().getResource("/sound/powerup.wav");
		soundURL[2] = getClass().getResource("/sound/gameover.wav");
	}
	
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e) {
			
		}
		
	}
	
	public void play() {
		
		clip.start();
	}
	
	public void loop() {
		
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		
		clip.stop();
	}
}
