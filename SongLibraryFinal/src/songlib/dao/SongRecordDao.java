package songlib.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javafx.util.Callback;
import songlib.models.SongRecordDto;

public class SongRecordDao {
	
	private static final String DB_PATH = "src/songlib/resources/song_record_db.csv";
	
	private static String filePath;
	private static List<SongRecordDto> songListCache;
	private static Map<String, Integer> songSortOrderMap; // uses the song's titleAndArtist string as a key
	
	private SongRecordDao(String path) {
		filePath = path;
		getAll();
	}
	
	private static class InstanceHolder {
		public static SongRecordDao instance = new SongRecordDao(DB_PATH);
	}
	
	public static SongRecordDao getInstance() {
		return InstanceHolder.instance;
	}

	private static void getAll() {
		songListCache = new ArrayList<>();
		
		try (FileReader fr = new FileReader(filePath); 
			 BufferedReader br = new BufferedReader(fr)) {
		
	    	for (String songRecordLine = br.readLine(); songRecordLine != null; songRecordLine = br.readLine()) {
			    if (songRecordLine != null && !songRecordLine.isEmpty()) {
			    	/*
			    	 * split uses regular expression and in regex | is a metacharacter representing the OR operator. 
			    	 * You need to escape that character using \ (written in String as "\\" since \ is also a 
			    	 * metacharacter in String literals and require another \ to escape it).
			    	 */
			    	String[] songRecordAttributes = songRecordLine.split("\\|");
			    	
			    	if (songRecordAttributes.length != 4) {
			    		System.err.println("Error: Invalid number of columns in record entry.");
			    		continue;
			    	}
			    	
			    	SongRecordDto song = new SongRecordDto();
			    	song.setTitle(songRecordAttributes[0]);
			    	song.setAlbum(songRecordAttributes[1]);
			    	song.setArtist(songRecordAttributes[2]);
			    	song.setYear(Integer.parseInt(songRecordAttributes[3]));
			    	songListCache.add(song);
			    }
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAll(List<SongRecordDto> songRecordList) {
		try (FileWriter fw = new FileWriter(filePath)) {
			for (SongRecordDto song : songRecordList) {
				StringBuilder sb = new StringBuilder();
				sb.append(song.getTitle());
				sb.append('|');
				sb.append(song.getAlbum());
				sb.append('|');
				sb.append(song.getArtist());
				sb.append('|');
				if (song.getYear() != null) {
					sb.append(song.getYear());
				}
				sb.append("\n");
				fw.write(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getSortedSongList() {
		// sort the song list cache
		Collections.sort(this.songListCache);
		// clear the sort order Map and reset.
		songSortOrderMap = new TreeMap<String, Integer>();
		
		String[] sortedSongArray = new String[this.songListCache.size()];
		int i = 0;
		for (SongRecordDto song : this.songListCache) {
			songSortOrderMap.put(song.getTitleAndArtist(), i);
			sortedSongArray[i++] = song.toString();
		}
		
		return sortedSongArray;
	}
	
	public int getIndexFromSortOrderMap(String target) {
		if (songSortOrderMap != null) {
			return songSortOrderMap.get(target);
		}
		return 0;
	}

	public int addNewSongRecord(SongRecordDto songRecord) {
		this.songListCache.add(songRecord);
		Collections.sort(this.songListCache);
		this.songSortOrderMap = new TreeMap<String, Integer>();
		
		String selectedSongTitleAndArtist = songRecord.getTitleAndArtist();
		int selectedIndex = 0;
		int i = 0;
		for (SongRecordDto song : this.songListCache) {
			if (selectedSongTitleAndArtist.equals(song.getTitleAndArtist())) {
				// we do this so we can pass it back to the SongLibAddView 
				// and set the SongLibView's songList(listView) selected record before we transition
				selectedIndex = i;
			}
			this.songSortOrderMap.put(song.getTitleAndArtist(), i++);
		}
		
		// persist to file system
		this.saveAll(this.songListCache);
		
		return selectedIndex;
	}

	public int deleteSong(int selectedRecordIndex) {
		this.songListCache.remove(selectedRecordIndex);
		this.songSortOrderMap = new TreeMap<String, Integer>();
		
		int i = 0;
		for (SongRecordDto song : this.songListCache) {
			this.songSortOrderMap.put(song.getTitleAndArtist(), i++);
		}
		// persist to file system
		this.saveAll(this.songListCache);
		
		// list is now empty
		if (selectedRecordIndex == 0) {
			return selectedRecordIndex;
		}
		// if we delete the last record in the list then select the previous record
		if (selectedRecordIndex > songListCache.size()-1) {
			return selectedRecordIndex-1;
		}
		// else select the next record
		return selectedRecordIndex;
	}

	public SongRecordDto getSongRecord(int selectedRecordIndex) {
		return this.songListCache.get(selectedRecordIndex);
	}

	public boolean isValidTitleAndArtist(String titleAndArtist) {
		return !this.songSortOrderMap.containsKey(titleAndArtist);
	}

}
