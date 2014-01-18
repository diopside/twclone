package entities;

import java.util.ArrayList;
import java.util.Stack;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import entities.units.Army;
import entities.world.MapShape;
import entities.world.Tile;

public class PathGenerator {

	private static Tile[][] TILES;
	private static int SIZE;
	private static final int MAX_DISTANCE = 100 // The greatest distance away from the current unit allowed to generate a path from
			, MAX_SEARCHED = 500;// the maximum number of nodes to traverse

	// These colors will be used in painting unit paths, indicating how long it will take the unit to get somewhere
	public static final Color TURN_1_COLOR = new Color(0, 255, 0), TURN_2_COLOR = new Color(255, 216, 0), 
			TURN_3_COLOR = new Color(255, 106, 0), DEFAULT_TURN_COLOR = new Color(255, 0 ,0);



	public static ArrayList<Tile> generateAStarPath(Coordinates startCoord, Tile dest){
		long s = System.currentTimeMillis();
		ArrayList<Tile> path = new ArrayList<>();
		ArrayList<Node> open = new ArrayList<>();
		ArrayList<Node> closed = new ArrayList<>();
		Tile start = TILES[startCoord.getX()][startCoord.getY()];

		// currently if the destination is occupied it just won't generate a path
		// eventually change this to the adjacent tile with the lowest f value
		if (dest.occupied()){
			dest = getNewDestinationTile(start, dest);
			if (dest == null)
				return path;
		}

		//initially add the starting tile to generate adjacent nodes
		open.add(new Node(start, 0, determineH(start, dest), null));

		// loop will ideally end when the dest is found, otherwise the loop ends when there aren't more nodes to examine, resulting in returning an empty stack
		while (open.size() > 0){
			Node nextNode = open.get(0);
			if (nextNode.getTile() == dest || closed.size() >= MAX_SEARCHED){
				traversePath(nextNode, path);
				path = reversePath(path);
				break;
			}
			addNeighbors(open.get(0), open, closed, start, dest);
		}
		System.out.println(System.currentTimeMillis() - s);
		return path;

	}

	private static Tile getNewDestinationTile(Tile start, Tile oldDest){
		//This method will get the tile with the lowest manhattan distance from the start tile and return it so long as it isn't occupied
		
		ArrayList<Tile> neighbors = MapShape.getTileNeighbors(oldDest, TILES, SIZE);
		int h = Integer.MAX_VALUE;
		Tile bestTile = neighbors.get(0);
		
		for (Tile t: neighbors){
			if (h  > determineH(start, t) && !t.occupied()){
				h = determineH(start, t);
				bestTile = t;
				
			}
		}
		
		// in the event there is no good tile and the starting tile is occupied, return null
		bestTile = bestTile.occupied() ? null : bestTile;
		return bestTile;
	}

	private static ArrayList<Tile> reversePath(ArrayList<Tile> path){
		/*
		 * Currently the path is returned in reverse order
		 * it is easier right now just to quickly reverse the nodes rather than changing other functionality
		 */
		ArrayList<Tile> newPath = new ArrayList<>();
		for (int i = 0; i < path.size(); i ++)
			newPath.add(path.get(path.size() - 1 - i));


		return newPath;

	}
	private static void traversePath(Node node, ArrayList<Tile> path){
		/*
		 * This method will recursively follow the path backwards from node until it reaches a null parent
		 */
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
					if (current.getTile().getX() + i >= 0 && current.getTile().getX() + i < SIZE && current.getTile().getY() + j >= 0 && current.getTile().getY() + j < SIZE){
						Tile next = TILES[current.getTile().getX() + i][current.getTile().getY() + j];
						if (isValid(next, closed)){
							if (open.contains(new Node(next, 0, 0, null)))
								; // recompute f here to see if this is a better route
							else
								open.add(new Node(next, determineG(next, current, dest), determineH(next, dest), current));
						}
					}
				}
			}

		open.remove(current);
		closed.add(current);
		insertionSort(open);

	}
	
	private static void insertionSort(ArrayList<Node> open){
		/*
		 * This is a basic insertion sort with the intention of speeding up the algorithm overall
		 * insertion is used because the list will always be nearly sorted after the neighbors are added with each iteration
		 */
		int elementsSorted = 1;
		int index = 1;
		
		while (elementsSorted < open.size()){
			Node temp = open.get(elementsSorted);
			for (index = elementsSorted; index > 0; index --){
				if (temp.getF() < open.get(index - 1).getF())
					open.set(index, open.get(index - 1));
				else
					break;
			}
			open.set(index, temp);
			elementsSorted ++;
		}
	}


	private static boolean isValid(Tile t, ArrayList<Node> closed){
		Node temp = new Node(t, 0, 0, null);
		if (t.occupied())
			return false;
		else if (closed.contains(temp))
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


	public static void testPathFinder(){
		/*
		 * This method is going to be used to determine the speed of the pathfinding algo
		 */
		long slowestFind = 0;
		long averageFind = 0;
		long quickestFind = 0;

		Tile start, dest;


		for (int i = 0; i < 100; i ++){
			int rnd1 = (int) (Math.random() * SIZE);
			int rnd2 = (int) (Math.random() * SIZE);
			int rnd3 = (int) (Math.random() * SIZE);
			int rnd4 = (int) (Math.random() * SIZE);

			long startTime = System.nanoTime();
			PathGenerator.generateAStarPath(new Coordinates(rnd1, rnd2), TILES[rnd3][rnd4]);
			long duration = System.nanoTime() - startTime;

			averageFind += duration;
			slowestFind = slowestFind > duration ? slowestFind : duration;
			quickestFind = quickestFind < duration? quickestFind : duration;

		}

		System.out.println("Slowest Path Time: " + slowestFind);
		System.out.println("Quickest Path Time: " + quickestFind);
		System.out.println("Average Path Time: " + averageFind / 100);

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



	public static Color getTurnColor(int turn){

		/*
		 * This method will get the appropriate color to render a unit's path based off of how many turns it would take 
		 * the unit to reach the node in the path
		 */
		switch (turn){

		case 0: return TURN_1_COLOR;

		case 1: return TURN_2_COLOR;

		case 2: return TURN_3_COLOR;

		default:  return DEFAULT_TURN_COLOR;
		}

	}



}












