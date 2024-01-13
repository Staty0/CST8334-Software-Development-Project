package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.ImageView;
import model.Card;

public class Stock extends Pile {
	private int pastThrough = 0;
	private int scoreOnTalonReturn = -100;
	
	public Stock() {
		scoreOnRemove = 5;
	}
	
	@Override
	public boolean addCard(Card card, int count) {
		if (canAddCard(card, count)) {

			cards.add(card);
			
			// Render code
			ImageView layeredImageView = card.getImageView();
			layeredImageView.setTranslateX(xOffset * (cards.size() - 1));
			layeredImageView.setTranslateY(yOffset * (cards.size() - 1));
			stackPane.getChildren().add(layeredImageView);

			return true;
		} else {
			// Handle invalid move
			return false;
		}
	}


	@Override
	boolean canAddCard(Card card, int count) {
		return !card.isFaceUp();
	}

	public void resetPastThrough() {
		pastThrough = 0;
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
		
		if (pastThrough >= 1) {
			ScoreManager.getInstance().addScore(scoreOnTalonReturn);
		}
		pastThrough += 1;
				
		return dealtCards;
	}
}
