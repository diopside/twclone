package entities.world;

import java.util.ArrayList;

public class MapShape {

	private boolean[][] spaces;
	private ArrayList<Region> regions;
	private ArrayList<RTriple> triples;
	private int size;


	public MapShape(int size){
		this.size = size;
		spaces = new boolean[size][size];
		regions = new ArrayList<>();
		triples = new ArrayList<>();
		generateShape();
	}

	private void generateShape(){
		for (int i = 1; i < size - 1; i ++){
			for (int j = 1; j < size - 1; j ++){
				spaces[i][j] = true;
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

		int totalSpaces = countSpaces(); // the total number of available spaces
		totalSpaces /= size; // get spacing for region seed.

		for (int i = 1; i < regions.size() + 1; i ++){
			// This method will generate "seeds" off of which regions will grow
			// The x and y variables are assigned to somewhat evenly space seeds about the map.
			int x = totalSpaces / i;
			int y = totalSpaces % i;

			regions.get(i - 1).addTile(x, y);
			triples.add(new RTriple(x, y, i - 1));
			spaces[x][y] = false;
		}

		growRegionSeeds();

	}

	private void growRegionSeeds(){
		int remainingSpaces = countSpaces();


		while (remainingSpaces > 0){
			RTriple r = null;
			RTriple rt = triples.get((int) (Math.random() * triples.size()));
			if (Math.random() > .2){
				if (spaces[rt.x + 1][rt.y] && rt.x + 1 < size){
					regions.get(rt.region).addTile(rt.x + 1, rt.y);
					r = new RTriple(rt.x + 1, rt.y, rt.region);
					remainingSpaces --;
				}
				else if(spaces[rt.x - 1][rt.y] && rt.x - 1 >= 0){
					regions.get(rt.region).addTile(rt.x - 1, rt.y);
					r = new RTriple(rt.x - 1, rt.y, rt.region);
					remainingSpaces --;
				}
				else if( rt.y -1 >= 0 && spaces[rt.x][rt.y - 1]){
					regions.get(rt.region).addTile(rt.x, rt.y - 1);
					r = new RTriple(rt.x, rt.y - 1, rt.region);
					remainingSpaces --;
				}
				else if(spaces[rt.x][rt.y + 1] && rt.y + 1 < size){
					regions.get(rt.region).addTile(rt.x + 1, rt.y + 1);
					r = new RTriple(rt.x , rt.y+ 1, rt.region);
					remainingSpaces --;
				}

			}

			if (r != null)
				triples.add(r);
		}
	}


	private int countSpaces(){
		int totalSpaces = 0;
		for (boolean[] a: spaces)
			for (boolean b: a)
				if (b) totalSpaces ++;
		return totalSpaces;
	}

	public ArrayList<Region> getRegions(){
		return regions;
	}


	class RTriple{
		public int x, y, region;

		public RTriple(int x, int y, int region){
			this.x = x;
			this.y = y;
			this.region = region;
		}
	}
}


















