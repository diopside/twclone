package entities.units;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import entities.Coordinates;
import entities.Draggable;
import entities.Faction;
import entities.world.Territory;

abstract public class Unit implements Draggable {

	protected Territory location;
	protected Coordinates coord;
	protected Faction owner;
	protected boolean dragging;
	protected int maxMovement, movementUsed;
	protected int lineOfSight;

	@Override
	abstract public Shape getOffsetShape(int mouseX, int mouseY);
	
	@Override
	public Coordinates getCoordinates() {		
		return coord;
	}

	@Override
	public void setDragging(boolean b) {
		dragging = b;
	}

	@Override
	abstract public void drag(Coordinates offCoords, int xOffset, int yOffset);
	
	
	
	abstract public void render(Graphics g, int xOffset, int yOffset, int mouseX, int mouseY);
		
	
	protected int x(){
		return coord.getX();
	}
	
	protected int y(){
		return coord.getY();
	}
	
}
