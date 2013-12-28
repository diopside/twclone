package entities.units;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import entities.Coordinates;
import entities.Draggable;
import entities.Entity;
import entities.Faction;
import entities.PathGenerator;
import entities.world.Territory;
import entities.world.Tile;
import entities.world.World;

abstract public class Unit extends Entity implements Draggable {

	protected String name;
	protected boolean dragging;
	protected int maxMovement, remainingMovement;
	protected int lineOfSight;
	protected ArrayList<Tile> path;
	

	@Override
	abstract public Shape getOffsetShape(int mouseX, int mouseY);
	

	@Override
	public void setDragging(boolean b) {
		dragging = b;
	}

	public void resetMovement(){
		remainingMovement = maxMovement;
	}
	@Override
	abstract public void drag(Coordinates offCoords, int xOffset, int yOffset);
	
	
	
	abstract public void render(Graphics g, int xOffset, int yOffset, int mouseX, int mouseY);
		
	
	public String getName(){
		return this.name;
	}
	
	public int getMaxMovement(){
		return maxMovement;
	}
	
	public int getRemainingMovement(){
		return remainingMovement;
	}
	
	public abstract void move(World world);
		
	
	
	public void setDestination(Tile destination){
		
		
		if (destination == null)
			return;
		// this return is called so that a current path is preserved if the player drops the army back in its own territory
		if (destination.getX() == coord.getX() && destination.getY() == coord.getY())
			return;
		
		path = PathGenerator.generateAStarPath(coord, destination);
	}
	
	public ArrayList<Tile> getPath(){
		return path;
	}
}
