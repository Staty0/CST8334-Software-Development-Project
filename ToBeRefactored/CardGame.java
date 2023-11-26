package controller;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.Deck;

public class CardGame {
    private Deck deck;
    private List<Pile> tableau;
    private List<Pile> foundations;
    private Pile deckPile; // Add a field for the deck pile

    public CardGame() {
        initializeGame();
    }

    private void initializeGame() {
        deck = new Deck();
        deck.shuffle();

        tableau = new ArrayList<>();
        foundations = new ArrayList<>();
        deckPile = new Pile(false); // face-up pile for the deck

        // Initialize tableau piles
        for (int i = 0; i < 7; i++) {
            Pile pile = new Pile(true); // face-down pile
            List<Card> cards = deck.deal(i + 1);
            pile.getCards().addAll(cards);
            tableau.add(pile);
        }

        // Initialize foundation piles
        for (int i = 0; i < 4; i++) {
            foundations.add(new Pile(false)); // face-up pile
        }

        // Populate the deck pile
        while (!deck.isEmpty()) {
            deckPile.addCard(deck.deal());
        }
    }

    public List<Pile> getTableau() {
        return tableau;
    }

    public Pile getDeckPile() {
        return deckPile;
    }

    public List<Pile> getFoundations() {
        return foundations;
    }

}