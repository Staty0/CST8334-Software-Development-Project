package controller;

import javafx.scene.layout.StackPane;
import model.Card;
import model.Rank;

public class Tableau extends Pile {
	private int xOffset = 0;
	private int yOffset = 20;

	boolean canAddCard(Card card) {
		if (cards.isEmpty()) {
			// If the pile is empty, only kings can be added
			Card topCard = cards.get(cards.size() - 1);
			return (topCard.getRank() == Rank.KING);
		} else {
			Card topCard = cards.get(cards.size() - 1);
			// Check if the colors alternate
			if (topCard.isRed() && card.isBlack() || (topCard.isBlack() && card.isRed())) {
				// Check if the rank is one below
				return (topCard.getRank().getValue() - 1 == card.getRank().getValue());
			} else {
				return false;
			}
		}
	}

	public Card removeTopCard() {
		if (!cards.isEmpty()) {
			cards.remove(cards.size() - 1);
			// If the teableau still has cards, check to make sure the top one is face up
			if (!cards.isEmpty()) {
				flipTopCard();
			}
		}
		return null; // Pile is empty
	}

	public void flipTopCard() {
		Card topCard = cards.get(cards.size() - 1);
		if (topCard.isFaceUp() == false) {
			topCard.flip();
		}
	}
	
	public void getStackView(StackPane stackPane) {
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}
}
