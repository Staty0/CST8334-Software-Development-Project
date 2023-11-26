package com.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.print.Collation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

public class GameController {
  @FXML
  private StackPane foundationPiles1, foundationPiles2, foundationPiles3, foundationPiles4;
  @FXML
  private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8;
  @FXML
  private StackPane stock;

  // initialize the deck of cards
  private Stack<Card> deck = createShuffledDeck();

  // initialize the graphicFromSheet class
  private GraphicFromSheet graphicFromSheet = new GraphicFromSheet();

  private Map<ImageView, StackPane> imageViewToPaneMap = new HashMap<>();

  // initialize the game controller from gui fxml AnchorPane
  // fx:controller="com.example.GameController"
  public void initialize() {
    System.out.println("GameController initialized");

    StackPane[] tableauPiles = { tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8 };

    for (StackPane tableauPile : tableauPiles) {
      setupDropTarget(tableauPile);
    }

    // Fill the tableau piles
    // the logics is that the first tableau pile has 1 card, the second has 2 cards,
    // the third has 3 cards, and so on
    // so fill the tableau piles with the cards from the deck
    // the deck.pop() will remove the card from the deck and return it so we can add
    // it to the tableau piles
    // does not mean that the deck is gone, just being used to fill the tableau
    // piles
    for (int i = 0; i < tableauPiles.length; i++) {
      for (int j = 0; j <= i; j++) {
        if (!deck.isEmpty()) {
          Card card = deck.pop();
          ImageView cardView;

          // only the first card in the tableau pile is face up
          // j != 1 means that if the card is not the first card in the tableau pile will
          // show the back of the card
          if (j != i) {
            cardView = graphicFromSheet.cardGet("back", 3);
          } else {
            cardView = graphicFromSheet.cardGet(card.getSuit(), card.getNumber());
            cardView = createDraggableCardView(card, tableauPiles[i]); // make the card draggable

          }
          cardView.setTranslateY(j * 30); // move the card down 30 pixels
          tableauPiles[i].getChildren().add(cardView);
        }
      }
    }

    // Fill the stock after the tableau piles are filled
    while (!deck.isEmpty()) {
      Card card = deck.pop();

      // test what is the id of the card in stock
      System.out.println(card.getId());

      ImageView cardView = graphicFromSheet.cardGet(card.getSuit(), card.getNumber());
      cardView = createDraggableCardView(card, stock);

      stock.getChildren().add(cardView);
    }
  }

  // Create a shuffled deck of cards
  private Stack<Card> createShuffledDeck() {
    Stack<Card> deck = new Stack<>();

    for (int i = 1; i <= 13; i++) {
      deck.push(new Card("clubs", i));
      deck.push(new Card("diamonds", i));
      deck.push(new Card("hearts", i));
      deck.push(new Card("spades", i));
    }

    // Shuffle the deck using the Collections class
    Collections.shuffle(deck);
    return deck;
  }

  // create a draggable card view
  // only the first card in the tableau pile is face up can be dragged
  private ImageView createDraggableCardView(Card card, StackPane currentPile) {
    ImageView cardView = graphicFromSheet.cardGet(card.getSuit(), card.getNumber());

    cardView.setOnDragDetected(event -> {
      Dragboard db = cardView.startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(card.getId());
      db.setContent(content);

      // test what is the id of the card being dragged
      System.out.println(card.getId());

      // Put the image being dragged centered behind the cursor
      // ImageView dragCard = graphicFromSheet.cardGet(card.getSuit(),
      // card.getNumber());
      db.setDragView(cardView.snapshot(null, null), 60, 90);

      // Hide the card being dragged
      cardView.setVisible(false);

      // Set the source of the drag event to the current pile
      currentPile.setUserData(cardView);

      event.consume();

      imageViewToPaneMap.put(cardView, currentPile);
    });

    cardView.setOnDragDone(event -> {
      if (!event.isAccepted()) {
        currentPile.getChildren().add(cardView);
        cardView.setVisible(true);
      }
      imageViewToPaneMap.remove(cardView);
      event.consume();
    });

    return cardView;
  }

  // set the drop event
  private void setupDropTarget(StackPane targetPane) {
    targetPane.setOnDragOver(event -> {
      if (event.getGestureSource() != targetPane && event.getDragboard().hasString()) {
        event.acceptTransferModes(TransferMode.MOVE);
      }
      event.consume();
    });

    targetPane.setOnDragDropped(event -> {
      Dragboard db = event.getDragboard();
      boolean success = false;
      if (db.hasString()) {
        // gettint the cardView from the source
        ImageView cardView = (ImageView) event.getGestureSource();
        StackPane sourcePane = imageViewToPaneMap.get(cardView);

        if (sourcePane != null) {
          // add the cardView to the targetPane
          targetPane.getChildren().add(cardView);
          cardView.setVisible(true); // show the cardView
          // cardView.setTranslateY(targetPane.getChildren().size() * 30);
          success = true;

          // remove the cardView from the sourcePane
          // sourcePane.setUserData(null);
          // imageViewToPaneMap.remove(cardView);
          event.setDropCompleted(success);
          event.consume();
        }
      }
      event.setDropCompleted(success);
      event.consume();
    });

  }

}
