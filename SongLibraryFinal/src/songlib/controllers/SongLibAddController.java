package songlib.controllers;

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
		if (songTitle == null || songTitle.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Please Enter a Song Title", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		String artistName = artistNameTextField.getText();
		if (artistName == null || artistName.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Please Enter an Artist Name", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		String albumTitle = albumTitleTextField.getText();
		Integer songYear = null;
		if (!albumYearTextField.getText().isEmpty()) {
			if (tryParseInteger(albumYearTextField.getText())) {
				songYear = Integer.parseInt(albumYearTextField.getText());
			} else {
				Alert alert = new Alert(AlertType.ERROR, "Please Enter a Valid Year.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
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
			songRecord.setYear(songYear);
			
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
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
}
