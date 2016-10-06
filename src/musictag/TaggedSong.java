package musictag;
import java.util.List;

public class TaggedSong {
	String identifier;
    List<String> tags;
    String fileLocation;
    
    
	public String getIdentifier() {
		return identifier;
	}
	public List<String> getTags() {
		return tags;
	}
	public String getFileLocation() {
		return fileLocation;
	}
    
    
}