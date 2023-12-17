package controller;

import java.util.ArrayList;
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
			if (!cards.isEmpty()) {
				Card pastTopCard = cards.get(cards.size() - 1);
				dragAndDrop.makeNonDraggable(pastTopCard);
			}
			
			cards.add(card);
			
			//Update the drag and drop to reference this pile
			dragAndDrop.createDraggableCardView(card, this);
			
			topStack.add(card);
			dragAndDrop.createDraggableCardStack(topStack, this);
			
            // Attempted render code update
            
            //ImageView layeredImageView = card.getImageView();
			//layeredImageView.setTranslateX(xOffset * cards.size() - 1);
			//layeredImageView.setTranslateY(yOffset * cards.size() - 1); 
			//stackPane.getChildren().add(layeredImageView);
			
			return true;
		} else {
			// Handle invalid move (e.g., show a message, log, etc.)
			System.out.println("Invalid move");
			return false;
		}
	}

	public Card removeTopCard() {
		if (!cards.isEmpty()) {
			Card removedCard = cards.remove(cards.size() - 1);
			
			//stackPane.getChildren().remove(cards.size());
			
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
			dragAndDrop.createDraggableCardView(topCard, this);
			topStack.clear();
			topStack.add(topCard);
		}
	}

	// Update the GUI
	public void updateStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}
}
