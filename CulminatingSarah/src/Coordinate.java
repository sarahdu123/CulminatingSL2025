
public class Coordinate {
		private int row;
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
		 * @return the row index
		 */
		public int getRow() {
			return row;
		}
		
		/**
		 * @return the column index
		 */
		public int getCol() {
			return col;
		}
		
		/**
		 * Coordinates need a correct equals method so we can compare positions 
		 * (ex: when checking if a shot hit a ship position)
		 * 
		 * @param other another object
		 * @return true if both a Coordinates with the same row/col
		 */
		@Override
		public boolean equals(Object other) {
			//If the other object is not a Coordinate, they can't be equal.
			if (!(other instanceof Coordinate)) {
				return false;
			}
			
			//Type cast
			// Compare row and col values
			Coordinate c = (Coordinate) other;
			return row == c.row && col == c.col;
			}
			
		
		/**
		 * @return a readable String representation
		 */
		@Override
		public String toString() {
			return "(" + row + ", " + col + ")";
		}
}



