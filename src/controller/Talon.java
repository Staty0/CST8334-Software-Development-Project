package controller;

import model.Card;

public class Talon extends Pile {
	private int xOffset = 2;
	private int yOffset = 0;

	@Override
	boolean canAddCard(Card card) {
		return false;
	}

	@Override
	public void updateStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}
}
