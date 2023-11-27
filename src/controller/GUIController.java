package controller;

import java.util.List;

import gui.DragAndDrop;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import model.Card;
import model.Deck;

public class GUIController {
	@FXML
	private StackPane foundation1, foundation2, foundation3, foundation4;
	@FXML
	private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8;
	@FXML
	private StackPane stock;
	@FXML
	private StackPane talon;

	private Deck deck;
	private DragAndDrop dragAndDrop = DragAndDrop.getInstance();;

	// initialize the GUI controller
	public void initialize() {
		StackPane[] tableauPiles = { tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8 };
		StackPane[] foundationPiles = { foundation1, foundation2, foundation3, foundation4 };

		deck = new Deck();
		deck.shuffle();

		// Fill the tableau piles
		// the logic is that the first tableau pile has 1 card, the second has 2 cards,
		// the third has 3 cards, and so on
		for (int i = 0; i < 7; i++) {
			Tableau tableau = new Tableau();
			List<Card> cards = deck.deal(i + 1);
			tableau.setCardList(cards);
			tableau.flipTopCard();
			tableau.setStackPane(tableauPiles[i]);
			tableau.getStackView();
			dragAndDrop.setupDropTarget(tableauPiles[i], tableau);
		}

		for (int i = 0; i < 4; i++) {
			Foundation foundation = new Foundation();
			foundation.setStackPane(foundationPiles[i]);
			foundation.getStackView();
			dragAndDrop.setupDropTarget(foundationPiles[i], foundation);
		}
	}
}