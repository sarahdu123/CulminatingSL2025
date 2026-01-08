import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Board board = new Board(8);
		Random rand = new Random();
		
		board.placeShipRandom(new Ship("Destroyer", 2), rand);
		board.placeShipRandom(new Ship("Submarine", 3), rand);
		
		System.out.println("Initial board (ships hidden):");
		printBoard(board.getGrid());
		
		System.out.println("Debug view (ships showing):");
		printBoardWithShips(board.getGrid());
		
		System.out.println("\nShooting at (2,3): " + board.shootAt(2,3));
		System.out.println("Shooting at (4,4): " + board.shootAt(4,4));
		System.out.println("Shooting at (2,3) again: " + board.shootAt(2,3));
		
		System.out.println("\nBoard after shots: ");
		printBoard(board.getGrid());
		
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
	
	
	// Prints board WITH ships visible (demo)
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

