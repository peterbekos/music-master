package musictag.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import musictag.TagDictionary;

public enum FileUtils {
	INSTANCE;
	
	static {
		init();
	}
	
	private static List<File> runningFileCache;
	private static MessageDigest messageDigest;
	
	public static void init() {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static List<File> getSongFilesFromPath(String path) {
		runningFileCache = new ArrayList<File>();
		
		walk(path);
		
		return runningFileCache;
		
	}

    private static void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
//                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
//                System.out.println( "File:" + f.getAbsoluteFile() );
            	if (isSongFile(f)) {
            		runningFileCache.add(f);
            	}
            }
        }
    }
    
    private static final String[] SONGFILE_ENDINGS = {".mp3", ".wav"};
    public static boolean isSongFile(File file) {
    	final String name = file.getName().toLowerCase();
    	for (String songfileEnding : SONGFILE_ENDINGS) {
    		if (name.endsWith(songfileEnding.toLowerCase())) {
    			return true;
    		}
    	}
    	return false;
    	
    }
    
    public static String getMd5FromFile(String path) {
    	try {
			FileInputStream fis = new FileInputStream(path);
			byte[] dataBytes = new byte[1024];
		    
		    int nread = 0; 
		    
		    while ((nread = fis.read(dataBytes)) != -1) {
		    	messageDigest.update(dataBytes, 0, nread);
		    };

		    byte[] mdbytes = messageDigest.digest();
		   
		    //convert the byte to hex format
		    StringBuffer sb = new StringBuffer("");
		    for (int i = 0; i < mdbytes.length; i++) {
		    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    
		    return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    public static void writeObjectToFile(String filename, Object object) {
		Gson gson = new Gson();
		String s = gson.toJson(object);
		
		try {
			Files.write(Paths.get(filename), s.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static <T> T loadObjectFromFile(File objectFile, Class<T> objectType) {
		try {
			FileInputStream fis = new FileInputStream(objectFile);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			    sb.append(line);
			}
	
			String json = sb.toString();
			Gson gson = new Gson();
			T tagDictionary = (T) gson.fromJson(json, objectType);
			return tagDictionary;
		} catch (Exception e) {
			return null;
		}
	}
    
}