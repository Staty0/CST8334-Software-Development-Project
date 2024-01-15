package controller;

/**
 * This class manages the score for the game.
 */

public class ScoreManager {

	private static ScoreManager single_instance = null;
	private int score = 0;
	private ScoreChangeListener scoreChangeListener;

	private ScoreManager() {
	}

	// Observer Pattern to notify the GUI when the score changes
	public interface ScoreChangeListener {
		void onScoreChanged(int newScore);
	}

	// Singleton pattern to ensure only one instance of this class is created
	public static synchronized ScoreManager getInstance() {
		if (single_instance == null)
			single_instance = new ScoreManager();
		return single_instance;
	}

	// Registering Observers to listen to the score changes
	public void setScoreChangeListener(ScoreChangeListener listener) {
		this.scoreChangeListener = listener;
	}

	public void addScore(int score) {
		this.score += score;
		if (scoreChangeListener != null) {
			scoreChangeListener.onScoreChanged(this.score);
		}
	}

	public Integer getScore() {
		return score;
	}

	public void resetScore() {
		score = 0;
		if (scoreChangeListener != null) {
			scoreChangeListener.onScoreChanged(this.score);
		}
	}


	// Add the logic to calculate the initial ante in Vegas mode
	public void setVegasAnte() {
		addScore(-52);
	}



}
