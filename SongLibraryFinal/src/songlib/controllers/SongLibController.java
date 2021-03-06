package songlib.controllers;

/*
 * Ian Moreno
 * Raj Patel
 * 
 */

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import songlib.dao.SongRecordDao;
import songlib.models.SongRecordDto;

public class SongLibController {
	
	@FXML private ListView songList;
	@FXML private Button addButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private Text songDetailText;
	
	public static int selectedRecordIndex;
	private ObservableList<String> obsList;

	
	@SuppressWarnings("unchecked")
	public void start() {		
		int tempSelectedIndex = selectedRecordIndex;
		this.obsList = FXCollections.observableArrayList(SongRecordDao.getInstance().getSortedSongList());
		songList.setItems(obsList);
	
		// we need this because the above statement songList.setItems(obsList) 
		// triggers the event listener on the obsList and sets the selectedRecordIndex to -1
		selectedRecordIndex = tempSelectedIndex;
		
		// set focus on the selected song record
		songList.getSelectionModel().select(selectedRecordIndex);
		songList.getFocusModel().focus(selectedRecordIndex);
		
		if (selectedRecordIndex >= 0 && SongRecordDao.getInstance().getSortedSongList().length > 0) {
			SongRecordDto song = SongRecordDao.getInstance().getSongRecord(selectedRecordIndex);
			StringBuilder detailsSB = new StringBuilder();
			if (song.getTitle() != null && !song.getTitle().isEmpty()) {
				detailsSB.append("Title: ");
				detailsSB.append(song.getTitle());
				detailsSB.append("\n");
			}
			if (song.getAlbum() != null && !song.getAlbum().isEmpty()) {
				detailsSB.append("Album: ");
				detailsSB.append(song.getAlbum());
				detailsSB.append("\n");
			}
			if (song.getArtist() != null && !song.getArtist().isEmpty()) {
				detailsSB.append("Artist: ");
				detailsSB.append(song.getArtist());
				detailsSB.append("\n");
			}
			if (song.getYear() != null) {
				detailsSB.append("Year: ");
				detailsSB.append(song.getYear());
			}	
			songDetailText.setText(detailsSB.toString());
		} else {
			songDetailText.setText("Title:\nAlbum:\nArtist:\nYear:");
		}
		
		// we want to disable the edit button if there are no records in the list
		if (SongRecordDao.getInstance().getSortedSongList().length == 0) {
			editButton.setDisable(true);
		} else {
			editButton.setDisable(false);
		}
		
		songList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selectedRecordIndex = songList.getSelectionModel().getSelectedIndex();
				if (selectedRecordIndex >= 0 && SongRecordDao.getInstance().getSortedSongList().length > 0) {
					SongRecordDto song = SongRecordDao.getInstance().getSongRecord(selectedRecordIndex);
					StringBuilder detailsSB = new StringBuilder();
					if (song.getTitle() != null && !song.getTitle().isEmpty()) {
						detailsSB.append("Title: ");
						detailsSB.append(song.getTitle());
						detailsSB.append("\n");
					}
					if (song.getAlbum() != null && !song.getAlbum().isEmpty()) {
						detailsSB.append("Album: ");
						detailsSB.append(song.getAlbum());
						detailsSB.append("\n");
					}
					if (song.getArtist() != null && !song.getArtist().isEmpty()) {
						detailsSB.append("Artist: ");
						detailsSB.append(song.getArtist());
						detailsSB.append("\n");
					}
					if (song.getYear() != null) {
						detailsSB.append("Year: ");
						detailsSB.append(song.getYear());
					}	
					songDetailText.setText(detailsSB.toString());
				} else {
					songDetailText.setText("Title:\nAlbum:\nArtist:\nYear:");
				}
			}
		});
	}
	
	public void addButtonClicked(ActionEvent event) {
		try {
			Parent songLibAddViewParent = FXMLLoader.load(getClass().getResource("/songlib/view/SongLibAddView.fxml"));
			Scene songLibAddScene = new Scene(songLibAddViewParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(songLibAddScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editButtonClicked(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/songlib/view/SongLibEditView.fxml"));
			Parent songLibEditViewParent = fxmlLoader.load();
			SongLibEditController songLibEditController = fxmlLoader.<SongLibEditController>getController();
			songLibEditController.start();
			
			Scene songLibEditScene = new Scene(songLibEditViewParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(songLibEditScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteButtonClicked(ActionEvent event) {
		// verify that user wishes to delete the record
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are You Sure You Want to Delete?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			selectedRecordIndex = SongRecordDao.getInstance().deleteSong(selectedRecordIndex);
			this.start();
		}
		return;
		
	}

}

