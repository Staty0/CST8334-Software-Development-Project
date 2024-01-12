package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gui.CardStack;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Card;

public abstract class Pile {
	private int xOffset = 0;
	private int yOffset = 0;
	protected List<Card> cards = new ArrayList<Card>();;
	protected StackPane stackPane;
	CardStack graphics = CardStack.getInstance();
	protected CardDragAndDrop dragAndDrop = CardDragAndDrop.getInstance();

	public void setStackPane(StackPane stackPane) {
		this.stackPane = stackPane;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setCardList(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	public boolean addCard(Card card, int count) {
		if (canAddCard(card, count)) {

			if (!cards.isEmpty()) {
				Card pastTopCard = cards.get(cards.size() - 1);
				dragAndDrop.makeNonDraggable(pastTopCard);
			}

			cards.add(card);

			// render code

			ImageView layeredImageView = card.getImageView();
			layeredImageView.setTranslateX(xOffset * (cards.size() - 1));
			layeredImageView.setTranslateY(yOffset * (cards.size() - 1));
			stackPane.getChildren().add(layeredImageView);

			// 10 points for each card moved to a fondation pile
			ScoreManager.getInstance().addScore(10);
			System.out.println("Fondation Pile + 10 points");

			return true;
		} else {
			// Handle invalid move (e.g., show a message, log, etc.)
			return false;
		}
	}

	abstract boolean canAddCard(Card card, int count);

	public Card removeTopCard() {
		if (!cards.isEmpty()) {
			return cards.remove(cards.size() - 1);
		}
		return null; // Pile is empty
	}

	public void updateDragNDrop() {
		// Update the top card's drag and drop to reference it's new pile
		if (!cards.isEmpty()) {
			Card topCard = cards.get(cards.size() - 1);
			List<Card> listAdapter = new ArrayList<Card>(Arrays.asList(topCard));
			dragAndDrop.createDraggableCardStack(listAdapter, this);

		}
	}

	// Update the GUI
	public void updateStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}
}
