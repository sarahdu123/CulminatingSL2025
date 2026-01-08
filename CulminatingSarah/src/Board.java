import java.util.ArrayList;
import java.util.Random;
/**
 * The Board class represents one Battleship board.
 * 
 * It stores: 
 * - a 2D grid of integers 
 * - a list of ships placed on the board 
 * 
 * Grid values: 
 * 0 = water
 * 1 = ship
 * 2 = hit
 * 3 = miss
 * 
 * It is responsible for:
 * - placing ships
 * - handling shots
 * - checking if all ships are sunk
 */


public class Board {
	
	public static final int EMPTY = 0;
	public static final int SHIP = 1;
	public static final int HIT = 2;
	public static final int MISS = 3;
	
	// Size of the board (ex: 8 is 8 x 8)
	private int size;
	
	// 2D array representing the board
	private int [][] grid; 
	
	// List of all ships currently on the board
	private ArrayList<Ship> ships;
	
	/**
	 * Constructs a new Board with the given size.
	 * Initializes the grid and ship list.
	 * @param size the board size 
	 */
	
	public Board(int size) {
		this.size = size;
		grid = new int[size][size];
		ships = new ArrayList<>();
	}
	
	/**
	 * Returns the grid so the GUI can read board values.
	 * 
	 * @return the 2D board grid
	 */
	public int[][] getGrid() {
		return grid;
	}
	
	/**
	 * Returns the list of ships on the board.
	 */
	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	/**
	 * Checks whether a given row and column are inside the board.
	 * 
	 * @param row row index
	 * @param col column index
	 * @return true if the coordinate is on the board 
	 */
	public boolean inBounds(int row, int col) {
		return row >= 0 && row < size && col >= 0 && col < size;
	}
	
	/**
	 * Returns true if this square has already been shot
	 * (either a hit or miss).
	 * 
	 * @param row row index 
	 * @param col column index 
	 * @return true if grid value is 2 (hit) or 3 (miss) 
	 */
	public boolean alreadyTried(int row, int col) {
		return grid[row][col] == 2 || grid[row][col] == 3;
	}
	
	/**
	 * Places a ship randomly on the board.
	 * Keeps trying random positions until the ship fits.
	 * 
	 * @param ship the ship to place
	 * @param rand Random object used for random placement
	 */
	public void placeShipRandom(Ship ship, Random rand) {
		boolean placed = false;
		
		// Keep trying until the ship is successfully placed 
		while (!placed) {
			int row = rand.nextInt(size);
			int col = rand.nextInt(size);
			boolean horizontal = rand.nextBoolean();
			
			// Check if the ship can be placed at this position 
			if (canPlaceShip(ship.getLength(), row, col, horizontal)) {
				
				// Place each part of the ship
				for (int i = 0; i < ship.getLength(); i++) {
					int r = 0;
					int c = 0;
					if (horizontal) {
						r = row;
						c = col + i;
					} else {
						r = row + i;						
						c = col;
					}
					
					// Mark the grid square as a ship
					grid[r][c] = 1;
					
					// Store the position inside the Ship object 
					ship.addPosition(new Coordinate(r,c));
				}
				// Add the ship to the board's ship list
				ships.add(ship);
				placed = true;
			}
		}
		
	}
	
	/**
	 * Checks whether a ship can be placed at a given position. 
	 * 
	 * The ship must: 
	 * - stay within the board
	 * - not overlap another ship 
	 * @param length ship length 
	 * @param row row index
	 * @param col column index
	 * @param horizontal
	 * @return
	 */
	private boolean canPlaceShip(int length, int row, int col, boolean horizontal) {
		for (int i = 0; i < length; i++) {
			int r = 0;
			int c = 0;
			if (horizontal) {
				r = row;
				c = col + i;
			} else {
				r = row + i;						
				c = col;
			}
			if (!inBounds(r,c) || grid[r][c] != 0) {
				return false;
			}	
		}
		return true;
	}
	/**
	 * Fires a shot at the given row and column.
	 * Returns a message describing the result. 
	 * @param row row index 
	 * @param col col index
	 * @return "Out of bounds", "Already tried", "Miss", "Hit", or "Sunk"
	 */

	public String shootAt(int row, int col) {
		// Shot is outside the board
		if (!inBounds(row, col)) {
			return "Out of bounds";
		}
		
		// Shot was already taken before
		if (alreadyTried(row, col)) {
			return "Already tried";
		}
		
		// Shot hits water
		if (grid[row][col] == 0) {
			grid[row][col] = 3; // mark as a miss
			return "Miss";
		}
		
		// Shot hits a ship
		if (grid[row][col] == 1) {
		grid[row][col] = 2; // mark as a hit
		
			// Find which ship was hit 
			for (Ship s : ships) {
				for (Coordinate pos : s.getPositions()) {
					if (pos.getRow() == row && pos.getCol() == col) {
						s.registerHit();
						
					// Check if that hit sank the ship
					if (s.isSunk()) {
						return "Sunk " + s.getName();
					} else {
						return "Hit " + s.getName();
					}
				}
			}
		}
			return "Hit";
	}
		return "Miss";
}
		
		/**
		 * Returns true if every ship on the board is sunk.
		 * Used to check if the game is over.
		 * 
		 * @return true if all ships are sunk
		 */
		public boolean allShipsSunk() {
			for (Ship s : ships) {
				if (!s.isSunk()) {
					return false;
				}
			}
			return true;
	}
		
	public Ship getShipAt(int row, int col) {
		for (Ship s : ships) {
			for (Coordinate pos : s.getPositions()) {
				if (pos.getRow() == row && pos.getCol() == col) {
					return s;
				}
			}
		}
		return null;
	}
}
