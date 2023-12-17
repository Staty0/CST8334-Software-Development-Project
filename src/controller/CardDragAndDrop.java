package controller;

import java.util.List;

import gui.CardStack;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import model.Card;
import model.ConfigReader;

public class CardDragAndDrop {
	private static CardDragAndDrop single_instance = null;
	private int[] sizeSetting = ConfigReader.getCardSize();
	private int xCenter = sizeSetting[0] / 2;
	private int yCenter = sizeSetting[1] / 2;

	private CardDragAndDrop() {
	}

	public static synchronized CardDragAndDrop getInstance() {
		if (single_instance == null)
			single_instance = new CardDragAndDrop();

		return single_instance;
	}

	public ImageView createDraggableCardView(Card card, Pile currentPile) {
		ImageView cardView = card.getImageView();
		cardView.setUserData(card);

		cardView.setOnDragDetected(event -> {
			Dragboard db = cardView.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.put(DataFormat.IMAGE, cardView);
			db.setContent(content);
			// Put the image being dragged centered behind the cursor
			db.setDragView(cardView.snapshot(null, null), xCenter, yCenter);

			// Hide the card being dragged
			cardView.setVisible(false);
			
			event.consume();
		});

		cardView.setOnDragDone(event -> {
			if (!event.isAccepted()) {
				cardView.setVisible(true);
			} else {
				cardView.setVisible(true);
				currentPile.removeTopCard();
			}
			event.consume();
		});
		return cardView;
	}

	public ImageView createDraggableCardStack(List<Card> cardStack, Pile currentPile) {
		int yOffest = 20;
		CardStack stackCreator = CardStack.getInstance();
		StackPane stackPane = new StackPane();
		stackCreator.stackSnapshotGenetator(stackPane, cardStack, 0, yOffest);

		Card bottomCard = cardStack.get(0);
		ImageView bottomCardView = bottomCard.getImageView();
		bottomCardView.setUserData(bottomCard);

		bottomCardView.setOnDragDetected(event -> {
			Dragboard db = bottomCardView.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.put(DataFormat.IMAGE, bottomCardView);
			db.setContent(content);
			
			db.setDragView(stackPane.snapshot(null, null), xCenter, yCenter);

			// Hide the card being dragged
			cardStack.forEach(stackedCard -> stackedCard.getImageView().setVisible(false));
			
			event.consume();
		});

		bottomCardView.setOnDragDone(event -> {
			if (!event.isAccepted()) {
				cardStack.forEach(stackedCard -> stackedCard.getImageView().setVisible(true));
			} else {
				cardStack.forEach(stackedCard -> stackedCard.getImageView().setVisible(true));
				currentPile.removeTopCard();
			}
			event.consume();
		});
		return bottomCardView;
	}

	// set the drop event
	public void setupDropTarget(StackPane targetPane, Pile targetPile) {
		targetPane.setOnDragOver(event -> {
			if (event.getGestureSource() != targetPane) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		targetPane.setOnDragDropped(event -> {
			boolean success = false;
			// Get the card and image view from the above methods
			ImageView cardView = (ImageView) event.getGestureSource();
			Card draggedCard = (Card) cardView.getUserData();
			
			success = targetPile.addCard(draggedCard);
			
			if (success) {
				cardView.setVisible(true);
				event.setDropCompleted(true);
			} else {
				// If the card couldn't be added
				System.out.println("Card could not be added to the pile.");
				event.setDropCompleted(false);
			}
			event.setDropCompleted(success);
			event.consume();
		});
	}

	public void makeNonDraggable(Card card) {
		ImageView cardView = card.getImageView();
		cardView.setOnDragDetected(null);
		cardView.setOnDragDone(null);
	}
}
