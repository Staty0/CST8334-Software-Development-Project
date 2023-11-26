package com.example;

import java.io.InputStream;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GraphicFromSheet {

  public ImageView cardGet(String suit, int number) {
    // Load the card sheet image
    Image cardSheet = new Image("card-deck-161536_1920.png");

    // Map the suit to a number
    int suitNumber = mapSuitToNumber(suit);

    // Calculate row and column
    // Set which row and column to load from
		// Suits are as follows: 0 is Clubs, 1 is Diamonds, 2 is Hearts, 3 is Spades
		// Column 4 has two blank cards on 1 and 2, 3 has the card back
    double row = suitNumber;
    double column = number - 1;

    // Define the dimensions of a single card in the card sheet
    double cardWidth = 147; // Width of a single card
    double cardHeight = 230; // Height of a single card

    // Define the coordinates of the region you want to display
    double startX = column * cardWidth;
    double startY = row * cardHeight;

    // Create an ImageView and crop the image to the part of it we need
    ImageView imageView = new ImageView(cardSheet);
    imageView.setViewport(new Rectangle2D(startX, startY, cardWidth, cardHeight));

    // Resize the card to the size we need
    imageView.setFitWidth(120);
    imageView.setFitHeight(180);

    // Set the ID for the ImageView
    imageView.setId(new Card(suit, number).getId());

    // TODO: Implement addDragAndDrop method to handle drag and drop functionality

    return imageView;
  }

  private int mapSuitToNumber(String suit) {
    switch (suit.toLowerCase()) {
      case "clubs":
        return 0;
      case "diamonds":
        return 1;
      case "hearts":
        return 2;
      case "spades":
        return 3;
      default:
        return 4; // Default case for back of the card
    }
  }
}
