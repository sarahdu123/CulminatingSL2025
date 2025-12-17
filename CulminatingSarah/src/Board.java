import java.util.ArrayList;
import java.util.Random;
public class Board {
	private int size;
	private int [][] grid; // 0 = water, 1 = ship, 2 = hit, 3 = miss
	private ArrayList<Ship> ships;
	
	public Board(int size) {
		this.size = size;
		grid = new int[size][size];
		ships = new ArrayList<>();
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public int getSize() {
		return size;
	}
	
	public ArrayList<Ship> getShips() {
		return ships;
	}

	// Place ship randomly
	public void placeShipRandom(Ship ship, Random rand) {
		boolean placed = false;
		while (!placed) {
			int row = rand.nextInt(size);
			int col = rand.nextInt(size);
			boolean horizontal = rand.nextBoolean();
			
			if (canPlaceShip(ship.getLength(), row, col, horizontal)) {
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
					
				}
			}
		}
		
	}

}
