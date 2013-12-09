package entities.world;

import java.util.ArrayList;

public class MapShape {

	private Tile[][] tiles;
	private ArrayList<Region> regions;
	private int size;


	public MapShape(int size){
		this.size = size;
		tiles = new Tile[size][size];
		regions = new ArrayList<>();
		generateShape();
	}

	private void generateShape(){
		for (int i = 0; i < size - 1; i ++){
			for (int j = 0; j < size - 1; j ++){
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
			Tile t = new Tile(x, y, regionSeeds.size());
			boolean valid = true;
			for (Tile seed: regionSeeds){
				if (seed.distance(t) < 3.0f){
					valid = false;
					break;
				}
			}
			if (valid){
				tiles[x][y] = t;
				regionSeeds.add(t);
			}

		}

		for (int i = 0; i < regions.size(); i ++){
			regions.get(i).addTile(regionSeeds.get(i));
		}


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
			
			if (x != 0 && tiles[x - 1][y] == null){
				nt = new Tile(x - 1, y, 0);
				r.addTile(nt);
				tiles[x-1][y] = nt;

				remainingSpaces --;
			}
			else if (x != size - 1 && tiles[x + 1][y] == null){
				nt = new Tile(x + 1, y, 0);
				r.addTile(nt);
				tiles[x+1][y] = nt;

				remainingSpaces --;
			}
			else if (y != size - 1 && tiles[x][y + 1] == null){
				nt = new Tile(x, y + 1, 0);
				r.addTile(nt);
				tiles[x][y+1] = nt;

				remainingSpaces --;
			}
			else if (y != 0 && tiles[x][y - 1] == null){
				nt = new Tile(x, y - 1, 0);
				r.addTile(nt);
				tiles[x][y - 1] = nt;
				remainingSpaces --;
			}
			
			
			
		}




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

}


















