package entities;

import entities.world.Tile;

public class PathTuple {

	private Tile t;
	private double field;
	
	public PathTuple(Tile t, double f){
		this.t = t;
		this.field = f;
	}
	
	public Tile getTile(){
		return t;
	}
	
	public double getField(){
		return field;
	}
}
