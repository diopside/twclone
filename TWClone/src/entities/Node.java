package entities;

import entities.world.Tile;

public class Node {

	
	private Tile t;
	private int g, h;
	private Node parent;
	
	public Node(Tile t, int g, int h, Node parent){
		this.t = t;
		this.g = g;
		this.h = h;
		this.parent = parent;
	}
	
	
	public int getF(){
		return g + h;
	}
	
	public Tile getTile(){
		return t;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public void setParent(Node p){
		parent = p;
	}
	
	public int getG(){
		return g;
	}
	
	public boolean equals(Node other){
		return other.getTile().equals(t);
	}
	
	
	
}
