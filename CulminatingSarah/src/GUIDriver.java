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
import java.util.ArrayList;

/**
 * JavaFX GUI for the Battleship game.
 * This class is responsible for: 
 * - displaying the game boards
 * - handling player mouse input 
 * - controlling turn-based gameplay between the player and the computer
 * - updating the visuals of the game after each move
 *
 * Each StackPane represents one cell on the game board. 
 */

public class GUIDriver extends Application {
		// Stores possible target coordinates for the computer after a hit
		private ArrayList<Coordinate> targetQueue = new ArrayList<>();
		
		// Tracks whether the game has ended
		private boolean gameOver = false;
		
		// Keeps track of which squares the computer has already shot at
		private boolean[][] computerShots = new boolean[SIZE][SIZE];
		
		// Board size (8x8)
		private static final int SIZE = 8;
		
		// Game boards for the player and computer
		private Board playerBoard;
		private Board computerBoard;
		
		// Visual grids for both boards
		private StackPane[][] playerCells;
		private StackPane[][] computerCells;
		
		// Text shown to the player
		private Label statusText;
		
		// True if it is the player's turn
		private boolean playerTurn = true;
		
		// Random number generator for ship placement and computer shots
		private Random rand = new Random();
		
		// Random number generator for ship placement and computer shots
		private Image carrierImg, battleshipImg, cruiserImg, submarineImg, destroyerImg;
		private Image explosionImg, missImg, sunkImg, waterImg;
		

		/**
        * Starts the Battleship game.
        * Sets up boards and places ships.
        */
	
		@Override
		public void start(Stage stage) {
			//Load images for the game
			loadImages();	
			//Initialize boards
			playerBoard = new Board(SIZE);
			computerBoard = new Board(SIZE);
			
			// Initialize visual cell grids
			playerCells = new StackPane[SIZE][SIZE];
			computerCells = new StackPane[SIZE][SIZE];
			
			// Randomly place ships on both boards
			placeAllShips(playerBoard);
			placeAllShips(computerBoard);
			
			// Create board layouts
			GridPane playerGrid = createBoardGrid(playerCells, playerBoard, true); // player board (show ships)
			GridPane computerGrid = createBoardGrid(computerCells, computerBoard, false); // computer board (clickable)
			
			// Status text shown below the boards
			statusText = new Label("Your turn! Click on the computer's board.");
			statusText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			
			// Player board section
			VBox playerBox = new VBox(5, new Label("Your Board"), playerGrid);
			playerBox.setAlignment(Pos.CENTER);
			
			// Computer board section
			VBox computerBox = new VBox(5, new Label("Computer Board"), computerGrid);
			computerBox.setAlignment(Pos.CENTER);
			
			// Place boards side by side
			HBox boards = new HBox(50, playerBox, computerBox);
			boards.setAlignment(Pos.CENTER);
			
			VBox root = new VBox(20, boards, statusText);
			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20));
			
			
			//Initial update to show player's ships
			updateBoard(playerBoard, playerCells, true);
			updateBoard(computerBoard, computerCells, false);
			
			// Create and show the scene
			Scene scene = new Scene(root, 900, 500);
			stage.setTitle("Battleship: Player vs Computer");
			stage.setScene(scene);
			stage.show();
			
			
		
		}
		
		/**
	    * Loads an image from the resources folder.
	    */
		private Image loadImage(String filename) {
			return new Image(getClass().getResourceAsStream(filename));
		}
		
		/**
		 * Loads all images used in the game.
	    */
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
		
		/**
        * Adds nearby squares for the computer to try after it makes a hit to provide strategy on the computer's side 
        */
		private void addAdjacentTargets(int row, int col) {
			int[][] directions = {
					{-1, 0}, //up
					{1, 0}, //down
					{0, -1}, //left
					{0, 1} //right
			};
			
			for (int[] d : directions) {
				int newRow = row + d[0];
				int newCol = col + d[1];
				
				if (newRow >=0 && newRow < SIZE &&
					newCol >=0 && newCol < SIZE &&
					!computerShots[newRow][newCol]) {
					
					targetQueue.add(new Coordinate(newRow, newCol));
				}
					
			}
		}
			
		/**
        * Randomly places all five ships on a board.
        */
		private void placeAllShips(Board board) {
			board.placeShipRandom(new Ship("Carrier", 5), rand);
			board.placeShipRandom(new Ship("Battleship", 4), rand);
			board.placeShipRandom(new Ship("Cruiser", 3), rand);
			board.placeShipRandom(new Ship("Submarine", 3), rand);
			board.placeShipRandom(new Ship("Destroyer", 2), rand);
		}
			
		/**
        * Creates a GridPane representing a Battleship board. 
        */
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
			
		/**
        * Handles the player's shot on the computer's board. 
        */
		private void playerShoots(int row, int col) {
			
			if (!playerTurn) return;
			
			String result = computerBoard.shootAt(row, col);
			updateBoard(computerBoard, computerCells, false);
			
			Ship hitShip = computerBoard.getShipAt(row, col);
			
			if (result.startsWith("Sunk")) {
				statusText.setText("You sunk the " + result.substring(5) + "! You get to go again!");
			} else if (result.startsWith("Hit")) {
				statusText.setText("You hit a ship! Go again.");
			} else if (result.equals("Miss")) {
				playerTurn = false;
				statusText.setText("You missed! Computer's turn...");
				computerTurnWithDelay();
			}
			
			if (computerBoard.allShipsSunk()) {
				statusText.setText("You win! All computer ships sunk!");
				gameOver = true;
				disableComputerBoard();
				return;
			}
		}
		
		/**
        * Starts the computer's turn after a delay. 
        */
		private void computerTurnWithDelay() {
			PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(1.5));
			pause.setOnFinished(e -> computerTurn());
			pause.play();
		}
		
		/**
        * Gives the computer an extra delayed shot after a hit or sink. 
        */
		private void computerExtraTurn() {
			PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds((1.5)));
			pause.setOnFinished(e -> computerTurn());
			pause.play();
		}
		
		/**
        * Handles the computer's turn logic.
        */
		private void computerTurn() {
			
			if (playerTurn) return;
			int row, col;
			
	
				//If there are target cells from a previous hit, use them first
				if (!targetQueue.isEmpty()) {
					Coordinate target = targetQueue.remove(0);
					row = target.getRow();
					col = target.getCol();
				}
				else {
					// Otherwise shoot randomly
					do {
						row = rand.nextInt(SIZE);
						col = rand.nextInt(SIZE);
					} while (computerShots[row][col]);
				}
				
				//Mark this cell as shot
				computerShots[row][col] = true;
				
				//Shoot
				String result = playerBoard.shootAt(row, col);
				updateBoard(playerBoard, playerCells, true);
				
				//Handle results
				if (result.startsWith("Hit")) {
					statusText.setText("Computer hit your ship!");
					addAdjacentTargets(row, col);
					computerExtraTurn();
					
					if (!playerBoard.allShipsSunk()) {
						computerExtraTurn();
					}
				}
				else if (result.startsWith("Sunk")) {
					statusText.setText("Computer sunk your " + result.substring(5) + "!");
					targetQueue.clear(); // stop targeting once ship is sunk
					
					if (!playerBoard.allShipsSunk()) {
						computerExtraTurn();
					}
				}
				else {
					playerTurn = true;
					statusText.setText("Your turn!");
				}
				
				//Game over check
				if (playerBoard.allShipsSunk()) {
					statusText.setText("Computer wins! All your ships are sunk!");
					gameOver = true;
					return;
				}
			
			}
		
			
		
	/**
	 * Updates the board visuals based on the game state.
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
	 * Returns the correct ship image based on ship's name
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
	
	/**
    * Disables the computer board when the game ends.
    */
	private void disableComputerBoard() {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				computerCells[r][c].setDisable(true);
			}
		}
	}
	
	/**
    * Starts the program. 
    */
	public static void main(String[] args) {
		launch(args);

	}

}
