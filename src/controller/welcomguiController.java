package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class welcomguiController {

	private boolean isVegasMode = false;

	// start new game (gui.fxml)
	public void newGame(ActionEvent event) {
	    try {
	        FXMLLoader guiLoader = new FXMLLoader(getClass().getResource("/gui/gui.fxml"));
	        Parent gui = guiLoader.load();

	        GUIController guiController = guiLoader.getController();

	        if (isVegasMode) {
	            guiController.setVegasMode(isVegasMode);
	            guiController.initialize();
	        }

//	        System.out.println("new game in welcoming controller : isVegasMode = " + isVegasMode);

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        gui.getStylesheets().add(getClass().getResource("/gui/gui.css").toExternalForm());
	        stage.setScene(new Scene(gui));
	        stage.centerOnScreen();
	        stage.setResizable(false); // Lock the window
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	// start Vegas mode
	public void startVegasMode(ActionEvent event) {
		isVegasMode = true; // Set Vegas mode flag
		newGame(event); // Redirect to newGame method
	}
	// exit the game
	public void exit(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
