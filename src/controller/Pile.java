package controller;

import java.util.ArrayList;
import java.util.List;

import gui.CardStack;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Card;

public abstract class Pile {
	private int xOffset = 0;
	private int yOffset = 0;
    protected List<Card> cards = new ArrayList<Card>(); ;
    protected StackPane stackPane;
    CardStack graphics = CardStack.getInstance();
    protected CardDragAndDrop dragAndDrop = CardDragAndDrop.getInstance();
    
    public void setStackPane(StackPane stackPane) {
		this.stackPane = stackPane;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setCardList(List<Card> cards) {
		this.cards = cards;
	}

    public List<Card> getCards() {
        return cards;
    }

    public boolean addCard(Card card) {
        if (canAddCard(card)) {
        	
        	if (!cards.isEmpty()) {
        		Card pastTopCard = cards.get(cards.size() - 1);
        		dragAndDrop.makeNonDraggable(pastTopCard);
        	}
        	
            cards.add(card);
            
            //Update the drag and drop to reference this pile
            dragAndDrop.createDraggableCardView(card, this);
            
            // Attempted render code update
            
            //ImageView layeredImageView = card.getImageView();
			//layeredImageView.setTranslateX(xOffset * cards.size() - 1);
			//layeredImageView.setTranslateY(yOffset * cards.size() - 1); 
			//stackPane.getChildren().add(layeredImageView);
            
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
        	//stackPane.getChildren().remove(cards.size());
            return cards.remove(cards.size() - 1);
        }
        return null; // Pile is empty
    }
    
    abstract public void updateStackView();
}
