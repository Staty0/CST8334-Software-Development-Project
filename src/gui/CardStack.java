package gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CardStack {
	// Singleton pattern to avoid multiple unneeded objects
	private static CardStack single_instance = null;
	// Get the Card graphic class
	GraphicFromSheet graphics = GraphicFromSheet.getInstance();

	private CardStack() {
	}

	public static synchronized CardStack getInstance() {
		if (single_instance == null)
			single_instance = new CardStack();

		return single_instance;
	}

	// Card back only stack
	public StackPane stackGenetator(int cards, int xoffset, int yoffset) {

		// Create a StackPane to hold the layered ImageViews
		StackPane stackPane = new StackPane();

		for (int i = 0; i < cards; i++) {
			ImageView layeredImageView = graphics.cardGet(5, 3);
			layeredImageView.setTranslateX(xoffset * -(i + 1)); // Adjust the X offset as needed
			layeredImageView.setTranslateY(yoffset * (i + 1)); // Adjust the Y offset as needed
			stackPane.getChildren().add(layeredImageView);
		}
		
		// Create a Scene with the StackPane
		return stackPane;
	}

	// Stacking with numbers
	public StackPane stackGenetator(int xoffset, int yoffset, int[][] cards) {
			// Create a StackPane to hold the layered ImageViews
			StackPane stackPane = new StackPane();
			
			for (int i = 0; i < cards.length; i++) {
				ImageView layeredImageView = graphics.cardGet(cards[i]);
				layeredImageView.setTranslateX(xoffset * -(i + 1));
				layeredImageView.setTranslateY(yoffset * (i + 1)); 
				stackPane.getChildren().add(layeredImageView);
			}
			
			// Create a Scene with the StackPane
			return stackPane;
		}
}
