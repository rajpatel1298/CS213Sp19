package songlib.controllers;

/*
 * Ian Moreno
 * Raj Patel
 * 
 */

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import songlib.dao.SongRecordDao;
import songlib.models.SongRecordDto;

public class SongLibAddController {
	
	@FXML private TextField songTitleTextField;
	@FXML private TextField albumTitleTextField;
	@FXML private TextField artistNameTextField;
	@FXML private TextField albumYearTextField;
	@FXML private Button addButton;
	@FXML private Button clearAllButton;
	@FXML private Button cancelButton;
	
	public void addButtonClicked(ActionEvent event) {
		String songTitle = songTitleTextField.getText();
		if (!isValidSongTitle(songTitle)) {
			return;
		}
		String artistName = artistNameTextField.getText();
		if (!isValidArtistName(artistName)) {
			return;
		}
		String albumTitle = albumTitleTextField.getText();
		if (!isValidAlbumTitle(albumTitle)) {
			return;
		}
		Integer albumYear = null;
		if (!albumYearTextField.getText().isEmpty()) {
			if (!parseValidAlbumYear(albumYearTextField.getText())) {
				return;
			}
			albumYear = Integer.parseInt(albumYearTextField.getText());
		}
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/songlib/view/SongLibView.fxml"));
			
			Scene songLibScene = new Scene((Parent)loader.load());
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			// create new record
			SongRecordDto songRecord = new SongRecordDto();
			songRecord.setTitle(songTitle);
			songRecord.setAlbum(albumTitle);
			songRecord.setArtist(artistName);
			songRecord.setYear(albumYear);
			
			// check that record is valid, and that a record does not already exist with same title and artist
			if (!this.isValidTitleAndArtist(songRecord.getTitleAndArtist())) {
				Alert alert = new Alert(AlertType.ERROR, songRecord.toString() + " already exists.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			
			// add new record to data cache and get the sorted index back so we can set
			// the songList (listView) selected record
			SongLibController.selectedRecordIndex = SongRecordDao.getInstance().addNewSongRecord(songRecord);
			
			SongLibController songLibController = loader.getController();
			songLibController.start();
			
			window.setScene(songLibScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isValidSongTitle(String songTitle) {
		if (songTitle == null || songTitle.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Please Enter a Song Title.", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		if (songTitle.contains("|")) {
			Alert alert = new Alert(AlertType.ERROR, "Song title cannot contain | characters.", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		return true;
	}
	
	private boolean isValidArtistName(String artistName) {
		if (artistName == null || artistName.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Please Enter an Artist Name.", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		if (artistName.contains("|")) {
			Alert alert = new Alert(AlertType.ERROR, "ArtistName cannot contain | characters.", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		return true;
	}
	
	private boolean isValidAlbumTitle(String albumTitle) {
		if (albumTitle.contains("|")) {
			Alert alert = new Alert(AlertType.ERROR, "Album Title cannot contain | characters.", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		return true;
	}
	
	private boolean parseValidAlbumYear(String albumYear) {
		if (tryParseInteger(albumYear)) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Please Enter a Valid Album Year.", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
	}
	
	private boolean isValidTitleAndArtist(String titleAndArtist) {
		return SongRecordDao.getInstance().isValidTitleAndArtist(titleAndArtist);
	}

	public void cancelButtonClicked(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/songlib/view/SongLibView.fxml"));
			
			Scene songLibScene = new Scene((Parent)loader.load());
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			SongLibController songLibController = loader.getController();
			songLibController.start();
			
			window.setScene(songLibScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearAllButtonClicked(ActionEvent event) {
		this.songTitleTextField.setText("");
		this.albumTitleTextField.setText("");
		this.artistNameTextField.setText("");
		this.albumYearTextField.setText("");
	}
	
	private boolean tryParseInteger(String value) {  
	     try {  
	         int year = Integer.parseInt(value); 
	         if (year < 0 || year > 2025) {
	        	 return false;
	         }
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
}
