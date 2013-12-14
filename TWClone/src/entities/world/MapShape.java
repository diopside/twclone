package entities.world;

import gui.OffsetLine;

import java.util.ArrayList;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Path;

public class MapShape {

	private Tile[][] tiles;
	private ArrayList<Region> regions;
	private Territory[] territories;
	private int size;
	private int TILE_ID;


	public MapShape(int size){
		this.size = size;
		tiles = new Tile[size][size];
		territories = new Territory[ (int)(size * .8)];
		regions = new ArrayList<>();
		generateShape();
		TILE_ID = 0;
	}

	private void generateShape(){
		for (int i = 0; i < size - 1; i ++){
			for (int j = 0; j < size - 1; j ++){
				// Each tile must first be initialized to null for later steps in the generation process
				tiles[i][j] = null;
			}
		}
		generateRegions();
	}

	private void generateRegions(){
		for (int i = 0; i < size + 1; i ++){
			// the ternary operator guarantees one of each region type and then the rest are random
			int type =  (i < 6) ? i : (int) (Math.random() * 6);
			regions.add(new Region(type));
		}


		ArrayList<Tile> regionSeeds = new ArrayList<>();
		while (regionSeeds.size() < regions.size()){
			int x = (int) ( Math.random() * (size - 2)) + 1;
			int y = (int) ( Math.random() * (size - 2)) + 1;
			Tile t = new Tile(x, y, TILE_ID); // The last parameter ensures a unique ID for each tile.
			TILE_ID ++;
			
			boolean valid = true;
			for (Tile seed: regionSeeds){ // Ensure the seeds start out a reasonable distance apart
				if (seed.distance(t) < 3.0f){
					valid = false;
					break;
				}
			}
			if (valid){ // If the new tile is spread away from the others, add it
				tiles[x][y] = t;
				regionSeeds.add(t);
			}
		}

		for (int i = 0; i < regions.size(); i ++) // Add the seeds to the regions
			regions.get(i).addTile(regionSeeds.get(i));

		growRegionSeeds();

	}

	private void growRegionSeeds(){
		int remainingSpaces = countSpaces();


		while (remainingSpaces > 0){
			Region r = regions.get((int) (Math.random() * regions.size()));
			Tile t = r.getTiles().get((int) (Math.random() *r.getTiles().size()) );
			Tile nt = null;
			int x = t.getX();
			int y = t.getY();

			/*
			 * The if structure below will take the random region tile and attempt to "grow" it.
			 * it does this by testing if the tile to the west, east, south, or north are currently null.
			 * If this is the case it will add it to the region.
			 */
			if (x != 0 && tiles[x - 1][y] == null){
				nt = new Tile(x - 1, y, TILE_ID);
				r.addTile(nt);
				tiles[x-1][y] = nt;
				TILE_ID ++;

				remainingSpaces --;
			}
			else if (x != size - 1 && tiles[x + 1][y] == null){
				nt = new Tile(x + 1, y, TILE_ID);
				r.addTile(nt);
				tiles[x+1][y] = nt;
				TILE_ID ++;
				remainingSpaces --;
			}
			else if (y != size - 1 && tiles[x][y + 1] == null){
				nt = new Tile(x, y + 1, TILE_ID);
				r.addTile(nt);
				tiles[x][y+1] = nt;
				TILE_ID ++;
				remainingSpaces --;
			}
			else if (y != 0 && tiles[x][y - 1] == null){
				nt = new Tile(x, y - 1, TILE_ID);
				r.addTile(nt);
				tiles[x][y - 1] = nt;
				remainingSpaces --;
				TILE_ID ++;
			}	
		}

		// Regions are now successfully created, time to create the territories in a similar manner

		generateTerritorySeeds();
	}

	private void generateTerritorySeeds(){

		boolean[][] assigned = new boolean[size][size];

		
		ArrayList<ArrayList<Tile>> territoryTiles = new ArrayList<ArrayList<Tile>>();
		for (int i = 0; i < territories.length; i ++){ // create a new list for each territory to house their tiles
			territoryTiles.add(new ArrayList<Tile>());
		}

		int numWithSeeds = 0;
		while (numWithSeeds < territoryTiles.size()){
			// get randomized coordinates for the tile
			int x = (int) ( Math.random() * (size - 2)) + 1;
			int y = (int) ( Math.random() * (size - 2)) + 1;

			Tile tile = tiles[x][y];
			boolean validTile = true;
			for (int i = 0; i < numWithSeeds; i ++)
				if (i != numWithSeeds){
					Tile otherTile = territoryTiles.get(i).get(0);// test against the other cities to ensure it isn't too close
					if (otherTile.distance(tile) < 6.5f){
						validTile = false;
						break;
					}
				}
			if (validTile){
				territoryTiles.get(numWithSeeds).add(tile);
				numWithSeeds ++;
				assigned[x][y] = true;
			}

		}
		
		for (int i = 0; i < territories.length; i ++){
			territories[i] = new Territory(territoryTiles.get(i), (short) i);
			territoryTiles.get(i).get(0).setTerritory(territories[i]);
		}

		growTerritorySeeds(assigned);


	}
	
	private void growTerritorySeeds(boolean[][] assigned){
		
		for (int i = 0; i < size; i ++)
			for (int j = 0; j < size; j ++){
				Tile currentTile = tiles[i][j];
				if (!assigned[i][j]){
					assignTileToClosestTerritory(currentTile);
					assigned[i][j] = true;
				}
			}
		
		generateBorders();
		
	}
	
	private void generateBorders(){
		
		for (Territory t: territories){
			for (Tile tile: t.getTiles()){
				setBorders(tile, t);
			}
			System.out.println(t.getBorder().size());
		}
		
		
		
	}
	private void setBorders(Tile tile, Territory t){
		int tX = tile.getX();
		int tY = tile.getY();
		final int MARGIN = 2;
		// The margin will be added to the borders to ensure there is no overlapping of borders, margin should be equal to painted line width for the border
		
		// check northern border
		if (tY - 1 >= 0)
			if (tiles[tX][tY -1].getTerritory().getID() != t.getID())
				t.getBorder().add(new OffsetLine(tX * Tile.SIZE, tY * Tile.SIZE + MARGIN, tX* Tile.SIZE + Tile.SIZE, tY * Tile.SIZE + MARGIN));
		// check eastern border
		if (tX + 1 <= size - 1)
			if (tiles[tX + 1][tY].getTerritory().getID() != t.getID())
				t.getBorder().add(new OffsetLine(tX * Tile.SIZE + Tile.SIZE - MARGIN, tY * Tile.SIZE, tX* Tile.SIZE + Tile.SIZE - MARGIN, tY * Tile.SIZE + Tile.SIZE));
		// check southern border
		if (tY + 1 <= size - 1)
			if (tiles[tX][tY + 1].getTerritory().getID() != t.getID())
				t.getBorder().add(new OffsetLine(tX * Tile.SIZE, tY * Tile.SIZE + Tile.SIZE - MARGIN, tX* Tile.SIZE + Tile.SIZE, tY * Tile.SIZE + Tile.SIZE - MARGIN));
		// check western border
		if (tX - 1 >= 0)
			if (tiles[tX - 1][tY].getTerritory().getID() != t.getID())
				t.getBorder().add(new OffsetLine(tX * Tile.SIZE + MARGIN, tY * Tile.SIZE, tX* Tile.SIZE + MARGIN, tY * Tile.SIZE + Tile.SIZE));
		
	}
	
	


	public Tile[][] getTiles(){
		return tiles;
	}

	private int countSpaces(){
		int totalSpaces = 0;
		for (Tile[] a: tiles)
			for (Tile b: a)
				if (b == null) totalSpaces ++;
		return totalSpaces;
	}

	public ArrayList<Region> getRegions(){
		return regions;
	}

	public int getSize(){
		return size;
	}
	
	public Territory[] getTerritories(){
		return territories;
	}

	private void assignTileToClosestTerritory(Tile tile){
		
		float currentDistance = Float.MAX_VALUE, shortestDistance = Float.MAX_VALUE;
		Territory bestTerritory = null;
		
		for (Territory t: territories){
			Tile currentTile = t.getTiles().get(0);
			currentDistance = (float) tile.distance(currentTile);
			if (currentDistance <= shortestDistance){
				shortestDistance = currentDistance;
				bestTerritory = t;
			}
		}
		
		bestTerritory.getTiles().add(tile);
		tile.setTerritory(bestTerritory);
		
	}




}


















