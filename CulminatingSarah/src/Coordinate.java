
public class Coordinate {
	public class Coordinate{
		private int row;
		private int col;
		
		/**
		 * Constructs a Coordinate using row and column indexes.
		 * 
		 * @param row 0-based row index
		 * @param col 0-based column index
		 */
		
		

		public class Coordinate(int row, int col) {
			this.row = row; 
			this.col = col;

		}
		/*
		 * @return the row index
		 */
		
		public int getRow() {
			return row;
		}
		
		/*
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
			//If both references point to the same object
			if (this == other) {
				return true;
			} 
			
			//If the other object is not a Coordinate, they can't be equal.
			if (!(other instanceOf Coordinate)) {
				return false;
			}
			
			//Type cast
			Coordinate c = (Coordinate) other;
			
			// Compare row and col values
			return this.row == c.row && this.col == c.col;
		}
		
		/**
		 * @return a readable String representation (useful for debjugging)
		 */
		@Override
		public String toString() {
			return "Coordinate[row=" + row + ", col=" + col + "]";
		}
	}
}
