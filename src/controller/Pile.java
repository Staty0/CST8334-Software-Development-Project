package controller;

import java.util.ArrayList;
import java.util.List;

import gui.CardStack;
import javafx.scene.layout.StackPane;
import model.Card;

public abstract class Pile {
    protected List<Card> cards = new ArrayList<Card>(); ;
    protected StackPane stackPane;
    
    public void setStackPane(StackPane stackPane) {
		this.stackPane = stackPane;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setCardList(List<Card> cards) {
		this.cards = cards;
	}

	CardStack graphics = CardStack.getInstance();

    public List<Card> getCards() {
        return cards;
    }

    public boolean addCard(Card card) {
        if (canAddCard(card)) {
            cards.add(card);
            return true;
        } else {
            // Handle invalid move (e.g., show a message, log, etc.)
            System.out.println("Invalid move");
            return false;
        }
    }

    abstract boolean canAddCard(Card card);
    
    public Card removeTopCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null; // Pile is empty
    }
    
    abstract public void getStackView();
}
