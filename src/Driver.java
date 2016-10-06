import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.Player;
import musictag.MusicTagAlpha;
import musictag.TagDictionary;

public class Driver {
	
	public static void main(String args[]) {
		System.out.println("Starting program");
		
		final String fileLocation = "E:/Google Drive/Collections/Music Collection/Z_Test";
		MusicTagAlpha tagger = new MusicTagAlpha(fileLocation);
		tagger.printFolderData();
		
		final String songFileLocation = tagger.getTaggedSongs().get(0).getFileLocation();
		//playSong(songFileLocation);
		
		System.out.println("Program done");
	}
	
	public static void playSong(File file) {
		try{
		    AudioInputStream audioInputStream =
		        AudioSystem.getAudioInputStream(file);
		    Player player = new Player(audioInputStream);
		    player.play();
		}
		catch(Exception ex) {
			System.err.println(ex);
		}
	}
	
	public static void playSong(String fileString) {
		File file = new File(fileString);
		playSong(file);
	}

}
