package gui;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUI extends Application {
	private CardStack cardStack = CardStack.getInstance();
	Random rand = new Random();
	GraphicFromSheet graphics = GraphicFromSheet.getInstance();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Solitaire");

		// Create the main layout
		GridPane base = new GridPane();
		
		// Foundation piles
		for (int i = 0; i < 4; i++) {
			Rectangle blankArea = createBlankArea();
            GridPane.setMargin(blankArea, new Insets(5));
			base.add(blankArea, i, 0);
		}

		// Teableau area
		for (int i = 0; i < 8; i++) {
			StackPane stack = cardStack.stackGenetator(i, 0, 20);
            GridPane.setMargin(stack, new Insets(5));
            //Below adds a random card
            ImageView layeredImageView = graphics.cardGet(rand.nextInt(3) + 1, rand.nextInt(12) + 1);
			layeredImageView.setTranslateX(0 * -(i + 1)); // Adjust the X offset as needed
			layeredImageView.setTranslateY(20 * (i + 1)); // Adjust the Y offset as needed
			stack.getChildren().add(layeredImageView);
			base.add(stack, i, 1);
		}
		
		// Draw pile
		StackPane drawPile = cardStack.stackGenetator(3, 15, 0);
		GridPane.setMargin(drawPile, new Insets(5));
		base.add(drawPile, 8, 0);

		// Screen size and start
		Scene scene = new Scene(base, 1366, 768);
		primaryStage.setScene(scene);

		primaryStage.show();
	}
	
	// Empty Rectangle for blank area
	private Rectangle createBlankArea() {
		Rectangle card = new Rectangle(120, 180);
		card.setFill(Color.WHITE);
		card.setStroke(Color.BLACK);
		return card;
	}

	// Test launch
	public static void main(String[] args) {
		launch(args);
	}
}