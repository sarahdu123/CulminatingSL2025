/**
 * The coordinate class represents a single position on the Battleship board.
 * 
 * A Coordinate stores:
 * - a row index
 * - a column index
 * 
 * Coordinate are used to track ship locations and shots on the board.
 */

public class Coordinate {
	// Row index of the coordinate (0-based)
	private int row;
	
	// Column index of the coordinate (0-based)
	private int col;

	/**
	 * Constructs a Coordinate using row and column indexes.
	 * 
	 * @param row 0-based row index
	 * @param col 0-based column index
	 */

	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;

	}
	/**
	 * Returns the row index of this coordinate
	 * 
	 * @return the row index
	 */

	public int getRow() {
		return row;
	}

	/**
	 * Returns the column index of this coordinate
	 * 
	 * @return the column index
	 */
	public int getCol() {          
		return col;
	}

	/**
	 * Coordinates need a correct equals method so we can compare positions (ex:
	 * when checking if a shot hit a ship position)
	 * 
	 * @param other another object
	 * @return true if both a Coordinates with the same row/col
	 */
	@Override
	public boolean equals(Object other) {
		// If the other object is not a Coordinate, they can't be equal.
		if (!(other instanceof Coordinate)) {
			return false;
		}

		// Type cast
		// Compare row and col values
		Coordinate c = (Coordinate) other;
		return row == c.row && col == c.col;
	}

	/**
	 * Returns a readable string representation of this Coordinate
	 * 
	 * @return a readable String representation
	 */
	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}

}
