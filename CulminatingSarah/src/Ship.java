import java.util.ArrayList;

/**
 * Ship represents a single Battleship ship Carrier, Destroyer, etc.).
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

	private String name;
	private int length;
	
	// How many times this ship has been hit
	private int hits;
	
	// The exact coordinates this ship occupies
	private ArrayList<Coordinate> positions;
	
	/**
	 * Constructs a new Ship. 
	 * 
	 * @param name ship name
	 * @param length shhip length (number of grid squares)
	 */
	public Ship(String name, int length) {
		this.name = name; 
		this.length = length;
		this.hits = 0;
		this.positions = new ArrayList<>();
	}
	
	/**
	 * @return ship name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return ship length
	 */
	public int getLength() {
		return length;
	}
	
	public void addPosition(Coordinate c) {
		positions.add(c);
	}
	
	public ArrayList<Coordinate> getPositions() {
		return positions;
	}
	
	public void registerHit() {
		hits++;
	}
	
	public boolean isSunk() {
		return hits >= length;
	}
}
