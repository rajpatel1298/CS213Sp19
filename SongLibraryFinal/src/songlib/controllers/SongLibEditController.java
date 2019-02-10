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

public class SongLibEditController {

	@FXML private TextField songTitleTextField;
	@FXML private TextField albumTitleTextField;
	@FXML private TextField artistNameTextField;
	@FXML private TextField albumYearTextField;
	@FXML private Button saveButton;
	@FXML private Button clearAllButton;
	@FXML private Button cancelButton;
	
	public void cancelButtonClicked(ActionEvent event) {
		try {
			Parent songLibViewParent = FXMLLoader.load(getClass().getResource("/songlib/view/SongLibView.fxml"));
			Scene songLibScene = new Scene(songLibViewParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(songLibScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
