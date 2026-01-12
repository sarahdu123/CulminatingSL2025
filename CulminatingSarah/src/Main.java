import java.util.Random;

/**
 * The Main class is used to test the Battleship game logic.
 * 
 * It:
 * - creates a Board
 * - places ships randomly
 * prints the board in different views
 * - fires sample shots
 * - checks if all ships are sunk
 * 
 * This class is mainly for testing and demonstration purposes
 */
public class Main {
	public static void main(String[] args) {
		
		// Create an 8x8 Battleship board
		Board board = new Board(8);
		
		// Random object used for ship placement
		Random rand = new Random();
		
		// Place ships randomly on the board
		board.placeShipRandom(new Ship("Destroyer", 2), rand);
		board.placeShipRandom(new Ship("Submarine", 3), rand);
		
		// Print the board as the player would see it (ships hidden)
		System.out.println("Initial board (ships hidden):");
		printBoard(board.getGrid());
		
		// Print the board showing ships (debug/testing view)
		System.out.println("Debug view (ships showing):");
		printBoardWithShips(board.getGrid());
		
		// Fire a few test shots
		System.out.println("\nShooting at (2,3): " + board.shootAt(2,3));
		System.out.println("Shooting at (4,4): " + board.shootAt(4,4));
		System.out.println("Shooting at (2,3) again: " + board.shootAt(2,3));
		
		// Print the board after shots have been taken
		System.out.println("\nBoard after shots: ");
		printBoard(board.getGrid());
		
		//Check if the game is over
		System.out.println("\nAll ships sunk? " + board.allShipsSunk());
	}
	
	/**
	 * Prints the board in a readable way.
	 * 
	 * If showsShips is false: 
	 * - ships stay hidden (~)
	 * - only hits (X) and misses (o) show
	 * 
	 * If showShips is true:
	 * - ships show as S
	 * @param grid
	 */
	
	// Prints board WITHOUT showing ships
	private static void printBoard(int[][] grid) {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid.length; c++) {
				if (grid[r][c] == 2) {
					System.out.print("X ");
				} else if (grid[r][c] == 3) {
					System.out.print("o ");
				} else {
					System.out.print("~ ");
				}
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Prints the board with ships visible.
	 * This method is used for debugging and testing.
	 * 
	 * Symbols: 
	 * S = ship
	 * X = hit
	 * o = miss
	 * ~ = water
	 * 
	 * @param grid the 2D board grid
	 */
	private static void printBoardWithShips(int[][] grid) {
		for (int r = 0; r < grid.length; r++) {
			System.out.print(r +" ");
			for (int c = 0; c < grid.length; c++) {
				if (grid[r][c] == 2) {
					System.out.print("X "); // hit
				} else if (grid[r][c] == 3) {
					System.out.print("o "); // miss
				} else if (grid[r][c] == 1){
					System.out.print("S "); // ship (only if showing)
				} else {
					System.out.print("~ "); // water / hidden ship
				}
			}
			System.out.println();
		}
	}
}

