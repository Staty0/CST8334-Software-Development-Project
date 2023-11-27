package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private List<Card> cards;

	public Deck() {
		initializeDeck();
	}

	// Initialize the deck with all possible combinations of suits and ranks
	private void initializeDeck() {
		cards = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards.add(new Card(suit, rank));
			}
		}
	}

	// Shuffle the deck
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	// Deal one card
    public Card deal() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        } else {
            return null;
        }
    }

    // Deal a list of cards
	public List<Card> deal(int numCards) {
        List<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < numCards && !cards.isEmpty(); i++) {
            dealtCards.add(cards.remove(0));
        }
        return dealtCards;
    }
	
	// Check if the deck is empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }


}
