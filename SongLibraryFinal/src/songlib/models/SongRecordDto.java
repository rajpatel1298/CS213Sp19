package songlib.models;

public class SongRecordDto implements Comparable<SongRecordDto> {

	private String title;
	private String artist;
	private String album;
	private int year;
	private String titleAndArtist;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getTitleAndArtist() {
		String titleAndArtist = "";
		if (this.getTitle() == null || this.getArtist() == null) {
			System.err.println("Error: Song Title and Artist cannot be null.");
		} else {
			titleAndArtist = this.getTitle() + this.getArtist();
			titleAndArtist = titleAndArtist.replaceAll("\\s+","");
			titleAndArtist = titleAndArtist.toLowerCase();
		}
		return titleAndArtist;
	}

	@Override
	public int compareTo(SongRecordDto otherSongRecordDto) {
		return this.getTitleAndArtist().compareTo(otherSongRecordDto.getTitleAndArtist());
	}
	
	@Override
	public String toString() {
		return "Title: " + this.getTitle() + ",  Artist: " + this.getArtist();
	}
	
}
