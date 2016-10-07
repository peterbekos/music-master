package musictag;
import java.util.ArrayList;
import java.util.List;

public class TagDictionary {
	String title;
    boolean isRange;
    List<String> tags = new ArrayList<>();
    
    public TagDictionary(String title, String... tags) {
    	this.title = title;
    	for (String tag : tags) {
    		this.tags.add(tag);
    	}
    }
    
    public String getTitle() {
    	return title;
    }
    
    @Override
    public String toString() {
    	String string = "[ " + title + " ]\n";
    	for (String tag : tags) {
    		string = string + tag + "\n";
    	}
    	return string;
    }
    
}