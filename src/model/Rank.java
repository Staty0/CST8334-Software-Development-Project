package model;


public enum Rank {

	//Rank of Cards: A (low), 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K (high)
	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
	EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

	private final int value;

	Rank(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}