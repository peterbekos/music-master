package musictag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import musictag.utils.FileUtils;

public class MusicTagAlpha implements MusicTagApi {
	
	private static final String HEAD_DIR = "/_musicmaster";
	private static final String SONG_DIR = HEAD_DIR + "/" + "songs";
	private static final String DICT_DIR = HEAD_DIR + "/" + "dictionaries";
	
	private String fileLocation;
	private List<File> songFiles;
	private List<TaggedSong> taggedSongs;
	private List<TagDictionary> tagDictionaries;
	
	private long timer = 0;
	
	public MusicTagAlpha(String fileLocation) {
		this.fileLocation = fileLocation;
		load(fileLocation);	
	}

	@Override
	public void load(String fileLocation) {
		checkSetUp();
		
		startTimer();
		System.out.println("Fetching Songs from: " + fileLocation);
		taggedSongs = new ArrayList<TaggedSong>();
		songFiles = FileUtils.getSongFilesFromPath(fileLocation);
		System.out.println("Song Fetch Complete - " + stopTimer() + "ms");
		
		startTimer();
		System.out.println("Hashing Song files");
		hashSongFiles();
		System.out.println("Song Hashing complete - " + stopTimer() + "ms");
		
		startTimer();
		System.out.println("Loading Tag Dictionaries");
		File dictionaryDirectory = new File( fileLocation + DICT_DIR );
        File[] dictionaryFiles = dictionaryDirectory.listFiles();
        tagDictionaries = new ArrayList<>();
        for (File file : dictionaryFiles) {
        	TagDictionary tagDictionary = FileUtils.loadObjectFromFile(file, TagDictionary.class);
        	tagDictionaries.add(tagDictionary);
        }
        System.out.println("Tag Dictionary Loading complete - " + stopTimer() + "ms");
	}
	
	private void hashSongFiles() {
		for (File songFile : songFiles) {
			final String md5 = FileUtils.getMd5FromFile(songFile.getAbsolutePath());
			final File tagObjectFile = new File(fileLocation + SONG_DIR + "/" + md5);
			TaggedSong taggedSong;
			if (tagObjectFile.exists()) {
				taggedSong = FileUtils.loadObjectFromFile(tagObjectFile, TaggedSong.class);
				if(!taggedSong.fileLocation.equals(songFile.getAbsolutePath())) {
					taggedSong.fileLocation = songFile.getAbsolutePath();
					updateSong(taggedSong);
				}
			} else {
				taggedSong = new TaggedSong();
				taggedSong.fileLocation = songFile.getAbsolutePath();
				taggedSong.identifier = md5;
				updateSong(taggedSong);
			}
			taggedSongs.add(taggedSong);
		}
	}
	
	private void checkSetUp() {
		String[] dirs = {HEAD_DIR, SONG_DIR, DICT_DIR};
		for (String dir : dirs) {
			File file = new File(fileLocation + dir);
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}
	
	public void printFolderData() {
		startTimer();
		System.out.println("Printing Folder Data");
		
		HashMap<String, Integer> folderMap = new HashMap<>();
		for (File file : songFiles) {
			final String fileKey = file.getParentFile().getName();
			Integer count;
			if (folderMap.containsKey(fileKey)) {
				count = folderMap.get(fileKey);
			} else {
				count = new Integer(0);
				folderMap.put(fileKey, count);
			}
			count++;
			folderMap.put(fileKey, count);
		}
		
		for (String key : folderMap.keySet()) {
			
		}
		
		System.out.println("Printing Folder Data Complete - " + stopTimer() + "ms");
	}

	@Override
	public void updateSong(TaggedSong song) {
		final String filename = fileLocation + SONG_DIR + "/" + song.identifier;
		FileUtils.writeObjectToFile(filename, song);
	}

	@Override
	public void updateDictionary(TagDictionary dictionary) {
		final String filename = fileLocation + DICT_DIR + "/" + dictionary.title;
		FileUtils.writeObjectToFile(filename, dictionary);
		
	}

	@Override
	public void removeDictionary(TagDictionary dictionary) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<TaggedSong> getTaggedSongs() {
		return taggedSongs;
	}

	@Override
	public List<TagDictionary> getTagDictionaries() {
		return tagDictionaries;
	}

	@Override
	public List<String> getAllTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> getSongsByTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> getSongsByTagsUnion(String... tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> getSongsByTagsIntersection(String... tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> getSongsByRange(String tagType, int startIndex, int endIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> getSongsInDictionary(TagDictionary tagDictionary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> filterSongsByTag(List<TaggedSong> taggedSongs, String tagType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> filterSongsByTagsUnion(List<TaggedSong> taggedSongs, String... tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> filterSongsByTagsIntersection(List<TaggedSong> taggedSongs, String... tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedSong> filterSongsByRange(List<TaggedSong> taggedSongs, String tagType, int startIndex,
			int endIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	private void startTimer() {
		timer = System.currentTimeMillis();
	}
	
	private long stopTimer() {
		return System.currentTimeMillis() - timer;
	}

}
