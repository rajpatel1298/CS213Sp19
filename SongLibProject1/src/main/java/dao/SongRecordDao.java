package main.java.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.models.SongRecordDto;

public class SongRecordDao implements Dao<SongRecordDto> {

	private String filePath;
	
	public SongRecordDao(String filePath) {
		this.filePath = filePath;
		this.filePath = "src/main/resources/db/song_record_db.csv";
	}
	
	@Override
	public Optional<SongRecordDto> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SongRecordDto> getAll() {
		List<SongRecordDto> songList = new ArrayList<>();
		
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
			    	songList.add(song);
			    }
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	    return songList;
	}

	@Override
	public void save(SongRecordDto SongRecordDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SongRecordDto SongRecordDto, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SongRecordDto SongRecordDto) {
		// TODO Auto-generated method stub
		
	}

}
