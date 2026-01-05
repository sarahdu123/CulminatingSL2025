import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.text.*;
import java.util.Random;

/**
 * JavaFX GUI for Battleship.
 * Each button represents one position on the board
 */

public class GUIDriver extends Application {
		private static final int SIZE = 8;
		
		private Board board;
		private Button[][] buttons;
		private Label statusText;
		
		@Override
		public void start(Stage stage) {
			board = new Board(SIZE);
			buttons = new Button[SIZE][SIZE];
			
			Random rand = new Random();
			board.placeShipRandom(new Ship("Carrier", 5), rand);
			board.placeShipRandom(new Ship("Battleship", 4), rand);
			board.placeShipRandom(new Ship("Cruiser", 3), rand);
			board.placeShipRandom(new Ship("Submarine", 3), rand);
			board.placeShipRandom(new Ship("Destroyer", 2), rand);
			
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(5);
			grid.setVgap(5);
			
			// Create buttons for the grid
			for (int r=0; r < SIZE; r++) {
				for (int c=0; c < SIZE; c++) {
					Button b = new Button("~");
					b.setPrefSize(40, 40);
					b.setFont(Font.font(16));
					
					final int row = r;
					final int col = c;
					b.setOnAction(e-> handleShot(row,col));
					buttons[r][c] = b;
					grid.add(b, c, r);
					
				}
			}
			
			statusText = new Label("Click a square to fire!");
			statusText.setFont(Font.font(14));
			
			VBox root = new VBox(15, grid, statusText);
			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20));
			
			Scene scene = new Scene(root, 420, 480);
			
			stage.setTitle("Battleship");
			stage.setScene(scene);
			stage.show();
	}
		
	/**
	 * Handles a player firing a shot
	 */
	private void handleShot(int row, int col) {
		String result = board.shootAt(row,  col);
		statusText.setText(result);
		
		updateBoard();
		
		if (board.allShipsSunk()) {
			statusText.setText("You win! All ships sunk!");
			disableAllButtons();
		}
	}
	
	/**
	 * Updates the buttons display based on board state.
	 */
	
	private void updateBoard() {
		int[][] grid = board.getGrid();
		
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				
				if (grid[r][c] == 2) {
					buttons[r][c].setText("X");
					buttons[r][c].setDisable(true);
				}
				else if (grid[r][c] == 3) {
					buttons[r][c].setText("o");
					buttons[r][c].setDisable(true);
				}
			}
		}
	}
	
	
	/**
	 * Disables the board when the game ends.
	 */

	private void disableAllButtons() {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				buttons[r][c].setDisable(true);
			}
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);

	}

}
