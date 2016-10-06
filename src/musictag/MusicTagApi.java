package musictag;
import java.util.List;

public interface MusicTagApi {
	
	void load(String fileLocation);
	void updateSong(TaggedSong song);
	void updateDictionary(TagDictionary dictionary);
	void removeDictionary(TagDictionary dictionary);

    List<TaggedSong> getTaggedSongs();
    List<TagDictionary> getTagDictionaries();
    List<String> getAllTags();

    List<TaggedSong> getSongsByTag(String tag);
    List<TaggedSong> getSongsByTagsUnion(String... tags);
    List<TaggedSong> getSongsByTagsIntersection(String... tags);
    List<TaggedSong> getSongsByRange(String tagType, int startIndex, int endIndex);
    List<TaggedSong> getSongsInDictionary(TagDictionary tagDictionary);

    List<TaggedSong> filterSongsByTag(List<TaggedSong> taggedSongs, String tagType);
    List<TaggedSong> filterSongsByTagsUnion(List<TaggedSong> taggedSongs, String... tags);
    List<TaggedSong> filterSongsByTagsIntersection(List<TaggedSong> taggedSongs, String... tags);
    List<TaggedSong> filterSongsByRange(List<TaggedSong> taggedSongs, String tagType, int startIndex, int endIndex);    

}