package model;

import gui.GraphicFromSheet;
import javafx.scene.image.ImageView;

public class Card {
	private final Suit suit;
	private final Rank rank;
	private boolean faceUp;

	private final ImageView frontImageView;
	private final ImageView backImageView;

	// Constructor
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
		this.faceUp = false;

		// Create front and back ImageViews
		this.frontImageView = GraphicFromSheet.getInstance().cardGet(suit.getValue(), rank.getValue());
		this.backImageView = GraphicFromSheet.getInstance().cardGet(5, 3); 
		backImageView.setVisible(false); // Initially, the back side is not visible
	}
	
	
	public boolean isRed() {
	    return suit == Suit.DIAMONDS || suit == Suit.HEARTS;
	}

	public boolean isBlack() {
	    return suit == Suit.CLUBS || suit == Suit.SPADES;
	}

	public boolean isFaceUp() {
		return faceUp;
	}

	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}

	public void flip() {
		faceUp = !faceUp;
		frontImageView.setVisible(faceUp);
		backImageView.setVisible(!faceUp);
	}


	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	public ImageView getFrontImageView() {
		return frontImageView;
	}

	public ImageView getBackImageView() {
		return backImageView;
	}
}

