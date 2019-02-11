package songlib.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/songlib/view/SongLibView.fxml"));
			
			Scene songLibScene = new Scene((Parent)loader.load());
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			// create new record
			SongRecordDto songRecord = new SongRecordDto();
			songRecord.setTitle(songTitleTextField.getText());
			songRecord.setAlbum(albumTitleTextField.getText());
			songRecord.setArtist(artistNameTextField.getText());
			songRecord.setYear(Integer.parseInt(albumYearTextField.getText()));
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
}
