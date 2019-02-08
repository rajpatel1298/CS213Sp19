package main.java;

import main.java.dao.SongRecordDao;

public class SongLib {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SongRecordDao dao = new SongRecordDao("path");
		dao.getAll();
	}

}
