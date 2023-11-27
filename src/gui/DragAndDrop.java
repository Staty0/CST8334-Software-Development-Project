package gui;

import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import model.Card;

public class DragAndDrop {
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
