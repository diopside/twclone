package entities;

import java.util.ArrayList;
import java.util.Stack;

import entities.world.Tile;

public class PathGenerator {

	private static Tile[][] TILES;
	private static int SIZE;
	private static final int MAX_DISTANCE = 100; // The greatest distance away from the current unit allowed to generate a path from




	public static Stack<Tile> generateAStarPath(Coordinates startCoord, Tile dest){
		long s = System.currentTimeMillis();
		Stack<Tile> path = new Stack<>();
		ArrayList<Node> open = new ArrayList<>();
		ArrayList<Node> closed = new ArrayList<>();
		Tile start = TILES[startCoord.getX()][startCoord.getY()];
		
		// currently if the destination is occupied it just won't generate a path
		if (dest.occupied())
			return path;

		open.add(new Node(start, 0, determineH(start, dest), null));

		while (open.size() > 0){
			Node nextNode = getBestNode(open);
			if (nextNode.getTile() == dest){
				traversePath(nextNode, path);
				break;
			}
			addNeighbors(getBestNode(open), open, closed, start, dest);
		}
		System.out.println(System.currentTimeMillis() - s);
		return path;

	}

	private static void traversePath(Node node, Stack<Tile> path){
		if (node.getParent() == null)
			return;
		else{
			path.add(node.getTile());
			traversePath(node.getParent(), path);
		}

	}



	private static void addNeighbors(Node current, ArrayList<Node> open, ArrayList<Node> closed, Tile start, Tile dest){

		/*
		 * This will examine all 8 tiles surrounding the tile at the current node, it will then
		 * add any valid ones to the open list or reevaluate the path of any already on the open list
		 * to see if the path from the current node is better than their previous parent node
		 */
		for (int i = -1; i < 2; i ++)
			for (int j = -1; j < 2; j ++){
				if (j == 0 && i == 0)
					continue;
				else{
					if (current.getTile().getX() + i > 0 && current.getTile().getX() + i < SIZE && current.getTile().getY() + j > 0 && current.getTile().getY() + j < SIZE){
						Tile next = TILES[current.getTile().getX() + i][current.getTile().getY() + j];
						if (isValid(next, closed)){
							if (open.contains(next))
								; // recompute f here to see if this is a better route
							else
								open.add(new Node(next, determineG(next, current, dest), determineH(next, dest), current));
						}
					}
				}
			}

		open.remove(current);
		closed.add(current);

	}

	private static Node getBestNode(ArrayList<Node> nodes){
		// This list will return the Node in the list with the lowest f
		Node best = nodes.get(0);
		for (Node n: nodes)
			if (n.getF() < best.getF())
				best = n;

		return best;
	}

	private static boolean isValid(Tile t, ArrayList<Node> closed){
		if (t.occupied())
			return false;
		else if (closed.contains(t))
			return false;

		return true;
	}

	private static boolean isAdjacent(Tile current, Tile next){
		int dx = Math.abs(current.getX() - next.getX());
		int dy = Math.abs(current.getY() - next.getY());

		if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1))
			return true;
		return false;
	}

	private static boolean isDiagonal(Tile current, Tile next){
		int dx = Math.abs(current.getX() - next.getX());
		int dy = Math.abs(current.getY() - next.getY());

		if (dy == 1 && dx == 1)
			return true;
		return false;
	}

	private static int determineG(Tile current, Node parent, Tile dest){
		int g = parent.getG();
		if (isAdjacent(current, parent.getTile()))
			return g + 10;
		else if (isDiagonal(current, parent.getTile()))
			return g + 14;
		else return Integer.MAX_VALUE;
	}
	private static int determineH(Tile current, Tile dest){
		// This will return the Manhattan distance from the current Tile to the destination

		int dx = Math.abs(current.getX() - dest.getX());
		int dy = Math.abs(current.getY() - dest.getY());
		if (current == dest) // this will encourage the destination node to be plucked off the list first when it is added
			return Integer.MIN_VALUE;

		return 10 * (dx + dy);
	}



	/*
	 * The first PathFinding algorithm I attempted to use was one involving flow fields, it was never fully completed
	 * and will most likely not end up being used
	 */
	public static ArrayList<Tile> generateFieldsPath(Coordinates coord, Tile dest){
		ArrayList<Tile> path = new ArrayList<>();
		Tile start = TILES[coord.getX()][coord.getY()];

		Tile current = null;
		ArrayList<PathTuple> tuples = new ArrayList<>();
		do {
			if (current == null)
				current = start;
			generateFieldsNeighbors(current, tuples, dest);
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

	private static void generateFieldsNeighbors(Tile current,
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












