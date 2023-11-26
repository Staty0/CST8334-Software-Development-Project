package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class GraphicFromSheet {
	// Singleton pattern to avoid multiple unneeded objects
	private static GraphicFromSheet single_instance = null;

	private GraphicFromSheet() {
	}

	public static synchronized GraphicFromSheet getInstance() {
		if (single_instance == null)
			single_instance = new GraphicFromSheet();

		return single_instance;
	}

	public ImageView cardGet(double suit, double number) {
		// Load the card sheet image
		Image cardSheet = new Image("file:images/card-deck-161536_1920.png");

		// Set which row and column to load from
		// Suits are as follows: 1 is Clubs, 2 is Diamonds, 3 is Hearts, 4 is Spades
		// Column 5 has two blank cards on 1 and 2, 3 has the card back
		double row = number - 1;
		double column = suit - 1;

		// Define the dimensions of a single card in the card sheet
		double cardWidth = 147; // Width of a single card
		double cardHeight = 230; // Height of a single card

		// Define the coordinates of the region you want to display
		double startX = (row * 147) + 1; // X-coordinate of the top-left corner of the region
		double startY = column * 230; // Y-coordinate of the top-left corner of the region

		// Create an ImageView and crop the image to the part of it we need
		ImageView imageView = new ImageView(cardSheet);
		imageView.setViewport(new javafx.geometry.Rectangle2D(startX, startY, cardWidth, cardHeight));

		// Resize the card to the size we need
		imageView.setFitWidth(120);
		imageView.setFitHeight(180);

		if (suit < 5) {
			addDragAndDrop(imageView);
		}

		return imageView;
	}

	// Array version of the cardGet, suit goes first, number second
	public ImageView cardGet(int[] card) {
		ImageView imageView = cardGet(card[0], card[1]);
		return imageView;
	}

	private void addDragAndDrop(ImageView imageView) {
		imageView.setOnDragDetected(event -> {
			// Start drag-and-drop
			Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);

			// Put the image being dragged centered behind the cursor 
			ClipboardContent content = new ClipboardContent();
			content.put(DataFormat.IMAGE, imageView);
			dragboard.setContent(content);
			dragboard.setDragView(imageView.snapshot(null, null), 60, 90);

			event.consume();
		});

	}
}
