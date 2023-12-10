package controller;

import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import model.Card;

public class CardDragAndDrop {
	private static CardDragAndDrop single_instance = null;

	private CardDragAndDrop() {
	}

	public static synchronized CardDragAndDrop getInstance() {
		if (single_instance == null)
			single_instance = new CardDragAndDrop();

		return single_instance;
	}


	// create a draggable card view
	public ImageView createDraggableCardView(Card card, Pile currentPile) {
		ImageView cardView = card.getImageView();
		cardView.setUserData(card);

		cardView.setOnDragDetected(event -> {
			Dragboard db = cardView.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.put(DataFormat.IMAGE, cardView);
			db.setContent(content);
			// Put the image being dragged centered behind the cursor
			db.setDragView(cardView.snapshot(null, null), 60, 90);

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
				currentPile.updateStackView();
			}
			event.consume();
		});
		return cardView;
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
				// Update the GUI on success
				cardView.setVisible(true);
				targetPile.updateStackView();
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
}
