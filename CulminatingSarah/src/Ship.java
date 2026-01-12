import java.util.ArrayList;

/**
 * Ship represents a single Battleship ship (Carrier, Destroyer, Battleship, Cruiser, Submarine).
 * 
 * We track: 
 * 	- name and length (metadata)
 * 	- positions (which coordinates the ship occupies)
 *  - hits (how many times it has been hit)
 *  
 *  Board is responsible for:
 *  - placing ships
 *  - determining if a shot hit which ship
 */
public class Ship {
	
	// The name of the ship (e.g. "Destroyer")
	private String name;
	
	// The length of the ship (number of grid squares)
	private int length;
	
	// How many times this ship has been hit
	private int hits;
	
	// The exact coordinates this ship occupies
	private ArrayList<Coordinate> positions;
	
	/**
	 * Constructs a new Ship. 
	 * 
	 * @param name ship name
	 * @param length ship length (number of grid squares)
	 */
	public Ship(String name, int length) {
		this.name = name; 
		this.length = length;
		this.hits = 0;
		this.positions = new ArrayList<>();
	}
	
	/**
	 * Returns the name of the ship
	 * 
	 * @return ship name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the length of the ship
	 * 
	 * @return ship length
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Adds a coordinate to the list of positions occupied by this ship.
	 * This method is called when the ship is placed on the board.
	 * 
	 * @param c is the coordinate occupied by the ship
	 */
	public void addPosition(Coordinate c) {
		positions.add(c);
	}
	
	/**
	 * Returns all coordinate occupied by this ship.
	 * 
	 * @return an ArrayList of Coordinate objects
	 */
	public ArrayList<Coordinate> getPositions() {
		return positions;
	}
	
	/**
	 * Registers a hit on this ship.
	 * Increases the hit count by one
	 */
	public void registerHit() {
		hits++;
	}
	
	/**
	 * Checks whether the ship has been sunk.
	 * A ship is sunk when the number of hits is greater than or equal to its length
	 * 
	 * @return true if the ship is sunk, false otherwise.
	 */
	public boolean isSunk() {
		return hits >= length;
	}
}
