package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class welcomguiController {

	
	// start new game (gui.fxml)
public void newGame(ActionEvent event) {

    try {
        Parent gui = FXMLLoader.load(getClass().getResource("/gui/gui.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // load the css file
        gui.getStylesheets().add(getClass().getResource("/gui/gui.css").toExternalForm());
        stage.setScene(new Scene(gui));
        // make the window in the center of the screen
        stage.centerOnScreen();
        stage.setResizable(false); // Lock the window
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
	
	// exit the game
	public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
	}
}
