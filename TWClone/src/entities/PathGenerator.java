package entities;

import java.util.ArrayList;

import entities.world.Tile;

public class PathGenerator {

	private static Tile[][] TILES;
	private static int SIZE;
	private static final int MAX_DISTANCE = 35;

	/*public static ArrayList<Coordinates> generatePath(Coordinates coord, Tile dest){
		ArrayList<Coordinates> path = new ArrayList<>();

		int currentX = coord.getX();
		int currentY = coord.getY();

		while (currentX != dest.getX() || currentY != dest.getY()){
			if ( currentX < dest.getX()){
				if (currentY < dest.getY()) currentY ++;
				else if (currentY > dest.getY()) currentY --;
				currentX ++;
			} // case x less than
			else if (currentX > dest.getX()){
				if (currentY < dest.getY()) currentY ++;
				else if (currentY > dest.getY()) currentY --;
				currentX --;
			} // end case x greater than
			else{
				if (currentY < dest.getY()) currentY ++;
				else if (currentY > dest.getY()) currentY --;
			}

			if (tile(currentX, currentY).occupied()){
				/*
				 * If the ideal tile is occupied it will locate a new tile.  To do this it will first determine the general direction in which it was headed, and will slightly modify this.
				 * It will find a tile adjacent to the blocking tile and see if this can generate a path to the destination, if so it will generate a path linking the end of the n - 1 path
				 * to the head of the n path
				 */
			/*	int dx = currentX - path.get(path.size() - 1).getX();
				int dy = currentY - path.get(path.size() - 1).getY();




			}


			path.add(new Coordinates(currentX, currentY));
		}





		return path;
	}*/


	public static ArrayList<Tile> generatePath(Coordinates coord, Tile dest){
		ArrayList<Tile> path = new ArrayList<>();
		Tile start = TILES[coord.getX()][coord.getY()];


	

		Tile current = null;
		ArrayList<PathTuple> tuples = new ArrayList<>();
		do {
			if (current == null)
				current = start;
			generateNeighbors(current, tuples, dest);
			PathTuple bestNeighbor = tuples.get(0);
			for (PathTuple pt: tuples){
				if (pt.getField() < bestNeighbor.getField())
					bestNeighbor = pt;
			}
			
			path.add(bestNeighbor.getTile());
			current = bestNeighbor.getTile();

		} while (current.getX() != dest.getX() && current.getY() != dest.getX());



		return path;
	}




	private static void generateNeighbors(Tile current,
			ArrayList<PathTuple> tuples, Tile dest) {

		final int[] vals = {1, 0, -1};

		tuples.clear();
		int x = current.getX();
		int y = current.getY();

		for (int i = 0; i < vals.length; i ++){
			x = current.getX();
			y = current.getY();
			for (int j = 0; j < vals.length; j ++){
				if (i == 0 && j == 0)
					continue;
				else{
					double field = TILES[x + vals[i]][y + vals[j]].occupied() ? Double.MAX_VALUE : TILES[x + vals[i]][y + vals[j]].distance(dest);
					tuples.add(new PathTuple(TILES[x + vals[i]][y + vals[j]], field));
				}
			}
		}
		
		System.out.println(tuples.size());





	}


	public static void setTiles(Tile[][] tiles, int size){
		TILES = tiles;
		SIZE = size;
	}

	public static boolean has(int x, int y){
		return (x < SIZE) && (y < SIZE);

	}

	public static Tile tile(int x, int y){
		return TILES[x][y];
	}




	

}












