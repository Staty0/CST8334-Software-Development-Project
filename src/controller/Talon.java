package controller;

import model.Card;

public class Talon extends Pile {
	
	private int xOffset = 2;
	private int yOffset = 0;
	
	
	public Talon(boolean isVegasMode) {
		super(isVegasMode);
	}

	@Override
	boolean canAddCard(Card card, int count) {
		return false;
	}

	@Override
	public void updateStackView() {
		stackPane.getChildren().clear();
		graphics.stackGenetator(stackPane, cards, xOffset, yOffset);
	}
}
