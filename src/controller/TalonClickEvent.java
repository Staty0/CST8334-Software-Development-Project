package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.input.MouseEvent;
import model.Card;

public class TalonClickEvent implements javafx.event.EventHandler<MouseEvent> {
	private Talon talon;
	private Stock stock;
	private CardDragAndDrop dragAndDrop = CardDragAndDrop.getInstance();

	public void setTalon(Talon talon) {
		this.talon = talon;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public void handle(MouseEvent arg0) {
		Card card = talon.removeTopCard();
		if (card != null) {
			stock.addCard(card);
			card.flip();
			List<Card> listAdapter = new ArrayList<Card>(Arrays.asList(card));
			dragAndDrop.createDraggableCardStack(listAdapter, stock);
			talon.updateStackView();
			stock.updateStackView();
		} else {
			List<Card> cardList = stock.drawAll();
			for (Card cards : cardList) {
	            cards.flip();
	        }
			talon.setCardList(cardList);
			talon.updateStackView();
			stock.updateStackView();
		}
	}
}
