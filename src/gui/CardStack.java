package gui;

import java.util.Iterator;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Card;

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

	public void stackGenetator(StackPane stackPane, List<Card> cards, int xOffset, int yOffset) {
		// Add a blank back to show empty stacks
		stackPane.getChildren().add(createBlankArea());

		// iterator to go through the cards given.
		if (cards != null) {
			Iterator<Card> iterator = cards.iterator();
			int index = 0;

			while (iterator.hasNext()) {
				Card card = iterator.next();
				ImageView layeredImageView = card.getImageView();
				layeredImageView.setTranslateX(xOffset * -index); // Adjust the X offset as needed
				layeredImageView.setTranslateY(yOffset * index); // Adjust the Y offset as needed
				stackPane.getChildren().add(layeredImageView);
				index++;
			}
		}
	}

	// Empty Rectangle for blank area
	private Rectangle createBlankArea() {
		Rectangle card = new Rectangle(120, 180);
		card.setFill(Color.WHITE);
		card.setStroke(Color.BLACK);
		return card;
	}
}
