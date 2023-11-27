package controller;

import gui.DragAndDrop;
import model.Card;

public class Tableau extends Pile {
	private int xOffset = 0;
	private int yOffset = 20;
	
	private DragAndDrop dragAndDrop = DragAndDrop.getInstance();

	boolean canAddCard(Card card) {
		if (cards.isEmpty()) {
			// If the pile is empty, only kings can be added
			return (card.getRank().getValue() == 13);
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

	// If the top card is face down, flip it up and put it in play
	public void flipTopCard() {
		Card topCard = cards.get(cards.size() - 1);
		if (topCard.isFaceUp() != true) {
			topCard.flip();
			dragAndDrop.createDraggableCardView(topCard, this);
		}
	}
	
	// Update the GUI
	public void getStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}
}
