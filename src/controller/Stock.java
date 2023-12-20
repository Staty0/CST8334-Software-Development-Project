package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.ImageView;
import model.Card;

public class Stock extends Pile {
	private int xOffset = 0;
	private int yOffset = 0;
	
	@Override
	public boolean addCard(Card card) {
		if (canAddCard(card)) {

			cards.add(card);
			
			// Render code
			ImageView layeredImageView = card.getImageView();
			layeredImageView.setTranslateX(xOffset * (cards.size() - 1));
			layeredImageView.setTranslateY(yOffset * (cards.size() - 1));
			stackPane.getChildren().add(layeredImageView);

			return true;
		} else {
			// Handle invalid move (e.g., show a message, log, etc.)
			System.out.println("Invalid move");
			return false;
		}
	}


	@Override
	boolean canAddCard(Card card) {
		return !card.isFaceUp();
	}

	@Override
	public void updateStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}

	public List<Card> drawAll() {
		List<Card> dealtCards = new ArrayList<>(cards);
		cards.clear();
		Collections.reverse(dealtCards); 
		return dealtCards;
	}
}
