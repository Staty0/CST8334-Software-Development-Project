package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
	@FXML
	private Button new_game;
	@FXML
	private Button back_to_menu;
	@FXML
	private Button pause_button;
	@FXML
	private Text scoreNumber;
	@FXML
	private Text timerText;
	private Timeline timeline;
	private int seconds = 0, minutes = 0;

	private Deck deck;
	private CardDragAndDrop dragAndDrop = CardDragAndDrop.getInstance();;
	private Tableau[] tableauPilesControllers = new Tableau[7];
	private Foundation[] foundationPilesControllers = new Foundation[4];

	private Talon talonClass = new Talon();
	private Stock stockClass = new Stock();

	private boolean isPaused = false;

	// initialize the GUI controller
	public void initialize() {
		StackPane[] tableauPiles = { tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8 };
		StackPane[] foundationPiles = { foundation1, foundation2, foundation3, foundation4 };

		gridPane.setStyle("-fx-background-color:" + ConfigReader.getBackgroundColour());

		ExecutorService executorService = Executors.newCachedThreadPool();
		CountDownLatch countDownLatch = new CountDownLatch(2);

		// Create the controllers and attach them to the StackPane
		executorService.submit(() -> {
			for (int i = 0; i < 7; i++) {
				Tableau tableau = new Tableau();
				tableau.setStackPane(tableauPiles[i]);
				tableauPilesControllers[i] = tableau;
				dragAndDrop.setupDropTarget(tableauPiles[i], tableau);
				TapToMove clickListener = new TapToMove();
				clickListener.setFoundationPiles(foundationPilesControllers);
				clickListener.setTableauPiles(tableauPilesControllers);
				clickListener.setSelfPileController(tableau);
				tableauPiles[i].setOnMouseClicked(clickListener);
			}
			countDownLatch.countDown();
		});

		executorService.submit(() -> {
			for (int i = 0; i < 4; i++) {
				Foundation foundation = new Foundation();
				foundation.setStackPane(foundationPiles[i]);
				foundationPilesControllers[i] = foundation;
				dragAndDrop.setupDropTarget(foundationPiles[i], foundation);
				TapToMove clickListener = new TapToMove();
				clickListener.setFoundationPiles(foundationPilesControllers);
				clickListener.setTableauPiles(tableauPilesControllers);
				clickListener.setSelfPileController(foundation);
				foundationPiles[i].setOnMouseClicked(clickListener);
			}
			countDownLatch.countDown();
		});

		// Setup the talon pile
		TalonClickEvent talonClick = new TalonClickEvent();

		talonClick.setTalon(talonClass);
		talonClass.setStackPane(talon);
		talon.setOnMouseClicked(talonClick);

		// Setup the stock pile
		talonClick.setStock(stockClass);
		stockClass.setStackPane(stock);
		TapToMove clickListener = new TapToMove();
		clickListener.setFoundationPiles(foundationPilesControllers);
		clickListener.setTableauPiles(tableauPilesControllers);
		clickListener.setSelfPileController(stockClass);
		stock.setOnMouseClicked(clickListener);

		setupTimer();

		// Update the score when the score changes
		ScoreManager.getInstance().setScoreChangeListener(newScore -> {
			scoreNumber.setText(String.valueOf(newScore));
		});

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executorService.shutdown();

		newGame();
	}

	public void newGame() {
		deck = new Deck();
		deck.shuffle();
		ScoreManager.getInstance().resetScore();

		// Fill the tableau piles
		// the logic is that the first tableau pile has 1 card, the second has 2 cards,
		// the third has 3 cards, and so on
		for (int i = 0; i < 7; i++) {
			Tableau tableau = tableauPilesControllers[i];
			List<Card> cards = deck.deal(i + 1);
			tableau.setCardList(cards);
			tableau.updateStackView();
			tableau.resetFirstTime();
			tableau.flipTopCard();
		}

		for (int i = 0; i < 4; i++) {
			foundationPilesControllers[i].setCardList(new ArrayList<Card>());
			foundationPilesControllers[i].updateStackView();
		}

		talonClass.setCardList(deck.dealAll());
		talonClass.updateStackView();

		stockClass.setCardList(new ArrayList<Card>());
		stockClass.resetPastThrough();
		stockClass.updateStackView();

		// Reset and then start the timer
		timeline.stop();
		seconds = 0;
		minutes = 0;
		timerText.setText("Time: 00:00");
		timeline.play();
	}

	// Button to start a new game
	public void newGame(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
		alert.setHeaderText("");
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				newGame();
			}
		});
	}

	// Pause button
	public void pauseGame(ActionEvent event) {
		isPaused = !isPaused;
		timeline.pause();
		showPausePopup();
	}

	private void showPausePopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Paused");
        alert.setHeaderText("");
        alert.setContentText("The game is paused");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOnCloseRequest(event -> {
        	timeline.play();
        });
        alert.showAndWait();
	}

	// Button to go back to the menu
	public void backToMenu(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
		alert.setContentText("");
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				try {
					Parent gui = FXMLLoader.load(getClass().getResource("/gui/welcomgui.fxml"));
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					// load the css file
					gui.getStylesheets().add(getClass().getResource("/gui/welcomgui.css").toExternalForm());
					stage.setScene(new Scene(gui));
					stage.centerOnScreen();
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// For the timer
	// timeline will be used to update the timer every second so does the score
	// logic
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
			}

		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
	}
}
