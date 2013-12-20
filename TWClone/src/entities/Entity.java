package entities;

abstract public class Entity {

	protected Coordinates coord; // in terms of tX and tY
	protected Faction owner;
	
	
	public Coordinates getCoordinates(){
		return coord;
	}
	
	protected int x(){
		return coord.getX();
	}
	protected int y(){
		return coord.getY();
	}
	
	public Faction getOwner(){
		return owner;
	}
}
