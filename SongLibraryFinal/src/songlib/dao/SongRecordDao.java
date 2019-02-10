package songlib.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.util.Callback;
import songlib.models.SongRecordDto;

public class SongRecordDao {
	
	private static final String DB_PATH = "src/songlib/resources/song_record_db.csv";
	
	private static String filePath;
	private static List<SongRecordDto> songListCache;
	private static Map<String, String> songDetailMap;
	
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
			    	String[] songRecordAttributes = songRecordLine.split(",");
			    	
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
				sb.append(',');
				sb.append(song.getAlbum());
				sb.append(',');
				sb.append(song.getArtist());
				sb.append(',');
				sb.append(song.getYear());
				sb.append("\n");
				fw.write(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getSortedSongList() {
		Collections.sort(this.songListCache);
		
		String[] sortedSongArray = new String[this.songListCache.size()];
		int i = 0;
		for (SongRecordDto song : this.songListCache) {
			sortedSongArray[i++] = song.toString();
		}
		
		return sortedSongArray;
	}

}
