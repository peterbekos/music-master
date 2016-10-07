package musictag;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TaggedSong {
	String identifier;
    List<String> tags = new ArrayList();
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
    
	public void addTags(String... tags) {
		for (String tag : tags) {
			this.tags.add(tag);
		}
	}
	
	public void removeTags(String... tags) {
		for (String tag : tags) {
			if (this.tags.contains(tag)) {
				this.tags.remove(tag);
			}
		}
	}
	
	@Override
	public String toString() {
		File file = new File(fileLocation);
		String string = file.getName();
		if (!tags.isEmpty()) {
			string = string + " - ";
		}
		for (String tag : tags) {
			string = string + " #" + tag;
		}
		return string;
	}
    
}