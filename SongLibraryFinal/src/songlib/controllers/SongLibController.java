package songlib.controllers;

import java.io.IOException;
import java.util.List;

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
	
	private ObservableList<String> obsList;
	
	
	public void start() {		
		this.obsList = FXCollections.observableArrayList(SongRecordDao.getInstance().getSortedSongList());
		songList.setItems(obsList);
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

}

