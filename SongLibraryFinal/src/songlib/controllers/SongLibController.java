package songlib.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import songlib.dao.SongRecordDao;

public class SongLibController {
	
	@FXML private ListView songList;
	@FXML private Button addButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	
	public static int selectedRecordIndex;
	private ObservableList<String> obsList;

	
	@SuppressWarnings("unchecked")
	public void start() {		
		this.obsList = FXCollections.observableArrayList(SongRecordDao.getInstance().getSortedSongList());
		songList.setItems(obsList);
		
		// set focus on the selected song record
		songList.getSelectionModel().select(selectedRecordIndex);
		songList.getFocusModel().focus(selectedRecordIndex);
		
		songList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selectedRecordIndex = songList.getSelectionModel().getSelectedIndex();
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
			Parent songLibEditViewParent = FXMLLoader.load(getClass().getResource("/songlib/view/SongLibEditView.fxml"));
			Scene songLibEditScene = new Scene(songLibEditViewParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(songLibEditScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteButtonClicked(ActionEvent event) {
		selectedRecordIndex = SongRecordDao.getInstance().deleteSong(selectedRecordIndex);
		this.start();
	}

}

