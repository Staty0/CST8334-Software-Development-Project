package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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
	private Tableau[] tableauPilesControllers = new Tableau[7];
	private Foundation[] foundationPilesControllers = new Foundation[4];
	
	private Talon talonClass = new Talon();
	private Stock stockClass = new Stock();

	// initialize the GUI controller
	public void initialize() {
		StackPane[] tableauPiles = { tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8 };
		StackPane[] foundationPiles = { foundation1, foundation2, foundation3, foundation4 };

		gridPane.setStyle("-fx-background-color:" + ConfigReader.getBackgroundColour());

		//Create the controllers and attach them to the StackPane
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
		
		newGame();
	}

	public void newGame() {
		deck = new Deck();
		deck.shuffle();
		
		// Fill the tableau piles
		// the logic is that the first tableau pile has 1 card, the second has 2 cards,
		// the third has 3 cards, and so on
		for (int i = 0; i < 7; i++) {
			Tableau tableau = tableauPilesControllers[i];
			List<Card> cards = deck.deal(i + 1);
			tableau.setCardList(cards);
			tableau.updateStackView();
			tableau.flipTopCard();
		}

		for (int i = 0; i < 4; i++) {
			foundationPilesControllers[i].setCardList(new ArrayList<Card>()); 
			foundationPilesControllers[i].updateStackView();
		}

		talonClass.setCardList(deck.dealAll());
		talonClass.updateStackView();

		stockClass.updateStackView();
	}

	// back to main menu
	public void backToMenu(ActionEvent event) {
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
}
