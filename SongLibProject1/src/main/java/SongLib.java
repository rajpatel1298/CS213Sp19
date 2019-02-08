package main.java;

import java.util.ArrayList;
import java.util.List;

import main.java.dao.SongRecordDao;
import main.java.models.SongRecordDto;

public class SongLib {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SongRecordDao dao = new SongRecordDao("path");
		dao.getAll();
		
		SongRecordDto song1 = new SongRecordDto();
		song1.setTitle("A new title1");
		song1.setAlbum("A new Album Name");
		song1.setArtist("Ian");
		song1.setYear(1999);
		
		SongRecordDto song2 = new SongRecordDto();
		song2.setTitle("A new title2");
		song2.setAlbum("A new Album Name2");
		song2.setArtist("Raj");
		song2.setYear(2099);
		
		List<SongRecordDto> songsList = new ArrayList<>();
		songsList.add(song1);
		songsList.add(song2);
		
		dao.saveAll(songsList);
		
	}

}
