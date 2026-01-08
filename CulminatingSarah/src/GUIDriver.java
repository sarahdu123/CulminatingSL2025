import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.animation.PauseTransition;
import java.util.Random;
import java.net.URL;
import java.util.Objects;


/**
 * JavaFX GUI for Battleship.
 * Each button represents one position on the board
 */

public class GUIDriver extends Application {
		private static final int SIZE = 8;
		
		private Board playerBoard;
		private Board computerBoard;
		
		private StackPane[][] playerCells;
		private StackPane[][] computerCells;
		
		private Label statusText;
		
		private boolean playerTurn = true;
		
		private Random rand = new Random();
		
		// Images
		private Image carrierImg, battleshipImg, cruiserImg, submarineImg, destroyerImg;
		private Image explosionImg, missImg, sunkImg, waterImg;
		
		
		
		@Override
		public void start(Stage stage) {
			System.out.println(getClass().getResource("water.png"));
			loadImages();
//			
//			URL url = getClass().getResource("water.png");
//			
//			if (url == null) {
//				System.out.println("Image not found!");
//			} else {
//				System.out.println("Image found");
//			}
			
//			Image picture = new Image(url.toExternalForm());
			
//			Image picture = new Image(getClass().getResourceAsStream("water.png"));
//			
//			ImageView pictureViewer = new ImageView(picture);

//			
			//Initialize boards
			playerBoard = new Board(SIZE);
			computerBoard = new Board(SIZE);
			
			playerCells = new StackPane[SIZE][SIZE];
			computerCells = new StackPane[SIZE][SIZE];
			
			placeAllShips(playerBoard);
			placeAllShips(computerBoard);
			
			GridPane playerGrid = createBoardGrid(playerCells, playerBoard, true); // player board (show ships)
			GridPane computerGrid = createBoardGrid(computerCells, computerBoard, false); // computer board (clickable)
			
			statusText = new Label("Your turn! Click on the computer's board.");
			statusText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			

			// Labels for boards
			VBox playerBox = new VBox(5, new Label("Your Board"), playerGrid);
			playerBox.setAlignment(Pos.CENTER);
			
			VBox computerBox = new VBox(5, new Label("Computer Board"), computerGrid);
			computerBox.setAlignment(Pos.CENTER);
			
			HBox boards = new HBox(50, playerBox, computerBox);
			boards.setAlignment(Pos.CENTER);
			
			VBox root = new VBox(20, boards, statusText);
			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20));
			
			
			//Initial update to show player's ships
			updateBoard(playerBoard, playerCells, true);
			updateBoard(computerBoard, computerCells, false);
			
			Scene scene = new Scene(root, 900, 500);
			stage.setTitle("Battleship: Player vs Computer");
			stage.setScene(scene);
			stage.show();
			
			
		
		}
		
		
		
		private Image loadImage(String filename) {
			return new Image(getClass().getResourceAsStream(filename));
		}
		
		private void loadImages() {
			carrierImg = loadImage("carrier.png");
			battleshipImg = loadImage("battleship.png");
			cruiserImg = loadImage("cruiser.png");
			submarineImg = loadImage("submarine.png");
			destroyerImg = loadImage("destroyer.png");
			
			explosionImg = loadImage("explosion.png");
			missImg = loadImage("miss.png");
			sunkImg = loadImage("smoke.gif");
			waterImg = loadImage("water.png");
		}
			
		// Places all 5 ships on the given board
		private void placeAllShips(Board board) {
			board.placeShipRandom(new Ship("Carrier", 5), rand);
			board.placeShipRandom(new Ship("Battleship", 4), rand);
			board.placeShipRandom(new Ship("Cruiser", 3), rand);
			board.placeShipRandom(new Ship("Submarine", 3), rand);
			board.placeShipRandom(new Ship("Destroyer", 2), rand);
		}
			
		//Creates a GridPane of buttons for a board
		private GridPane createBoardGrid(StackPane[][] cells, Board board, boolean showShips) {
			GridPane grid = new GridPane();
			grid.setHgap(2);
			grid.setVgap(2);

			for (int r = 0; r < SIZE; r++) {
				for (int c = 0; c < SIZE; c++) {
					StackPane cell = new StackPane();
					cell.setPrefSize(45, 45);
					cell.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
					
					if (!showShips) {
						final int row = r;
						final int col = c;
						cell.setOnMouseClicked(e -> playerShoots(row, col));
					}
					
					cells[r][c] = cell;
					grid.add(cell,  c,  r);
				
			}
		}
		return grid;
		}
			
		// Player fires at computer board
		private void playerShoots(int row, int col) {
			
			if (!playerTurn) return;
			
			String result = computerBoard.shootAt(row, col);
			updateBoard(computerBoard, computerCells, false);
			
			Ship hitShip = computerBoard.getShipAt(row, col);
			
			if (result.startsWith("Sunk")) {
				statusText.setText("You sunk the " + result.substring(5) + "!");
			} else if (result.startsWith("Hit")) {
				statusText.setText("You hit a ship! Go again.");
			} else if (result.equals("Miss")) {
				playerTurn = false;
				statusText.setText("You missed! Computer's turn...");
				computerTurnWithDelay();
			}
			
			if (computerBoard.allShipsSunk()) {
				statusText.setText("You win! All computer ships sunk!");
			}
		}
		
		private void computerTurnWithDelay() {
			PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(1.2));
			pause.setOnFinished(e -> computerTurn());
			pause.play();
		}
		
		// Computer fires at random untried square on player board
		private void computerTurn() {
			int row, col;
			String result;
			do {
				row = rand.nextInt(SIZE);
				col = rand.nextInt(SIZE);
				result = playerBoard.shootAt(row,  col);
				updateBoard(playerBoard, playerCells, true);
				
				if (result.startsWith("Sunk")) {
					statusText.setText("Computer sunk you " + result.substring(5) + "!");
				} 
				
				if (playerBoard.allShipsSunk()) {
					statusText.setText("Computer wins! All your ships are sunk!");
					return;
				}
			} while (result.startsWith("Hit") || result.startsWith("Sunk"));
		
			
			playerTurn = true;
			statusText.setText("Your turn!");
		}
			
			
		
	/**
	 * Updates the buttons display based on board state.
	 */
	
	private void updateBoard(Board board, StackPane[][] cells, boolean showShips) {
		
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				StackPane cell = cells[r][c];
				cell.getChildren().clear();
				
				int val = board.getGrid()[r][c];
				Ship ship = null;
				
				// Find ship at this coordinate
				for (Ship s : board.getShips()) {
					for (Coordinate pos : s.getPositions()) {
						if (pos.getRow() == r && pos.getCol() == c) {
							ship = s;
							break;
						}
					}
				}
			
				
				//Background
				ImageView bg = new ImageView(waterImg);
				bg.setFitWidth(45);
				bg.setFitHeight(45);
				cell.getChildren().add(bg);
				
				//Ship image
				if (val == Board.SHIP && showShips && ship != null) {
					ImageView shipImg = new ImageView(getShipImage(ship));
					shipImg.setFitWidth(45);
					shipImg.setFitHeight(45);
					cell.getChildren().add(shipImg);
				}
				
				//Hit
				if (val == Board.HIT && ship != null) {
					ImageView img = ship.isSunk() ? new ImageView(sunkImg) : new ImageView(explosionImg);
					img.setFitWidth(45);
					img.setFitHeight(45);
					cell.getChildren().add(img);
					
				}
				
				//Miss
				if (val == Board.MISS) {
					ImageView img = new ImageView(missImg);
					img.setFitWidth(45);
					img.setFitHeight(45);
					cell.getChildren().add(img);
				}
				
			}
		}
	}
	
	/**
	 * Return ship image based on name
	 */
	
	private Image getShipImage(Ship ship) {
		switch (ship.getName()) {
		case "Carrier": return carrierImg;
		case "Battleship": return battleshipImg;
		case "Cruiser": return cruiserImg;
		case "Submarine": return submarineImg;
		case "Destroyer": return destroyerImg;
		default: return waterImg;
		}
	}
	
//	private void styleButton(Button b, int value, boolean showShips, Ship ship) {
//		b.setText("");
//		
//		if (value == Board.EMPTY) {
//			b.setStyle("-fx-background-colour: " + WATER_COLOUR + ";");
//		}
//		else if (value == Board.MISS) {
//			b.setStyle("-fx-background-colour: " + MISS_COLOUR + ";");
//			b.setText(".");
//		}
//		else if (value == Board.HIT) {
//			if (ship != null && ship.isSunk()) {
//				b.setStyle("-fx-background-colour: " + SUNK_COLOUR + ";");
//			} else {
//				b.setStyle("-fx-background-colour: " + HIT_COLOUR + ";");
//			}
//			b.setText("X");
//		}
//		else if (value == Board.SHIP && showShips && ship != null) {
//			b.setStyle("-fx-background-colour: " + getShipColour(ship) + ";");
//		}
//	}
//	
//	
//	/**
//	 * Disables the board when the game ends.
//	 */
//
//	private void disableAllButtons(Button[][] buttons) {
//		for (Button[] row : buttons) {
//			for (Button b : row) {
//				b.setDisable(true);
//			}
//		}
//	}
//	
	/**
	 * Styles a button based on the board value.
	 * Uses bright colours to make the game more visual.
	 */
//	private void styleButton(Button b, int value, boolean showShips) {
//		b.setFont(Font.font("Arial", FontWeight.BOLD, 14));
//		
//		if (value == 2) { //HIT
//			b.setText("X");
//			b.setStyle(
//				"-fx-background-color: #ff4c4c;" +
//				"-fx-text-fill: white;" +
//				"-fx-border-color: black;"
//			);
//			b.setDisable(true);
//		}
//		else if (value == 3) { //MISS
//			b.setText("o");
//			b.setStyle(
//				"-fx-background-color: #f0f0f0;" + // white/gray
//				"-fx-text-fill: black;" +
//				"-fx-border-color: black;"
//			);
//			b.setDisable(true);
//		}
//		else if (value == 1 && showShips) { // PLAYER SHIP
//			b.setText("S");
//			b.setStyle(
//				"-fx-background-color: #2ecc71;" + //green
//				"-fx-text-fill: white;" +
//				"-fx-border-color: black;"
//			);
//		}
//		else { // WATER
//			if (showShips) {
//				//Player board water
//				b.setStyle(
//					"-fx-background-color: #3498db;" + //blue
//					"-fx-border-color: black;"
//				);
//			} else {
//				//Computer board water (slightly darker)
//				b.setStyle(
//					"-fx-background-color: #2980b9;" +
//					"-fx-border-color: black;"
//				);
//			}
//			b.setText("~");
//		}
//		
//	}
	
//	private String getShipColor(Ship ship) {
//		switch (ship.getName()) {
//		case "Carrier": return CARRIER_COLOUR;
//		case "Battleship": return BATTLESHIP_COLOUR;
//		case "Cruiser": return CRUISER_COLOUR;
//		case "Submarine": return SUBMARINE_COLOUR;
//		case "Destroyer": return DESTROYER_COLOUR;
//		default: return "gray";
//		}
//	}
////	
//	
	public static void main(String[] args) {
		launch(args);

	}

}
