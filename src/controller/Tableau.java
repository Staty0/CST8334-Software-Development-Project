package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.ImageView;
import model.Card;
import model.Rank;

public class Tableau extends Pile {
	private int xOffset = 0;
	private int yOffset = 20;
	private List<Card> topStack = new ArrayList<Card>();

	boolean canAddCard(Card card) {
		if (cards.isEmpty()) {
			// If the pile is empty, only kings can be added
			return (card.getRank() == Rank.KING);
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

	public boolean addCard(Card card) {
		if (canAddCard(card)) {
			cards.add(card);

			topStack.add(card);

			// Render code update
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

	public Card removeTopCard() {
		if (!cards.isEmpty()) {
			Card removedCard = cards.remove(cards.size() - 1);
			topStack.remove(removedCard);
			// If the teableau still has cards, check to make sure the top one is face up
			if (!cards.isEmpty()) {
				flipTopCard();
			}
			return removedCard;
		}
		return null; // Pile is empty
	}

	// If the top card is face down, flip it up and put it in play
	public void flipTopCard() {
		Card topCard = cards.get(cards.size() - 1);
		if (topCard.isFaceUp() != true) {
			topCard.flip();

			stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
			ImageView layeredImageView = topCard.getImageView();
			layeredImageView.setTranslateX(xOffset * (cards.size() - 1));
			layeredImageView.setTranslateY(yOffset * (cards.size() - 1));
			stackPane.getChildren().add(layeredImageView);

			List<Card> listAdapter = new ArrayList<Card>(Arrays.asList(topCard));
			dragAndDrop.createDraggableCardStack(listAdapter, this);
			topStack.clear();
			topStack.add(topCard);
		}
	}

	// Update the GUI
	public void updateStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}

	public void updateDragNDrop() {
		// Update the top card's drag and drop to reference it's new pile
		if (!topStack.isEmpty()) {
			for (int i = 0; i < topStack.size() - 1; i++) {
				List<Card> subArray = new ArrayList<>(topStack.subList(i, topStack.size()));
				dragAndDrop.createDraggableCardStack(subArray, this);
			}
		}
	}
}
