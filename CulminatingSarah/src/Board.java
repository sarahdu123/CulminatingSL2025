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
					grid[r][c] = 1;
					ship.addPosition(new Coordinate(r,c));
				}
				ships.add(ship);
				placed = true;
			}
		}
		
	}
	
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
			if (r >= size || c >= size || grid[r][c] != 0) {
				return false;
			}
			
		}
		return true;
	}

	// Shoot at coordinate 
	public String shootAt(int row, int col) {
		if (grid[row][col] == 1) {
			grid[row][col] = 2;
			for (Ship s : ships) {
				for (Coordinate pos : s.getPositions()) {
					if (pos.getRow() == row && pos.getCol() == col) {
						s.registerHit();
					}
					if (s.isSunk()) {
						return "Sunk " + s.getName();
					} else {
						return "Hit " + s.getName();
					}
					
				}
			}
		}
	}
}
