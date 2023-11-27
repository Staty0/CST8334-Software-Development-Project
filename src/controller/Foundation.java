package controller;

import model.Card;

public class Foundation extends Pile {
	private int xOffset = 0;
	private int yOffset = 0;

	boolean canAddCard(Card card) {
		if (cards.isEmpty()) {
			// If the pile is empty, only aces can be added
			return (card.getRank().getValue() == 1);
		} else {
			Card topCard = cards.get(cards.size() - 1);
			// Check if same suit
			if (topCard.getSuit() == card.getSuit()) {
				// Check if the rank is one below
				return (topCard.getRank().getValue() + 1 == card.getRank().getValue());
			} else {
				return false;
			}
		}
	}

	// Update the GUI
	public void getStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}
}
