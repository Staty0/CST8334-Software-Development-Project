package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import controller.GUIController;


/**
 * JavaFX App
 */
public class App extends Application {
    GUIController controller = new GUIController();
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomgui.fxml"));

        // Load the root node
        Parent root = loader.load();
        // load the css file
        root.getStylesheets().add(getClass().getResource("welcomgui.css").toExternalForm());
        
        stage.setTitle("Solitaire Game");
        
        stage.setScene(new Scene(root));
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.setFill(Color.SPRINGGREEN);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}