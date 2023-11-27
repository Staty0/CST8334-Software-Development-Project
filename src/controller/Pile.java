package controller;

import java.util.List;

import gui.CardStack;
import model.Card;

public abstract class Pile {
    public List<Card> cards;
    
    public void setCardList(List<Card> cards) {
		this.cards = cards;
	}


	CardStack graphics = CardStack.getInstance();

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        if (canAddCard(card)) {
            cards.add(card);
        } else {
            // Handle invalid move (e.g., show a message, log, etc.)
            System.out.println("Invalid move");
        }
    }

    abstract boolean canAddCard(Card card);
    
    
    public Card removeTopCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null; // Pile is empty
    }
}
