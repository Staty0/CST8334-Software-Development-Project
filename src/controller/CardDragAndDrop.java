package controller;

import java.util.Iterator;
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
	private Pile lastTarget;

	private CardDragAndDrop() {
	}

	public static synchronized CardDragAndDrop getInstance() {
		if (single_instance == null)
			single_instance = new CardDragAndDrop();

		return single_instance;
	}

	public ImageView createDraggableCardStack(List<Card> cardStack, Pile currentPile) {
		int yOffest = 20;

		CardStack stackCreator = CardStack.getInstance();
		StackPane stackPane = new StackPane();
		stackCreator.stackSnapshotGenetator(stackPane, cardStack, 0, yOffest);

		Card bottomCard = cardStack.get(0);
		ImageView bottomCardView = bottomCard.getImageView();
		bottomCardView.setUserData(cardStack);

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
				event.consume();

				System.out.println("Drag and drop failed");

			} else {
				cardStack.forEach(stackedCard -> stackedCard.getImageView().setVisible(true));
				for (int i = 0; i < cardStack.size(); i++) {
					currentPile.removeTopCard();
				}
				lastTarget.updateDragNDrop();
				event.consume();
				currentPile.updateDragNDrop();

				// test for the score system
				System.out.println("Drag and drop success");
				ScoreManager.getInstance().addScore(1);
				event.consume();
				System.out.println(ScoreManager.getInstance().getScore());
				
			}
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
			List<Card> draggedCards = (List<Card>) cardView.getUserData();

			success = targetPile.canAddCard(draggedCards.get(0), draggedCards.size());

			if (success) {
				Iterator<Card> iterator = draggedCards.iterator();
				while (iterator.hasNext()) {

					targetPile.addCard(iterator.next(), draggedCards.size());
				}
				lastTarget = targetPile;
				event.setDropCompleted(true);
			} else {
				// If the card couldn't be added
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
