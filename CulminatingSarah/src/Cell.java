/**
 * A Cell represents one square on the board grid.
 * 
 * A cell must track:
 * - whether there is a ship on it.
 * - whether it has been shot already
 */
public class Cell {
	private boolean hasShip;
	private boolean isHit;
	
	/**
	 * Constructs a default Cell:
	 * - water (no ship)
	 * - not hit yet
	 */
	
	public Cell() {
		this.hasShip = false;
		this.isHit = false;
	}
	
	/**
	 * @return true if this cell has a ship segment
	 */
	public boolean hasShip() {
		return hasShip;
	}
	
	/**
	 * Sets whether this cell contains a ship segment.
	 * 
	 * @param hasShip true if a ship occupies this cell
	 */
	public void setHasShip(boolean hasShip) {
		this.hasShip = hasShip;
	}
	
	/**
	 * @return true if this cell has already been fired upon
	 */
	public boolean isHit() {
		return isHit;
	}
	
	/**
	 * Marks this cell as hit (shot at).
	 * 
	 * @param hit true if shot has been fired here
	 */
	public void setHit(boolean hit) {
		this.isHit = hit;
	}
	
	/**
	 * Returns a character to display this cell in the console.
	 * 
	 * Display rules:
	 * - 'X' = hit ship
	 * - 'o' = miss
	 * - 'S' = ship (only shown on your own board)
	 * - '~' = unknown water
	 * 
	 * @param showShips if true, show ships that are not hit yet.
	 * @return display character
	 */
	
	public char getDisplayChart(boolean showShips) {
		//If already shot and had a ship --> hit marker
		if (isHit && hasShip) {
			return 'X';
		}
		
		// If already shot and no ship --> miss marker
		if (isHit && !hasShip) {
			return 'o';
		}
		// If not shot and has ship, only show if allowed
		if (!isHit && hasShip && showShips) return 'S';
		}
		// Default: water / unknown
		return '~';
}
