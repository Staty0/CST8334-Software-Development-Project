package controller;

import java.io.IOException;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Card;
import model.ConfigReader;
import model.Deck;

public class GUIController {
	@FXML
	private StackPane foundation1, foundation2, foundation3, foundation4;
	@FXML
	private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8;
	@FXML
	private StackPane stock;
	@FXML
	private StackPane talon;
	@FXML
	private GridPane gridPane;

	private Deck deck;
	private CardDragAndDrop dragAndDrop = CardDragAndDrop.getInstance();;

	@FXML
	private Button new_game;

	@FXML
	private Text scoreNumber;

	@FXML
	private Text timerText;
	private Timeline timeline;
	private int seconds = 0, minutes = 0;

	// initialize the GUI controller
	public void initialize() {
		StackPane[] tableauPiles = { tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8 };
		StackPane[] foundationPiles = { foundation1, foundation2, foundation3, foundation4 };

		deck = new Deck();
		deck.shuffle();

		// start the timer
		setupTimer();
		timeline.play();

		gridPane.setStyle("-fx-background-color:" + ConfigReader.getBackgroundColour());

		// Fill the tableau piles
		// the logic is that the first tableau pile has 1 card, the second has 2 cards,
		// the third has 3 cards, and so on
		for (int i = 0; i < 7; i++) {
			Tableau tableau = new Tableau();
			List<Card> cards = deck.deal(i + 1);
			tableau.setCardList(cards);
			tableau.setStackPane(tableauPiles[i]);
			tableau.updateStackView();
			tableau.flipTopCard();
			dragAndDrop.setupDropTarget(tableauPiles[i], tableau);
		}

		// Setup the foundation piles
		for (int i = 0; i < 4; i++) {
			Foundation foundation = new Foundation();
			foundation.setStackPane(foundationPiles[i]);
			foundation.updateStackView();
			dragAndDrop.setupDropTarget(foundationPiles[i], foundation);
		}

		// Setup the talon pile
		TalonClickEvent talonClick = new TalonClickEvent();
		Talon talonClass = new Talon();
		talonClick.setTalon(talonClass);

		talonClass.setCardList(deck.dealAll());
		talonClass.setStackPane(talon);
		talon.setOnMouseClicked(talonClick);
		talonClass.updateStackView();

		// Setup the stock pile
		Stock stockClass = new Stock();
		talonClick.setStock(stockClass);
		stockClass.setStackPane(stock);
		stockClass.updateStackView();

		// Update the score when the score changes
		ScoreManager.getInstance().setScoreChangeListener(newScore -> {
			scoreNumber.setText(String.valueOf(newScore));
		});
	}

	// Start a new game button
	public void newGame(ActionEvent event) {
		try {
			Parent gui = FXMLLoader.load(getClass().getResource("/gui/gui.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// load the css file
			gui.getStylesheets().add(getClass().getResource("/gui/gui.css").toExternalForm());
			stage.setScene(new Scene(gui));
			stage.centerOnScreen();
			stage.show();
			timeline.play();
			// reset the score
			ScoreManager.getInstance().resetScore();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// For the timer
	// timeline will be used to update the timer every second so does the socre logic
	private void setupTimer() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			seconds++;
			if (seconds == 60) {
				minutes++;
				seconds = 0;
			}
			timerText.setText(String.format("Time: %02d:%02d", minutes, seconds));

			// - 2 points for 10 seconds
			if (seconds % 10 == 0) {
				ScoreManager.getInstance().addScore(-2);
				System.out.println("Time - 2 points");
			}

		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
	}

}
