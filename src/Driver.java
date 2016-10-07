import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.Player;
import musictag.MusicTagAlpha;
import musictag.TagDictionary;

public class Driver {
	
	static MusicTagAlpha tagger;
	
	public static void main(String args[]) {
		System.out.println("Starting program");
		
		final String fileLocation = "E:/Google Drive/Collections/Music Collection/Z_Test";
		tagger = new MusicTagAlpha(fileLocation);
		
		System.out.println("Program Setup done");
		while (true) {
			readInput();
		}
	}
	
	public static void readInput() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        System.out.print("> ");
	        String s = br.readLine();
	        runCommand(s.split(" "));
        } catch(Exception e){
            
        }
	}
	
	public static void runCommand(String... args) {
		final String command = args[0].toLowerCase();
		
		final String title;
		final TagDictionary dictionary;
		final String[] tags;
		final List<TagDictionary> tagDictionaries;
		
		switch(command) {
			case "x":
				System.exit(0);
				break;
				
			case "help":
				showHelp();
				break;
				
			case "new_dict":
				title = args[1];
				tags = Arrays.copyOfRange(args, 2, args.length);
				TagDictionary tagDictionary = new TagDictionary(title, tags);
				tagger.addDictionary(tagDictionary);
				break;
				
			case "read_dict":
				title = args[1];
				dictionary = tagger.getTagDictionaryByTitle(title);
				System.out.println(dictionary.toString());
				break;
				
			case "rm_dict":
				title = args[1];
				dictionary = tagger.getTagDictionaryByTitle(title);
				tagger.removeDictionary(dictionary);
				break;
				
			case "ls_dict":
				tagDictionaries = tagger.getTagDictionaries();
				for (TagDictionary dict : tagDictionaries) {
					System.out.println(dict.getTitle());
				}
				break;
				
			case "play_rand":
				tagger.selectRandomSong();
				break;
				
			case "tag_sl":
				tags = Arrays.copyOfRange(args, 1, args.length);
				tagger.addTagsToSelectedSong(tags);
				break;
				
			default:
				showHint();
		}
	}
	
	public static void showHint() {
		int randomNumber = ThreadLocalRandom.current().nextInt(2, helpTips.length);
		System.out.println(helpTips[0]);
		System.out.println(helpTips[randomNumber]);
		System.out.println(helpTips[1]);
		
	}
	public static final String[] helpTips = {
			"help - displays a full list of commands",
			"x - exits the program",
			"new_dict title tags... - makes a new dictionary"
	};
	public static void showHelp() {
		for (String tip : helpTips) {
			System.out.println(tip);
		}
	}

}
