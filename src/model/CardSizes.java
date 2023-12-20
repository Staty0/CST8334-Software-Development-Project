package model;

public enum CardSizes {
	
	NORMAL(new int[]{120, 180}), SMALL(new int[]{90, 135}), LARGE(new int[]{150, 225});
	
    private final int[] value;

    CardSizes(int[] value) {
        this.value = value;
    }

	public int[] getValue() {
        return value;
    }
}
