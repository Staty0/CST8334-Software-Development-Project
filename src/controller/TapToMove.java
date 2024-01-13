package controller;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Card;

public class TapToMove implements EventHandler<MouseEvent> {
	private Pile selfPileController;
	private Tableau[] tableauPiles;
	private Foundation[] foundationPiles;

	public void setSelfPileController(Pile selfPileController) {
		this.selfPileController = selfPileController;
	}

	public void setTableauPiles(Tableau[] tableauPiles) {
		this.tableauPiles = tableauPiles;
	}

	public void setFoundationPiles(Foundation[] foundationPiles) {
		this.foundationPiles = foundationPiles;
	}

	@Override
	public void handle(MouseEvent arg0) {
		//This code's if statements can likely be combined for readability, I think
		if (arg0.getClickCount() == 2) {
			List<Card> cards = selfPileController.getCards();
			if (cards.size() > 0) {
				if (selfPileController instanceof Tableau) {
					Card topCard = cards.get(cards.size() - 1);
					for (int f = 0; f < foundationPiles.length; f++) {
						Foundation target = foundationPiles[f];
						if (target.canAddCard(topCard, 1)) {
							target.addCard(selfPileController.removeTopCard(), 1);
							selfPileController.updateDragNDrop();
							target.updateDragNDrop();
							return;
						}
					}
					for (int i = 0; i < cards.size(); i++) {
						Card currentCard = cards.get(i);
						if (currentCard.isFaceUp()) {
							for (int f = 0; f < tableauPiles.length; f++) {
								Tableau target = tableauPiles[f];
								if (target.canAddCard(currentCard, cards.size() - i)) {
									int trackCardsAdded = 0;
									for (int t = i; t < cards.size(); t++) {
										target.addCard(cards.get(t), cards.size() - t);
										trackCardsAdded += 1;
									}
									for (int t = 0; t < trackCardsAdded; t++) {
										selfPileController.removeTopCard();
									}
									selfPileController.updateDragNDrop();
									target.updateDragNDrop();
									return;
								}
							}
						}
					}
				} else {
					Card cardToMove = cards.get(cards.size() - 1);
					for (int f = 0; f < foundationPiles.length; f++) {
						Foundation target = foundationPiles[f];
						if (target.canAddCard(cardToMove, 1)) {
							target.addCard(selfPileController.removeTopCard(), 1);
							selfPileController.updateDragNDrop();
							target.updateDragNDrop();
							return;
						}
					}
					for (int f = 0; f < tableauPiles.length; f++) {
						Tableau target = tableauPiles[f];
						if (target.canAddCard(cardToMove, 1)) {
							target.addCard(selfPileController.removeTopCard(), 1);
							selfPileController.updateDragNDrop();
							target.updateDragNDrop();
							return;
						}
					}
				}
			}
		}
	}
}
