package controller;

import java.util.ArrayList;
import java.util.List;

import model.Card;

public class Stock extends Pile {
	private int xOffset = 0;
	private int yOffset = 0;

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
		return dealtCards;
	}
}
